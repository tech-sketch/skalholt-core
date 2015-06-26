package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import skalholt.codegen.database.common.AbstractDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class AbstractScreenActions extends AbstractDao {
  /** テーブル作成 */
  def createTable = {
    db.run(ScreenAction.schema.create)
  }

  /** テーブル削除 */
  def dropTable = {
    db.run(ScreenAction.schema.drop)
  }
  /** 登録 */
  def create(e: ScreenActionRow) = {
    db.run(DBIO.seq(ScreenAction += e))
  }

  /** 更新 */
  def update(e: ScreenActionRow) = {
    val q =ScreenAction.filter(t => t.screenId === e.screenId && t.actionId === e.actionId).update(e)
    db.run(q)
  }

  /** 削除 */
  def delete(screenId :String, actionId :String) = {
    val q =ScreenAction.filter(t => t.screenId === screenId && t.actionId === actionId).delete
    db.run(q)
  }

  /** ID検索 */
  def findById(screenId :String, actionId :String): Future[ScreenActionRow] =  {
    val action = ScreenAction.filter(t => t.screenId === screenId && t.actionId === actionId).result
    db.run(action.head)
  }

}