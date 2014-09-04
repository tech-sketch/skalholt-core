package skalholt.codegen.database.common

import BaseDatabase.profile.simple._
import skalholt.codegen.constants.GenConstants._
import scala.slick.jdbc.StaticQuery

abstract class AbstractDao {

  val database = Database.forURL(genUrl, genUser, genPassword, null, genDriver)

  def truncateTable(tableNm: String) = database.withSession { implicit session: Session =>
    StaticQuery.updateNA(s"""TRUNCATE TABLE ${if (genSchema.isEmpty()) "" else s""""${genSchema}".""" }"${tableNm}"""").execute
  }
}
