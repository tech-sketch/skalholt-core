package skalholt.codegen.database.common

import scala.slick.jdbc.meta.{ createModel, MTable }
import scala.slick.driver.JdbcDriver
import scala.slick.driver.JdbcDriver.backend.DatabaseDef
import scala.slick.model.{ Column, Table }
import skalholt.codegen.util.StringUtil._
import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._
import skalholt.codegen.constants.GenConstants._
import com.typesafe.scalalogging.slf4j.LazyLogging

object DBUtils extends AbstractDao with LazyLogging {

  def getModel(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String]) = {

    val db =
      if (!user.isEmpty)
        JdbcDriver.simple.Database.forURL(url, driver = jdbcDriver, user = user.get, password = password.getOrElse(""))
      else
        JdbcDriver.simple.Database.forURL(url, driver = jdbcDriver)

    db.withSession { implicit session =>
      val tables = MTable.getTables(None, Some(schema), None, None).list.filter(t => !ignoreTables.exists(_.equalsIgnoreCase(t.name.name)))
        .filter(t => (!t.name.name.endsWith("_pkey") && !t.name.name.endsWith("_seq")))

      logger.info("----- tables -----")
      tables.zipWithIndex.foreach { case (table, index) => logger.info(s"${f"${index + 1}% 4d"}.${table.name.name}") }
      logger.info("----- tables -----")

      createModel(tables, JdbcDriver)
    }
  }

  def getTable(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(tableNm: String): Option[Table] =
    getModel(jdbcDriver, url, schema, user, password).tables.find(_.name.table.equalsIgnoreCase(decamelize(tableNm)))

  def getColumnLength(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(tableNm: String): Int =
    getTable(jdbcDriver, url, schema, user, password)(tableNm) match {
      case Some(t) => t.columns.length
      case _ => 0
    }

  def getColumns(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(tableNm: String): Seq[Column] =
    getTable(jdbcDriver, url, schema, user, password)(tableNm) match {
      case Some(t) => t.columns
      case _ => Seq.empty[Column]
    }

}