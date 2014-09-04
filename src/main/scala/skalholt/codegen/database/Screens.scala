package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._

object Screens extends AbstractScreens {

  /** 検索 */
  def find(): List[(Option[String], Option[String])] = database.withTransaction { implicit session: Session =>
    val q = (for {
      ((actionClassId, subsystemNmEn), ss) <- Screen.groupBy(s => (s.actionClassId, s.subsystemNmEn))
    } yield (actionClassId, subsystemNmEn))
      .sortBy { case (actionClassId, subsystemNmEn) => (subsystemNmEn, actionClassId) }

    q.list
  }

  /**
   * アクションクラスID・サブシステム(英名)でエンティティを検索します。
   *
   * @param actionClassId アクションクラスID
   * @param subsystemNmEn サブシステム(英名)
   * @return エンティティのリスト
   */
  def findByActionClassIdAndSubsystem(actionClassId: String, subsystemNmEn: String): List[ScreenRow] = database.withTransaction { implicit session: Session =>
    Screen.filter(v => (v.actionClassId === actionClassId && v.subsystemNmEn === subsystemNmEn)).sortBy(_.screenRbn).list
  }

  def truncate = {
    truncateTable("SCREEN")
  }

}