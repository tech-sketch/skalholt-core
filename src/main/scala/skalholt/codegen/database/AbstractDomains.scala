package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._
import skalholt.codegen.database.common.AbstractDao

abstract class AbstractDomains extends AbstractDao {
  /** テーブル作成 */
  def createTable = database.withSession { implicit session: Session =>
    Domain.ddl.create
  }

  /** テーブル削除 */
  def dropTable = database.withSession { implicit session: Session =>
    Domain.ddl.drop
  }
  /** 登録 */
  def create(e: DomainRow) = database.withTransaction { implicit session: Session =>
    Domain.insert(e)
  }

  /** 更新 */
  def update(e: DomainRow) = database.withTransaction { implicit session: Session =>
    Domain.filter(t => t.domainCd === e.domainCd).update(e)
  }

  /** 削除 */
  def delete(domainCd :String) = database.withTransaction { implicit session: Session =>
    Domain.filter(t => t.domainCd === domainCd).delete
  }

  /** ID検索 */
  def findById(domainCd :String): DomainRow = database.withTransaction { implicit session: Session =>
    Domain.filter(t => t.domainCd === domainCd).first
  }
}