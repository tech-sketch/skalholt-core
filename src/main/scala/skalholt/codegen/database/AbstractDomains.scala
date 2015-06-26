package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import skalholt.codegen.database.common.AbstractDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class AbstractDomains extends AbstractDao {
  /** テーブル作成 */
  def createTable = {
    db.run(Domain.schema.create)
  }

  /** テーブル削除 */
  def dropTable = {
    db.run(Domain.schema.drop)
  }
  /** 登録 */
  def create(e: DomainRow) = {
    db.run(DBIO.seq(Domain += e))
  }

  /** 更新 */
  def update(e: DomainRow) = {
    val q = Domain.filter(t => t.domainCd === e.domainCd).update(e)
    db.run(q)
  }

  /** 削除 */
  def delete(domainCd :String) = {
    val q = Domain.filter(t => t.domainCd === domainCd).delete
    db.run(q)
  }

  /** ID検索 */
  def findById(domainCd :String): Future[DomainRow] = {
    val action = Domain.filter(t => t.domainCd === domainCd).result
    db.run(action.head)
  }
}