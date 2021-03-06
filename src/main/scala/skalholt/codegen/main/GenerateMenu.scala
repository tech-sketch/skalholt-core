package skalholt.codegen.main

import skalholt.codegen.database.{Screens, ScreenItems}
import skalholt.codegen.templates._
import skalholt.codegen.templates.MenuTemplate._
import skalholt.codegen.util.FileUtil
import skalholt.codegen.constants.GenConstants._
import com.typesafe.scalalogging.slf4j.LazyLogging
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object GenerateMenu extends App with LazyLogging {

  logger.info("start GenerateMenu")
  require(Await.result(ScreenItems.getScreenItemCount, Duration.Inf) > 0,
    "出力対象の画面設計書項目が存在しません。")

  // Menuのジェネレート
  val folder = if (args != null && args.length > 0) args(0) else outputFolder
  // 処理対象のデータリスト(ACTION_CLASS_ID/SCREEN_ID)を取得
  val menus = Await.result(Screens.find(), Duration.Inf).map {
    case (Some(actionClassId), Some(subsystemNmEn)) => generateMenu(folder, actionClassId, subsystemNmEn)
    case _ => Menu("", "", "")
  }.filter(!_.actionId.isEmpty())

  val str = menuTemplate(menus)
  FileUtil.createFile(str, folder + "/app/views/", "applications.scala.html", overrideMenu)

  logger.info("end   GenerateMenu")

  private def generateMenu(folder: String, actionClassId: String, subsystemNmEn: String): Menu = {

    val screenItemList = Await.result(ScreenItems.findByActionClassIdWithScreen(actionClassId, subsystemNmEn), Duration.Inf)
    require(screenItemList.size > 0, "ユースケース[" + actionClassId + "]の項目定義が存在しません。画面設計書を確認してください。")

    val screens = Await.result(Screens.findByActionClassIdAndSubsystem(actionClassId, subsystemNmEn), Duration.Inf)
    val screen = screens.head
    require(screen != null, "ユースケースに対応する画面情報が存在しません。")

    def getIndexActionPkgId(screenType: Option[String]) = {
      screenType match {
        case Some("Create") => "/create/"
        case Some("Search") => "/"
        case _ => ""
      }
    }

    val pkgNm = screen.subsystemNmEn.get.toLowerCase

    Menu(pkgNm, getIndexActionPkgId(screen.screenType), actionClassId)
  }

}
