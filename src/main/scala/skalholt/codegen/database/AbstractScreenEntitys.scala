package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import skalholt.codegen.database.common.AbstractDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class AbstractScreenEntitys extends AbstractDao {
  /** テーブル作成 */
  def createTable = {
    db.run(ScreenEntity.schema.create)
  }

  /** テーブル削除 */
  def dropTable = {
    db.run(ScreenEntity.schema.drop)
  }

  /** 登録 */
  def create(e: ScreenEntityRow) = {
    db.run(DBIO.seq(ScreenEntity += e))
  }

  /** 更新 */
  def update(e: ScreenEntityRow) = {
    val q = ScreenEntity.filter(t => t.screenId === e.screenId && t.lineNo === e.lineNo).update(e)
    db.run(q)
  }

  /** 削除 */
  def delete(screenId: String, lineNo: scala.math.BigDecimal) = {
    val q = ScreenEntity.filter(t => t.screenId === screenId && t.lineNo === lineNo).delete
    db.run(q)
  }

  /** ID検索 */
  def findById(screenId: String, lineNo: scala.math.BigDecimal): Future[ScreenEntityRow] = {
    val action = ScreenEntity.filter(t => t.screenId === screenId && t.lineNo === lineNo).result
    db.run(action.head)
  }

}