package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ScreenActions extends AbstractScreenActions {

  def truncate = truncateTable("SCREEN_ACTION")

  def findByScreenId(screenId: String): Future[Seq[ScreenActionRow]] = {
    val q = ScreenAction.filter(_.screenId === screenId).sortBy(_.actionId)
    db.run(q.result)
  }

}