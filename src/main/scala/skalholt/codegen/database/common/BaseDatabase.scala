package skalholt.codegen.database.common

import slick.driver.JdbcDriver

object BaseDatabase extends Profile {
  lazy val profile: JdbcDriver = getProfile
  import profile.api._

  abstract class BaseTable[T](_tableTag: Tag, _schemaName: Option[String], _tableName: String) extends Table[T](_tableTag, _schemaName, _tableName)
}