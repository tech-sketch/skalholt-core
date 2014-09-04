package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._
import skalholt.codegen.database.common.AbstractDao

abstract class AbstractScreenItems extends AbstractDao {
  /** テーブル作成 */
  def createTable = database.withSession { implicit session: Session =>
    ScreenItem.ddl.create
  }

  /** テーブル削除 */
  def dropTable = database.withSession { implicit session: Session =>
    ScreenItem.ddl.drop
  }
  /** 登録 */
  def create(e: ScreenItemRow) = database.withTransaction { implicit session: Session =>
    ScreenItem.insert(e)
  }

  /** 更新 */
  def update(e: ScreenItemRow) = database.withTransaction { implicit session: Session =>
    ScreenItem.filter(t => t.screenId === e(0) && t.itemNo === e(1)).update(e)
  }

  /** 削除 */
  def delete(screenId :String, itemNo :scala.math.BigDecimal) = database.withTransaction { implicit session: Session =>
    ScreenItem.filter(t => t.screenId === screenId && t.itemNo === itemNo).delete
  }

  /** ID検索 */
  def findById(screenId :String, itemNo :scala.math.BigDecimal): ScreenItemRow = database.withTransaction { implicit session: Session =>
    ScreenItem.filter(t => t.screenId === screenId && t.itemNo === itemNo).first
  }
}