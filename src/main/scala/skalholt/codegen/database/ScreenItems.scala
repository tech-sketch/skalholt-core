package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ScreenItems extends AbstractScreenItems {

  def truncate = truncateTable("SCREEN_ITEM")

  def findWithScreenAndDomain(actionClassId: String, subsystemNmEn: String): Future[Seq[(ScreenItemRow, ScreenRow, DomainRow)]] = {
    val q = (for (
      c1 <- ScreenItem;
      c2 <- Screen if (c2.actionClassId === actionClassId && c2.subsystemNmEn === subsystemNmEn) && (c1.screenId === c2.screenId);
      c3 <- Domain if (c1.domainCd === c3.domainCd)
    ) yield (c1, c2, c3)).sortBy { case (screenItem, screen, domain) => (screen.screenRbn, screenItem.itemNo) }

    db.run(q.result)
  }

  /** 検索 */
  def getSearchResultActions(actionClassId: String, subsystemNmEn: String): Future[Seq[ScreenItemRow]] = {
    val q = (for (
      c1 <- ScreenItem if (!c1.actionId.isEmpty && c1.searchresultFlag === "1");
      c2 <- Screen if (c2.actionClassId === actionClassId && c2.subsystemNmEn === subsystemNmEn) && (c1.screenId === c2.screenId)
    ) yield (c1, c2)).sortBy { case (screenItem, screen) => (screen.screenRbn, screenItem.itemNo) }
    val a = q.map { case (screenItem, screen) => screenItem }
    db.run(a.result)
  }

  def getSearchResultItems(actionClassId: String, subsystemNmEn: String) :Future[Seq[ScreenItemRow]] = {
    val q = (for (
      c1 <- ScreenItem if (c1.actionId.isEmpty && c1.searchresultFlag === "1");
      c2 <- Screen if (c2.actionClassId === actionClassId && c2.subsystemNmEn === subsystemNmEn) && (c1.screenId === c2.screenId)
    ) yield (c1, c2)).sortBy { case (screenItem, screen) => (screen.screenRbn, screenItem.itemNo) }
    val a = q.map { case (screenItem, screen) => screenItem }
    db.run(a.result)

  }

  /**
   * 出力対象となる画面設計書項目があるか確認する。
   *
   * @return 取得行数(取得できなかった場合は0件)
   */
  def getScreenItemCount() = {
    db.run(ScreenItem.length.result)
  }

  /**
   * アクションクラスIDとサブシステム（英名）で(SCREENエンティティと結合した)エンティティを検索する。
   *
   * @param actionClassId アクションクラスID
   * @param subsystemNmEn サブシステム名(英名)
   * @return エンティティのリスト
   */
  def findByActionClassIdWithScreen(actionClassId: String, subsystemNmEn: String): Future[Seq[ScreenItemRow]] = {

    val q = (for (
      c1 <- ScreenItem;
      c2 <- Screen if (c2.actionClassId === actionClassId && c2.subsystemNmEn === subsystemNmEn) && (c1.screenId === c2.screenId)
    ) yield (c1, c2)).sortBy { case (screenItem, screen) => (screen.screenRbn, screenItem.itemNo) }

    val a = q.map { case (screenItem, screen) => screenItem }
    db.run(a.result)
  }

  def findByScreenIdWithAction(screenId: String): Future[Seq[(ScreenItemRow, ScreenActionRow)]] = {

    val q = (for (
      si <- ScreenItem if (si.screenId === screenId && si.searchresultFlag === "0");
      sa <- ScreenAction if (si.screenId === sa.screenId && si.actionId === sa.actionId && sa.actionNmEn.isDefined)
    ) yield (si, sa)).sortBy { case (screenItem, screenAction) => (screenItem.itemNo) }

    db.run(q.result)
  }

  def findByActionId(screenId: String, actionId: String): Future[Option[ScreenItemRow]] = {

    val q = ScreenItem.filter(s => s.screenId === screenId && s.actionId === actionId)
    db.run(q.result.headOption)

  }

}