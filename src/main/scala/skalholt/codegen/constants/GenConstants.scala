package skalholt.codegen.constants

import com.typesafe.config._
import com.typesafe.scalalogging.slf4j.LazyLogging

object GenConstants extends LazyLogging {

  val config = ConfigFactory.load()

  // Business Application Schema
  val bizSlickDriver = config.getString("dbbiz.slickDriver")
  val bizJdbcDriver = config.getString("dbbiz.jdbcDriver")
  val bizUrl = config.getString("dbbiz.url")
  val bizCatalog = config.getString("dbbiz.catalog")
  val bizSchema = config.getString("dbbiz.schema")
  val bizUser = config.getString("dbbiz.username")
  val bizPassword = config.getString("dbbiz.password")

  // Generator Schema
  val genSlickDriver = config.getString("db.gen.slickDriver")
  val genDriver = config.getString("db.gen.driver")
  val genUrl = config.getString("db.gen.url")
  val genSchema = config.getString("db.gen.schema")
  val genUser = config.getString("db.gen.username")
  val genPassword = config.getString("db.gen.password")

  // Generator Setting
  val outputFolder = config.getString("src.gen.outputFolder")
  val pkg = config.getString("src.gen.pkg")
  val ignoreTables = config.getString("src.gen.ignoreTables").split(",", 100).toList

  val overrideController = config.getBoolean("override.controller")
  val overrideControllerLogic = config.getBoolean("override.controllerLogic")
  val overrideLogic = config.getBoolean("override.logic")
  val overrideAbstractDao = config.getBoolean("override.abstractDao")
  val overrideDao = config.getBoolean("override.dao")
  val overrideForm = config.getBoolean("override.form")
  val overrideMenu = config.getBoolean("override.menu")
  val overrideRoutes = config.getBoolean("override.routes")
  val overrideView = config.getBoolean("override.view")

  object ScreenType extends Enumeration {
    type ScreenType = Value
    val Create = Value("Create")
    val Update = Value("Update")
    val Search = Value("Search")
  }

  def constantsLogs(args: Array[String] = null) = {

    val (_bizSlickDriver, _bizJdbcDriver, _bizUrl, _bizCatalog, _bizSchema, _outputFolder, _pkg, _bizUser, _bizPassword) =
      if (args != null && args.length >= 9)
        (args(0), args(1), args(2), args(3), args(4), args(5), args(6), args(7), args(8))
      else (bizSlickDriver, bizJdbcDriver, bizUrl, bizCatalog, bizSchema, outputFolder, pkg, bizUser, bizPassword)

    logger.info("---------------------------------------------- ")
    logger.info("-- database schema for business application -- ")
    logger.info("slick driver   = " + _bizSlickDriver)
    logger.info("jdbc driver    = " + _bizJdbcDriver)
    logger.info("url            = " + _bizUrl)
    logger.info("user           = " + _bizUser)
    logger.info("password       = " + _bizPassword)
    logger.info("schema         = " + _bizSchema)
    logger.info("catalog        = " + _bizCatalog)
    logger.info("")
    logger.info("-- database schema for generator            -- ")
    logger.info("slick driver   = " + genSlickDriver)
    logger.info("jdbc driver    = " + genDriver)
    logger.info("url            = " + genUrl)
    logger.info("user           = " + genUser)
    logger.info("password       = " + genPassword)
    logger.info("schema         = " + genSchema)
    logger.info("")
    logger.info("-- generator settings                       -- ")
    logger.info("output folder  = " + _outputFolder)
    logger.info("package        = " + _pkg)
    logger.info("ignore tables  = " + ignoreTables)
    logger.info("---------------------------------------------- ")
    logger.info("")
  }

  case class GenParam(
    bizSlickDriver: Option[String] = Some(config.getString("dbbiz.slickDriver")),
    bizJdbcDriver: Option[String] = Some(config.getString("dbbiz.jdbcDriver")),
    bizUrl: Option[String] = Some(config.getString("dbbiz.url")),
    bizUser: Option[String] = Some(config.getString("dbbiz.username")),
    bizPassword: Option[String] = Some(config.getString("dbbiz.password")),
    bizSchema: Option[String] = Some(config.getString("dbbiz.schema")),
    bizCatalog: Option[String] = Some(config.getString("dbbiz.catalog")),
    outputFolder: Option[String] = Some(config.getString("src.gen.outputFolder")),
    pkg: Option[String] = Some(config.getString("src.gen.pkg")),
    ignoreTables: Option[List[String]] = Some(config.getString("src.gen.ignoreTables").split(",", 100).toList))

  def getArgs(arg: GenParam): (Array[String], Array[String]) = {
    arg match {
      case null => (null, null)
      case x =>
        (Array(x.bizSlickDriver.getOrElse(bizSlickDriver),
          x.bizJdbcDriver.getOrElse(bizJdbcDriver),
          x.bizUrl.getOrElse(bizUrl),
          x.bizCatalog.getOrElse(bizCatalog),
          x.bizSchema.getOrElse(bizSchema),
          x.outputFolder.getOrElse(outputFolder),
          x.pkg.getOrElse(pkg),
          x.bizUser.getOrElse(bizUser),
          x.bizPassword.getOrElse(bizPassword)),
          Array(x.outputFolder.getOrElse(outputFolder)))
    }
  }
}
