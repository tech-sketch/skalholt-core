package skalholt.codegen.main

import skalholt.codegen.database.{Screens, ScreenItems, ScreenEntitys, ScreenActions}
import skalholt.codegen.database.common.Tables.ScreenActionRow
import skalholt.codegen.util.FileUtil
import skalholt.codegen.constants.GenConstants._
import skalholt.codegen.templates._
import skalholt.codegen.templates.ControllerTemplate._
import skalholt.codegen.util.GenUtil
import skalholt.codegen.util.StringUtil._
import com.typesafe.scalalogging.slf4j.LazyLogging
import scala.collection.mutable.ListBuffer
import skalholt.codegen.database.common.DBUtils
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object GenerateController extends App with LazyLogging {
  logger.info("start GenerateController")
  require(Await.result(ScreenItems.getScreenItemCount, Duration.Inf) > 0,
    "出力対象の画面設計書項目が存在しません。")


  // Controllerのジェネレート
  val Array(slickDriver, jdbcDriver, url, catalog, schema, outputDir, tablesPkg, _*) =
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

  val (user, password) = if (args.size > 7) (Some(args(7)), Some(args(8))) else (None, None)

  generateController(slickDriver, jdbcDriver, url, schema, user, password)(outputDir, tablesPkg)

  logger.info("end   GenerateController")

  private def generateController(slickDriver: String, jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(folder: String, tablesPkg: String) = {

    // 処理対象のデータリスト(ACTION_CLASS_ID/SCREEN_ID)を取得
    val actionClassIdList = Await.result(Screens.find(), Duration.Inf)

    var entities = ListBuffer.empty[String]

    actionClassIdList.foreach {
      case (Some(actionClassId), Some(subsystemNmEn)) =>
        val flist = ScreenItems.findWithScreenAndDomain(actionClassId, subsystemNmEn)
        flist.onSuccess { case screenItemList =>
          if (screenItemList.size == 0) {
            logger.warn(s"ユースケース[${actionClassId}]の項目定義が存在しません。画面設計書を確認してください。")
          } else {
            val screenListf = Screens.findByActionClassIdAndSubsystem(actionClassId, subsystemNmEn)
            screenListf.onSuccess { case screenList =>
              val screen = screenList.head

              if (screen != null) {
                val pkgNm = screen.subsystemNmEn.get.toLowerCase
                val screenEntitysf = ScreenEntitys.findById(screen.screenId, 1)

                val screenActionList = Await.result(ScreenActions.findByScreenId(screen.screenId), Duration.Inf)
                val screenItems = screenItemList.filter(_._1(9) == Some("pk"))
                val colKeys = screenItems.map {
                  case (screenItem, screen, domain) =>
                    GenUtil.getColumnType(screenItem, domain)
                }
                require(!colKeys.isEmpty, "pk:" + colKeys + ",screenActionList:" + screenActionList)
                val actionNms = screenActionList.filter(_.forwardScreenId == Some("myself")).map(_.actionNmEn.getOrElse(""))

                Await.ready(screenEntitysf, Duration.Inf)
                val entityNm = screenEntitysf.value.get match {
                  case Success(e) => e.entityNmEn.get
                  case Failure(e) => throw e
                }
                val columns = DBUtils.getColumns(jdbcDriver, url, schema, user, password)(entityNm)
                val rows = screenItemList.map { case (screenItem, screen, domain) => GenUtil.createRows(screenItem, screen, domain) }

                val allColumns = DBUtils.getColumns(jdbcDriver, url, schema, user, password)(entityNm)
                val columnLength = allColumns.length
                val keyColumns = allColumns.filter(_.options.exists(o => o.toString.eq("AutoInc")))
                val rowsExcluded = rows.filter(p => !keyColumns.exists(_.name == decamelize(p.iname)))
                val itemIsMatch = GenUtil.isMatch(allColumns, rows)

                val parm = if (screen.screenType == Some("Update"))
                  Controllers(pkgNm, entityNm, actionClassId, actionNms, colKeys, tablesPkg, rows, columns.toList, itemIsMatch, slickDriver)
                else
                  Controllers(pkgNm, entityNm, actionClassId, actionNms, colKeys, tablesPkg, rowsExcluded, columns.toList, itemIsMatch, slickDriver)

                val parmExcluded = Controllers(pkgNm, entityNm, actionClassId, actionNms, colKeys, tablesPkg, rowsExcluded, columns.toList, itemIsMatch, slickDriver)
                val parmAll = Controllers(pkgNm, entityNm, actionClassId, actionNms, colKeys, tablesPkg, rows, columns.toList, itemIsMatch, slickDriver)

                val str = controllerTemplate(parm)
                FileUtil.createFile(str, folder + s"/app/controllers/$pkgNm", capitalize(screen.actionClassId.get) + ".scala", overrideController)

                val indexStr = screen.screenType match {
                  case Some(screenType) if (screenType == "Create") =>
                    indexCreateTemplate(parmExcluded)
                  case Some(screenType) if (screenType == "Update") =>
                    indexUpdateTemplate(parmAll)
                  case Some(screenType) if (screenType == "Search") =>
                    indexSearchTemplate(parmExcluded)
                  case _ => ""
                }
                FileUtil.createFile(indexStr, folder + s"/app/controllers/$pkgNm/${actionClassId.toLowerCase}", "Index.scala", overrideControllerLogic)

                def createLogic(screenAction: ScreenActionRow) = {
                  val (actionNm, actionMethod, logicMethod) = screenAction.actionNmEn match {
                    case Some(actionNm) if (actionNm == "Create") =>
                      (actionNm, createTemplate(parmExcluded), createLogicTemplate(parmAll))
                    case Some(actionNm) if (actionNm == "Update") =>
                      (actionNm, updateTemplate(parmAll), updateLogicTemplate(parmAll))
                    case Some(actionNm) if (actionNm == "Search") =>
                      (actionNm, searchTemplate(parmExcluded), searchLogicTemplate(parmExcluded))
                    case Some(actionNm) if (actionNm == "Delete") =>
                      (actionNm, deleteTemplate(parmExcluded), deleteLogicTemplate(parmExcluded))
                    case Some(actionNm) => (actionNm, "???", "???")
                    case _ => ("???", "???", "???")
                  }
                  FileUtil.createFile(actionMethod, folder + s"/app/controllers/$pkgNm/${actionClassId.toLowerCase}", actionNm + ".scala", overrideControllerLogic)
                  FileUtil.createFile(logicMethod, folder + s"/app/logics/${entityNm.toLowerCase}", actionNm + "Logic.scala", overrideLogic)
                }
                screenActionList.filter(_.forwardScreenId == Some("myself")).foreach(createLogic)

                if (!entities.contains(entityNm)) {
                  val indexLogicStr = indexCreateLogicTemplate(parm)
                  FileUtil.createFile(indexLogicStr, folder + s"/app/logics/${entityNm.toLowerCase}", "IndexLogic.scala", overrideLogic)
                  entities += entityNm
                }
              }
            }
          }
        }

      case _ =>
    }
  }
}
