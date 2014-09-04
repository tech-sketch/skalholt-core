package skalholt.codegen.main
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class GenerateSpec extends Specification {
  "Application" should {

    "generate all code" in {

      GenerateForm.main(null)
      GenerateDao.main(null)
      GenerateController.main(null)
      GenerateRoutes.main(null)
      GenerateMenu.main(null)
      GenerateView.main(null)

      "" === ""
    }
  }

}
