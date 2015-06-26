package skalholt.codegen.database.common

import slick.driver.JdbcDriver
import skalholt.codegen.constants.GenConstants._
import com.typesafe.scalalogging.slf4j.LazyLogging

trait Profile extends LazyLogging{
  def getProfile: JdbcDriver = singleton[JdbcDriver](genSlickDriver)
  logger.debug("profile:"+getProfile)

  private def singleton[T](name: String)(implicit man: Manifest[T]): T ={
    Class.forName(name + "$").getDeclaredField("MODULE$").get(man.runtimeClass).asInstanceOf[T]
  }
}