package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._
import skalholt.codegen.database.common.AbstractDao

abstract class AbstractScreens extends AbstractDao {
  /** テーブル作成 */
  def createTable = database.withSession { implicit session: Session =>
    Screen.ddl.create
  }

  /** テーブル削除 */
  def dropTable = database.withSession { implicit session: Session =>
    Screen.ddl.drop
  }
  /** 登録 */
  def create(e: ScreenRow) = database.withTransaction { implicit session: Session =>
    Screen.insert(e)
  }

  /** 更新 */
  def update(e: ScreenRow) = database.withTransaction { implicit session: Session =>
    Screen.filter(_.screenId === e.screenId).update(e)
  }

  /** 削除 */
  def delete(screenId: String) = database.withTransaction { implicit session: Session =>
    Screen.filter(_.screenId === screenId).delete
  }

  /** ID検索 */
  def findById(screenId: String): ScreenRow = database.withTransaction { implicit session: Session =>
    Screen.filter(_.screenId === screenId).first
  }
}
