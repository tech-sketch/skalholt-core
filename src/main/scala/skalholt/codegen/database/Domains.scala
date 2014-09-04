package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.common.BaseDatabase.profile.simple._

object Domains extends AbstractDomains {

  def truncate = {
    truncateTable("DOMAIN")
  }

}