package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._
import skalholt.codegen.database.common.AbstractDao

abstract class AbstractScreenActions extends AbstractDao {
  /** テーブル作成 */
  def createTable = database.withSession { implicit session: Session =>
    ScreenAction.ddl.create
  }

  /** テーブル削除 */
  def dropTable = database.withSession { implicit session: Session =>
    ScreenAction.ddl.drop
  }
  /** 登録 */
  def create(e: ScreenActionRow) = database.withTransaction { implicit session: Session =>
    ScreenAction.insert(e)
  }

  /** 更新 */
  def update(e: ScreenActionRow) = database.withTransaction { implicit session: Session =>
    ScreenAction.filter(t => t.screenId === e.screenId && t.actionId === e.actionId).update(e)
  }

  /** 削除 */
  def delete(screenId :String, actionId :String) = database.withTransaction { implicit session: Session =>
    ScreenAction.filter(t => t.screenId === screenId && t.actionId === actionId).delete
  }

  /** ID検索 */
  def findById(screenId :String, actionId :String): ScreenActionRow = database.withTransaction { implicit session: Session =>
    ScreenAction.filter(t => t.screenId === screenId && t.actionId === actionId).first
  }
}