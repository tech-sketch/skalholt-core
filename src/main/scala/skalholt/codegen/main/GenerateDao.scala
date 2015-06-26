package skalholt.codegen.main

import slick.codegen.SourceCodeGenerator
import slick.model.{ Model, Table }
import skalholt.codegen.constants.GenConstants._
import skalholt.codegen.database.common.DBUtils
import skalholt.codegen.templates.DaoTemplate._
import skalholt.codegen.util.StringUtil._
import com.typesafe.scalalogging.slf4j.LazyLogging

object GenerateDao extends App with LazyLogging {

  logger.info("start GenerateDao")

  // ジェネレート
  val genargs =
    if (args == null || args.isEmpty)
      Array(bizSlickDriver,
        bizJdbcDriver,
        bizUrl,
        bizCatalog,
        bizSchema,
        outputFolder,
        pkg,
        bizUser,
        bizPassword)
    else args

  generate(genargs)

  logger.info("end   GenerateDao")

  private def generate(args: Array[String]) = {

    val Array(slickDriver, jdbcDriver, url, catalog, schema, outputDir, pkg, _*) = args
    val (user, password) = if (args.size > 7) (Some(args(7)), Some(args(8))) else (None, None)
    val model = DBUtils.getModel(jdbcDriver, url, schema, user, password)

    val folder = outputDir + "/app/"
    GenerateDao(model, folder, slickDriver, pkg)
    generateEntity(model, folder, slickDriver, pkg)
  }

  private def generateEntity(model: Model, folder: String, driver: String, pkg: String) = {
    val codegen = new SourceCodeGenerator(model)
    codegen.writeToFile(driver, folder, pkg)
  }

  private def GenerateDao(model: Model, folder: String, slickDriver: String, pkg: String) = {
    val codegen = new SourceCodeGenerator(model)
    def outputByTable(table: Table) = {
      import slick.ast.ColumnOption.PrimaryKey

      val pks = (table.primaryKey match {
        case Some(x) => x.columns
        case None => table.columns.filter(_.options.contains(PrimaryKey)).toList
      }).map(col => (decapitalize(camelize(col.name)), col))

      // TODO I'd like to add a function find by only a part of keys
      // AbstractDaoの出力
      val abstractDao = abstractDaoTemplate(camelize(table.name.table), pks, table.columns.toList, slickDriver, pkg)
      codegen.writeStringToFile(abstractDao, folder, "daos", s"Abstract${camelize(table.name.table)}s.scala")

      // Daoの出力
      val dao = daoTemplate(camelize(table.name.table), slickDriver, pkg)
      codegen.writeStringToFile(dao, folder, "daos", s"${camelize(table.name.table)}s.scala")
    }

    model.tables.foreach(outputByTable)
  }
}
