package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._

object AnnotationDefinitions extends AbstractAnnotationDefinitions {

  def truncate = {
    truncateTable("ANNOTATION_DEFINITION")
  }

  /**
   * ドメインコードに紐付いているアノテーションを取得する。
   *
   * @param domainCd 対象のドメインコード
   * @return アノテーション定義のリスト
   */
  def getAnnotationList(domainCd: String) = database.withTransaction { implicit session: Session =>
    val q = (for (
      anodef <- AnnotationDefinition if (anodef.domainCd === domainCd);
      ano <- Annotation if (ano.annotationCd === anodef.annotationCd)
    ) yield (anodef, ano)).sortBy { case (anodef, ano) => anodef.annotationCd }
    q.list
  }

  def filterByDomainCd(domainCd: String) = database.withTransaction { implicit session: Session =>
    AnnotationDefinition.filter(t => t.domainCd === domainCd).firstOption
  }
}