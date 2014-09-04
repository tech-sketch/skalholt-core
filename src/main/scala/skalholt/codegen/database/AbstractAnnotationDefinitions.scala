package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._
import skalholt.codegen.database.common.AbstractDao

abstract class AbstractAnnotationDefinitions extends AbstractDao {
  /** テーブル作成 */
  def createTable = database.withSession { implicit session: Session =>
    AnnotationDefinition.ddl.create
  }

  /** テーブル削除 */
  def dropTable = database.withSession { implicit session: Session =>
    AnnotationDefinition.ddl.drop
  }
  /** 登録 */
  def create(e: AnnotationDefinitionRow) = database.withTransaction { implicit session: Session =>
    AnnotationDefinition.insert(e)
  }

  /** 更新 */
  def update(e: AnnotationDefinitionRow) = database.withTransaction { implicit session: Session =>
    AnnotationDefinition.filter(t => t.domainCd === e.domainCd && t.annotationCd === e.annotationCd).update(e)
  }

  /** 削除 */
  def delete(domainCd :String, annotationCd :String) = database.withTransaction { implicit session: Session =>
    AnnotationDefinition.filter(t => t.domainCd === domainCd && t.annotationCd === annotationCd).delete
  }

  /** ID検索 */
  def findById(domainCd :String, annotationCd :String): AnnotationDefinitionRow = database.withTransaction { implicit session: Session =>
    AnnotationDefinition.filter(t => t.domainCd === domainCd && t.annotationCd === annotationCd).first
  }
}