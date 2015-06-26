package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ScreenEntitys extends AbstractScreenEntitys {

  def truncate = truncateTable("SCREEN_ENTITY")

  def filterByScreen(screenId: String): Future[Option[ScreenEntityRow]] = {
    val q = ScreenEntity.filter(_.screenId === screenId).result.headOption
    db.run(q)
  }

}