package skalholt.codegen.database.common

import skalholt.codegen.constants.GenConstants._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

abstract class AbstractDao {

  val db = Database.forURL(genUrl, genUser, genPassword, null, genDriver)

  def truncateTable(tableNm: String) =  {
    val schema = if (genSchema.isEmpty) "" else genSchema + "."
    val q = sqlu"TRUNCATE TABLE #${schema}#${tableNm}"
    db.run(q)
  }
}
