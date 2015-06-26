package skalholt.codegen.main

import skalholt.codegen.database.{Screens, ScreenItems, ScreenActions}
import skalholt.codegen.database.common.Tables.{ScreenItemRow, DomainRow}
import skalholt.codegen.templates._
import skalholt.codegen.templates.RoutesTemplate._
import skalholt.codegen.util.FileUtil
import skalholt.codegen.constants.GenConstants._
import skalholt.codegen.util.GenUtil
import com.typesafe.scalalogging.slf4j.LazyLogging
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object GenerateRoutes extends App with LazyLogging {

  logger.info("start GenerateRoutes")

  // ジェネレート
  val folder = if (args != null && args.length > 0) args(0) else outputFolder
  generate(folder)

  logger.info("end   GenerateRoutes")

  private def generate(folder: String) = {

    require(Await.result(ScreenItems.getScreenItemCount, Duration.Inf) > 0,
      "出力対象の画面設計書項目が存在しません。")

    // Routesのジェネレート
    // 処理対象のデータリスト(ACTION_CLASS_ID/SCREEN_ID)を取得
    val actionClassIdList = Await.result(Screens.find(), Duration.Inf)
    val routes = actionClassIdList.map {
      case (Some(actionClassId), Some(subsystemNmEn)) => generateRoutes(actionClassId, subsystemNmEn)
      case _ => List(Route("", "", "", "", ""))
    }.flatMap(r => r)
    val str = routesTemplate(routes)
    FileUtil.createFile(str, folder + "/conf", "routes", overrideRoutes)
  }

  private def generateRoutes(actionClassId: String, subsystemNmEn: String): Seq[Route] = {
    val screenItemList = Await.result(ScreenItems.findWithScreenAndDomain(actionClassId, subsystemNmEn), Duration.Inf)
    require(screenItemList.size > 0, "ユースケース[" + actionClassId +
      "]の項目定義が存在しません。画面設計書を確認してください。")

    val screens = Await.result(Screens.findByActionClassIdAndSubsystem(actionClassId, subsystemNmEn), Duration.Inf)
    val screen = screens.head
    require(screen != null, "ユースケースに対応する画面情報が存在しません。")

    def getColumnType(screenItem: ScreenItemRow, domain: DomainRow) = {
      val p = GenUtil.getColumnType(screenItem, domain)
      s"${p.pname} :${p.ptype}"
    }

    val keys = screenItemList.filter(_._1(9) == Some("pk"))
    val param = keys.map { case (screenItem, screen, domain) => getColumnType(screenItem, domain) }.mkString(", ")
    val urlParam = keys.map(v => ":" + v._1(4).get).mkString("/")

    def getRequest(actionNm: Option[String], screenId: String, actionId: String) = {
      Await.result(ScreenItems.findByActionId(screenId, actionId), Duration.Inf) match {
        case Some(screenItem) => screenItem(8) match {
          case Some("link") => "GET "
          case _ => "POST"
        }
        case _ => "POST"
      }
    }

    def getActionPkgId(actionNm: Option[String]) = {
      actionNm match {
        case Some("Create") => "create/create/"
        case Some("Update") => s"${urlParam}/"
        case Some("Search") => "search/"
        case Some("Delete") => s"${urlParam}/delete/"
        case _ => ""
      }
    }
    def getIndexActionPkgId(screenType: Option[String]) = {
      screenType match {
        case Some("Create") => "create/"
        case Some("Update") => s"${urlParam}/update/"
        case Some("Search") => ""
        case _ => ""
      }
    }
    def getActionId(actionNm: Option[String]) = {
      actionNm match {
        case Some("Update") => s"update(${param})"
        case Some("Delete") => s"delete(${param})"
        case x => x.get
      }
    }
    def getIndexActionId(screenType: Option[String]) = {
      screenType match {
        case Some("Update") => s"index(${param})"
        case _ => "index"
      }
    }

    val screenActionList = Await.result(ScreenActions.findByScreenId(screen.screenId), Duration.Inf)
    val pkgNm = screen.subsystemNmEn.get.toLowerCase

    Route("GET ", pkgNm, getIndexActionPkgId(screen.screenType), actionClassId, getIndexActionId(screen.screenType)) +:
      screenActionList.filter { f =>
        f.forwardScreenId == Some("myself") ||
          Await.result(Screens.findById(f.screenId), Duration.Inf).useCaseNm ==
            Await.result(Screens.findById(f.forwardScreenId.get), Duration.Inf).useCaseNm
      }.map(f =>
        Route(getRequest(f.actionNmEn, f.screenId, f.actionId), pkgNm, getActionPkgId(f.actionNmEn), actionClassId, getActionId(f.actionNmEn)))
  }

}
