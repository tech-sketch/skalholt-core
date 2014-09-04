package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._
import skalholt.codegen.database.common.AbstractDao

abstract class AbstractScreenEntitys extends AbstractDao {
  /** テーブル作成 */
  def createTable = database.withSession { implicit session: Session =>
    ScreenEntity.ddl.create
  }

  /** テーブル削除 */
  def dropTable = database.withSession { implicit session: Session =>
    ScreenEntity.ddl.drop
  }
  /** 登録 */
  def create(e: ScreenEntityRow) = database.withTransaction { implicit session: Session =>
    ScreenEntity.insert(e)
  }

  /** 更新 */
  def update(e: ScreenEntityRow) = database.withTransaction { implicit session: Session =>
    ScreenEntity.filter(t => t.screenId === e.screenId && t.lineNo === e.lineNo).update(e)
  }

  /** 削除 */
  def delete(screenId :String, lineNo :scala.math.BigDecimal) = database.withTransaction { implicit session: Session =>
    ScreenEntity.filter(t => t.screenId === screenId && t.lineNo === lineNo).delete
  }

  /** ID検索 */
  def findById(screenId :String, lineNo :scala.math.BigDecimal): ScreenEntityRow = database.withTransaction { implicit session: Session =>
    ScreenEntity.filter(t => t.screenId === screenId && t.lineNo === lineNo).first
  }
}