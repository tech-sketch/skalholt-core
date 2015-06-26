package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Screens extends AbstractScreens {

  def truncate = truncateTable("SCREEN")

  /** 検索 */
  def find(): Future[Seq[(Option[String], Option[String])]] = {
    val q = (for {
      ((actionClassId, subsystemNmEn), ss) <- Screen.groupBy(s => (s.actionClassId, s.subsystemNmEn))
    } yield (actionClassId, subsystemNmEn))
      .sortBy { case (actionClassId, subsystemNmEn) => (subsystemNmEn, actionClassId) }

    db.run(q.result)
  }

  /**
   * アクションクラスID・サブシステム(英名)でエンティティを検索します。
   *
   * @param actionClassId アクションクラスID
   * @param subsystemNmEn サブシステム(英名)
   * @return エンティティのリスト
   */
  def findByActionClassIdAndSubsystem(actionClassId: String, subsystemNmEn: String): Future[Seq[ScreenRow]] = {
    val q = Screen.filter(v => (v.actionClassId === actionClassId && v.subsystemNmEn === subsystemNmEn)).sortBy(_.screenRbn)
    db.run(q.result)
  }

}