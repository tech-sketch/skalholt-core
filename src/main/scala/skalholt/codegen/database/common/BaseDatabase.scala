package skalholt.codegen.database.common

import scala.slick.driver.JdbcDriver
import skalholt.codegen.constants.GenConstants._

object BaseDatabase extends Profile {
  lazy val profile: JdbcDriver = getProfile
  import profile.simple._

  def withSession[T](f: (Session => T)): T = Database.forURL(genUrl, genUser, genPassword, null, genDriver).withSession(f)

  abstract class BaseTable[T](_tableTag: Tag, _schemaName: Option[String], _tableName: String) extends Table[T](_tableTag, _schemaName, _tableName)
}