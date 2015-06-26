package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import skalholt.codegen.database.common.AbstractDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class AbstractScreenItems extends AbstractDao {
  /** テーブル作成 */
  def createTable = {
    db.run(ScreenItem.schema.create)
  }

  /** テーブル削除 */
  def dropTable = {
    db.run(ScreenItem.schema.drop)
  }
  /** 登録 */
  def create(e: ScreenItemRow) = {
    db.run(DBIO.seq(ScreenItem += e))
  }

  /** 更新 */
  def update(e: ScreenItemRow) = {
    val q = for{s <- ScreenItem if (s.screenId === e(0)  && s.itemNo === e(1))} yield s
    db.run(q.update(e))
  }

  /** 削除 */
  def delete(screenId :String, itemNo :scala.math.BigDecimal) = {
    db.run(ScreenItem.filter(t => t.screenId === screenId && t.itemNo === itemNo).delete)
  }

  /** ID検索 */
  def findById(screenId :String, itemNo :scala.math.BigDecimal): Future[ScreenItemRow] = {
    val action = ScreenItem.filter(t => t.screenId === screenId && t.itemNo === itemNo).result
    db.run(action.head)
  }
}