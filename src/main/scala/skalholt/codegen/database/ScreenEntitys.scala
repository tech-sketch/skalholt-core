package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._

object ScreenEntitys extends AbstractScreenEntitys {

  def truncate = {
    truncateTable("SCREEN_ENTITY")
  }

  def filterByScreen(screenId :String): Option[ScreenEntityRow] = database.withTransaction { implicit session: Session =>
    ScreenEntity.filter(_.screenId === screenId).firstOption
  }

}