package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._

object ScreenActions extends AbstractScreenActions {

  def truncate = {
    truncateTable("SCREEN_ACTION")
  }

  def findByScreenId(screenId: String) = database.withTransaction { implicit session: Session =>
    ScreenAction.filter(_.screenId === screenId).sortBy(_.actionId).list
  }

}