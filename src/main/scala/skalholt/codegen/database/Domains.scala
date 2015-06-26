package skalholt.codegen.database

import skalholt.codegen.database.common.Tables._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

object Domains extends AbstractDomains {

  def truncate = truncateTable("DOMAIN")

}