package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import skalholt.codegen.database.common.AbstractDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class AbstractScreens extends AbstractDao {
  /** テーブル作成 */
  def createTable = {
    db.run(Screen.schema.create)
  }

  /** テーブル削除 */
  def dropTable = {
    db.run(Screen.schema.drop)
  }
  /** 登録 */
  def create(e: ScreenRow) = {
    db.run(DBIO.seq(Screen += e))
  }

  /** 更新 */
  def update(e: ScreenRow) = {
    val q = Screen.filter(_.screenId === e.screenId).update(e)
    db.run(q)
  }

  /** 削除 */
  def delete(screenId: String) = {
    val q = Screen.filter(_.screenId === screenId).delete
    db.run(q)
  }

  /** ID検索 */
  def findById(screenId: String): Future[ScreenRow] = {
    val action = Screen.filter(_.screenId === screenId).result
    db.run(action.head)
  }

}
