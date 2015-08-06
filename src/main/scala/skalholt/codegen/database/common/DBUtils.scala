package skalholt.codegen.database.common

import skalholt.codegen.util.StringUtil
import slick.jdbc.meta.MTable
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import slick.model.Column
import skalholt.codegen.util.StringUtil._
import slick.driver.H2Driver.api._
import skalholt.codegen.constants.GenConstants._
import com.typesafe.scalalogging.slf4j.LazyLogging
import slick.jdbc.JdbcModelBuilder

object DBUtils extends AbstractDao with LazyLogging {

  def getDatabase(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String]) =
    if (!user.isEmpty)
      Database.forURL(url, driver = jdbcDriver, user = user.get, password = password.getOrElse(""))
    else
      Database.forURL(url, driver = jdbcDriver)

  def getTables(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String]) = {
    val tablesA = MTable.getTables(None, Some(schema), None, None)
    val gendb = getDatabase(jdbcDriver, url, schema, user, password)
    Await.result(gendb.run(tablesA), Duration.Inf)
      .filter(_.tableType == "TABLE")
      .filter(t => !ignoreTables.exists(_.equalsIgnoreCase(t.name.name)))
  }

  def getModel(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String]) = {
    val tables = getTables(jdbcDriver, url, schema, user, password)
    val gendb = getDatabase(jdbcDriver, url, schema, user, password)
    val modelBuilder = (new JdbcModelBuilder(tables, true)).buildModel
    Await.result(gendb.run(modelBuilder), Duration.Inf)
  }

  def getTable(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(tableNm: String): Option[MTable] =
    getTables(jdbcDriver, url, schema, user, password).find(t => t.name.name.equalsIgnoreCase(decamelize(tableNm)))

  def getColumnLength(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(tableNm: String): Int =
    (getColumns(jdbcDriver, url, schema, user, password)(tableNm: String)).length

  def getColumns(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(tableNm: String): Seq[Column] = {
    getModel(jdbcDriver, url, schema, user, password).tables.filter(t => t.name.table.compareToIgnoreCase(StringUtil.decamelize(tableNm)) == 0) match {
      case m if (m.length > 0) => m.head.columns
      case _ => Seq.empty[Column]
    }
  }
}