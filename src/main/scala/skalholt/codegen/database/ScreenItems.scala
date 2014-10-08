package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._

object ScreenItems extends AbstractScreenItems {

  def findWithScreenAndDomain(actionClassId: String, subsystemNmEn: String) = database.withTransaction { implicit session: Session =>
    val q = (for (
      c1 <- ScreenItem;
      c2 <- Screen if (c2.actionClassId === actionClassId && c2.subsystemNmEn === subsystemNmEn) && (c1.screenId === c2.screenId);
      c3 <- Domain if (c1.domainCd === c3.domainCd)
    ) yield (c1, c2, c3)).sortBy { case (screenItem, screen, domain) => (screen.screenRbn, screenItem.itemNo) }
    q.list
  }

  /** 検索 */
  def getSearchResultActions(actionClassId: String, subsystemNmEn: String) = database.withTransaction { implicit session: Session =>
    val q = (for (
      c1 <- ScreenItem if (!c1.actionId.isEmpty && c1.searchresultFlag === "1");
      c2 <- Screen if (c2.actionClassId === actionClassId && c2.subsystemNmEn === subsystemNmEn) && (c1.screenId === c2.screenId)
    ) yield (c1, c2)).sortBy { case (screenItem, screen) => (screen.screenRbn, screenItem.itemNo) }
    q.list.map { case (screenItem, screen) => screenItem }
  }

  def getSearchResultItems(actionClassId: String, subsystemNmEn: String) = database.withTransaction { implicit session: Session =>
    val q = (for (
      c1 <- ScreenItem if (c1.actionId.isEmpty && c1.searchresultFlag === "1");
      c2 <- Screen if (c2.actionClassId === actionClassId && c2.subsystemNmEn === subsystemNmEn) && (c1.screenId === c2.screenId)
    ) yield (c1, c2)).sortBy { case (screenItem, screen) => (screen.screenRbn, screenItem.itemNo) }
    q.list.map { case (screenItem, screen) => screenItem }
  }

  /**
   * 出力対象となる画面設計書項目があるか確認する。
   *
   * @return 取得行数(取得できなかった場合は0件)
   */
  def getScreenItemCount() = database.withTransaction { implicit session: Session =>
    ScreenItem.list.length
  }

  /**
   * アクションクラスIDとサブシステム（英名）で(SCREENエンティティと結合した)エンティティを検索する。
   *
   * @param actionClassId アクションクラスID
   * @param subsystemNmEn サブシステム名(英名)
   * @return エンティティのリスト
   */
  def findByActionClassIdWithScreen(actionClassId: String, subsystemNmEn: String) = database.withTransaction { implicit session: Session =>

    val q = (for (
      c1 <- ScreenItem;
      c2 <- Screen if (c2.actionClassId === actionClassId && c2.subsystemNmEn === subsystemNmEn) && (c1.screenId === c2.screenId)
    ) yield (c1, c2)).sortBy { case (screenItem, screen) => (screen.screenRbn, screenItem.itemNo) }

    q.list.map { case (screenItem, screen) => screenItem }
  }

  def findByScreenIdWithAction(screenId: String): List[(ScreenItemRow, ScreenActionRow)] = database.withTransaction { implicit session: Session =>

    val q = (for (
      si <- ScreenItem if (si.screenId === screenId && si.searchresultFlag === "0");
      sa <- ScreenAction if (si.screenId === sa.screenId && si.actionId === sa.actionId && sa.actionNmEn.isNotNull)
    ) yield (si, sa)).sortBy { case (screenItem, screenAction) => (screenItem.itemNo) }

    q.list
  }

  def truncate = {
    truncateTable("SCREEN_ITEM")
  }

  def findByActionId(screenId: String, actionId: String): Option[ScreenItemRow] = database.withTransaction { implicit session: Session =>

    ScreenItem.filter(s => s.screenId === screenId && s.actionId === actionId).list.headOption

  }

}