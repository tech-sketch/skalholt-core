import skalholt.codegen.main.Generate
import skalholt.codegen.constants.GenConstants._
import skalholt.codegen.main.GenerateDao

object Skalholt extends App {

  val p = GenParam(
    Some("slick.driver.H2Driver"),
    Some("org.h2.Driver"),
    Some("jdbc:h2:tcp://localhost:9092/skalholt"),
    Some("sa"),
    Some(""),
    Some("SAMPLE"),
    None,
    Some("""C:\work\skalholt\skalholt-template"""),
    Some("models"),
    None)
  Generate.all(p)
  //Generate.generate(p)
  //Generate.importData(p)

}
