package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

object AnnotationDefinitions extends AbstractAnnotationDefinitions {

  def truncate = truncateTable("ANNOTATION_DEFINITION")

  /**
   * ドメインコードに紐付いているアノテーションを取得する。
   *
   * @param domainCd 対象のドメインコード
   * @return アノテーション定義のリスト
   */
  def getAnnotationList(domainCd: String) = {
    val q = (for (
      anodef <- AnnotationDefinition if (anodef.domainCd === domainCd);
      ano <- Annotation if (ano.annotationCd === anodef.annotationCd)
    ) yield (anodef, ano)).sortBy { case (anodef, ano) => anodef.annotationCd }
    db.run(q.result)
  }

  def filterByDomainCd(domainCd: String) = {
    val action = AnnotationDefinition.filter(t => t.domainCd === domainCd).result
    db.run(action.head)
  }
}