package skalholt.codegen.main

import skalholt.codegen.database.{ Screens, ScreenItems, ScreenEntitys, ScreenActions }
import skalholt.codegen.database.common.Tables.{ ScreenRow, ScreenItemRow, DomainRow }
import skalholt.codegen.templates.ViewTemplate._
import java.io.{ FileOutputStream, IOException, FileNotFoundException, BufferedWriter, OutputStreamWriter, File }
import skalholt.codegen.database.AnnotationDefinitions
import skalholt.codegen.util.StringUtil._
import skalholt.codegen.util.{ FileUtil, GenUtil }
import skalholt.codegen.constants.GenConstants._
import skalholt.codegen.templates.{ Views, Link }
import com.typesafe.scalalogging.slf4j.LazyLogging
import skalholt.codegen.database.common.DBUtils

object GenerateView extends App with LazyLogging {

  logger.info("start GenerateView")

  require(ScreenItems.getScreenItemCount > 0, "出力対象の画面設計書項目が存在しません。")

  // Viewのジェネレート
  val Array(slickDriver, jdbcDriver, url, catalog, schema, folder, tablesPkg, _*) =
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
  generate(jdbcDriver, url, schema, user, password)(folder, tablesPkg)

  logger.info("end   GenerateView")

  private def generate(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(folder: String, tablesPkg: String) = {

    // 処理対象のデータリスト(ACTION_CLASS_ID/SCREEN_ID)を取得
    val actionClassIdList = Screens.find()
    actionClassIdList.foreach {
      case (Some(actionClassId), Some(subsystemNmEn)) =>
        val screenItemList =
          ScreenItems.findWithScreenAndDomain(actionClassId, subsystemNmEn)

        if (screenItemList.size == 0) {
          logger.warn("ユースケース[" + actionClassId +
            "]の項目定義が存在しません。画面設計書を確認してください。")
        } else {
          val screenList = Screens.findByActionClassIdAndSubsystem(actionClassId, subsystemNmEn)

          def createHtml(screen: ScreenRow) = {
            val actionClassId = screen.actionClassId.get

            val (submitActionClassId, submitActionId) =
              ScreenActions.findByScreenId(screen.screenId).headOption match {
                case Some(screenAction) =>
                  val (submitScreenId, submitActionId) = screenAction.forwardScreenId match {
                    case Some("myself") => (screen.screenId, decapitalize(screenAction.actionNmEn.get))
                    case Some(screenId) => (screenId, "index")
                    case _ => (screen.screenId, "index")
                  }
                  (Screens.findById(submitScreenId).actionClassId.get,
                    submitActionId)
                case _ => (screen.screenId, "index")

              }

            val buttons = ScreenItems.findByScreenIdWithAction(screen.screenId).map(_.actionNmEn.get)

            val tableTitle = screen.screenNm.get
            val entityNm = ScreenEntitys.findById(screen.screenId, 1).entityNmEn.get

            val rows = screenItemList.map {
              case (screenItem, screen, domain) =>
                GenUtil.createRows(screenItem, domain)
            }
            val pkgNm = screen.subsystemNmEn.get

            def getColumnType(screenItem: ScreenItemRow, domain: DomainRow) = {
              val p = GenUtil.getColumnType(screenItem, domain)
              s"${p.pname} :${p.ptype}"
            }

            def getfwSctionId(screenId: String, actionId: String, fwActionId: String) = {
              (actionId, fwActionId) match {
                case (current, "myself") => s"${capitalize(screenId)}.${decapitalize(current)}"
                case (current, fw) => s"${capitalize(fw)}.index"
              }
            }

            def getfwPkg(fwScreenId: Option[String]) = fwScreenId match {
              case Some("myself") => pkgNm
              case Some(id) => Screens.findById(id).subsystemNmEn.getOrElse(pkgNm)
              case _ => pkgNm
            }

            val keys = screenItemList.filter(_._1(9) == Some("pk"))
            val param = keys.map {
              case (screenItem, screen, domain) =>
                getColumnType(screenItem, domain)
            }.mkString(", ")
            val paramNm = keys.map(_._1(4).get).mkString(", ")

            def getIcon(actionNm: String) = {
              actionNm match {
                case "Update" => "glyphicon glyphicon-edit"
                case "Delete" => "glyphicon glyphicon-remove"
                case _ => ""
              }
            }
            def getFoword(screenItem: ScreenItemRow) = {
              val screenAction = ScreenActions.findById(screen.screenId, screenItem(24).get)
              Link(getfwPkg(screenAction.forwardScreenId),
                getfwSctionId(screenAction.screenId, screenAction.actionNmEn.get, screenAction.forwardScreenId.get),
                keys.zipWithIndex.map {
                  case ((screenItem, screen, domain), index) =>
                    (if (rows.length > 22) s"${entityNm}(${index})"
                    else s"${entityNm}.${screenItem(4).get}") + (
                      if (domain.dataType == Some("bigDecimal")) ".toString"
                      else "")
                }.mkString(", "),
                getIcon(screenAction.actionNmEn.get))
            }

            val actions = ScreenItems.getSearchResultActions(actionClassId, subsystemNmEn)
            val tableLinkData = actions.map(getFoword)

            val resultItems = ScreenItems.getSearchResultItems(actionClassId, subsystemNmEn).map(_(4).get)

            val allColumns = DBUtils.getColumns(jdbcDriver, url, schema, user, password)(entityNm)
            val columnLength = allColumns.length
            val columns = allColumns.filter(!_.options.exists(o => o.toString.eq("AutoInc")))
            val keyColumns = allColumns.filter(_.options.exists(o => o.toString.eq("AutoInc")))
            val rowsExcluded = rows.filter(p => !keyColumns.exists(_.name.equalsIgnoreCase(p.pname)))

            val views =
              if (screen.screenType == Some("Update"))
                Views(actionClassId, submitActionClassId, submitActionId, tableTitle, rows, buttons, pkgNm, tablesPkg, entityNm, allColumns.toList, resultItems)
              else
                Views(actionClassId, submitActionClassId, submitActionId, tableTitle, rowsExcluded, buttons, pkgNm, tablesPkg, entityNm, allColumns.toList, resultItems)
            val str = screen.screenType match {
              case Some("Search") => searchTemplate(views, tableLinkData)
              case Some("Create") => createTemplate(views)
              case Some("Update") => updateTemplate(views, param, paramNm)
              case _ => "no template"
            }

            FileUtil.createFile(str, s"${folder}/app/views/${pkgNm.toLowerCase}", screen.actionClassId.get + ".scala.html", overrideView)

          }
          screenList.foreach(createHtml)
        }
      case _ =>
    }
  }
}
