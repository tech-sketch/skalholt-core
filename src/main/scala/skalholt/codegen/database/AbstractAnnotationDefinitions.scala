package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import skalholt.codegen.database.common.AbstractDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class AbstractAnnotationDefinitions extends AbstractDao {
  /** テーブル作成 */
  def createTable = {
    db.run(AnnotationDefinition.schema.create)
  }

  /** テーブル削除 */
  def dropTable = {
    db.run(AnnotationDefinition.schema.drop)
  }
  /** 登録 */
  def create(e: AnnotationDefinitionRow) = {
    db.run(DBIO.seq(AnnotationDefinition += e))
  }

  /** 更新 */
  def update(e: AnnotationDefinitionRow) =  {
    val q = AnnotationDefinition.filter(t => t.domainCd === e.domainCd && t.annotationCd === e.annotationCd).update(e)
    db.run(q)
  }

  /** 削除 */
  def delete(domainCd :String, annotationCd :String) =  {
    val q =AnnotationDefinition.filter(t => t.domainCd === domainCd && t.annotationCd === annotationCd).delete
    db.run(q)
  }

  /** ID検索 */
  def findById(domainCd :String, annotationCd :String): Future[AnnotationDefinitionRow] =  {
    val action = AnnotationDefinition.filter(t => t.domainCd === domainCd && t.annotationCd === annotationCd).result
    db.run(action.head)
  }
}