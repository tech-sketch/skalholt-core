package skalholt.codegen.util

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class StringUtilSpec extends Specification {

  "StringUtils " should {
    "decapitalize test" in {
      StringUtil.decapitalize("Abcd") === "abcd"
      StringUtil.decapitalize("AbcdEfg") === "abcdEfg"
      StringUtil.decapitalize("ABcdEfg") === "ABcdEfg"
      StringUtil.decapitalize("A") === "a"
      StringUtil.decapitalize("AB") === "AB"
      StringUtil.decapitalize("ABC") === "ABC"
      StringUtil.decapitalize("") === ""
      StringUtil.decapitalize(null) === null
    }

    "capitalize test" in {
      StringUtil.capitalize("abcd") === "Abcd"
      StringUtil.capitalize("abcdEfg") === "AbcdEfg"
      StringUtil.capitalize("aBcdEfg") === "ABcdEfg"
      StringUtil.capitalize("a") === "A"
      StringUtil.capitalize("ab") === "Ab"
      StringUtil.capitalize("abc") === "Abc"
      StringUtil.capitalize("aB") === "AB"
      StringUtil.capitalize("") === ""
      StringUtil.capitalize(null) === null
    }

    "camelize test" in {
      StringUtil.camelize("ABCD") === "Abcd"
      StringUtil.camelize("ABCD_EFG") === "AbcdEfg"
      StringUtil.camelize("AB_CD_EFG") === "AbCdEfg"
      StringUtil.camelize("A") === "A"
      StringUtil.camelize("ab") === "Ab"
      StringUtil.camelize("ABC") === "Abc"
      StringUtil.camelize("A_B") === "AB"
      StringUtil.camelize("") === ""
      StringUtil.camelize(null) === null
    }

      "decamelize test" in {
      StringUtil.decamelize("Abcd") === "ABCD"
      StringUtil.decamelize("AbcdEfg") === "ABCD_EFG"
      StringUtil.decamelize("AbCdEfg") === "AB_CD_EFG"
      StringUtil.decamelize("A") === "A"
      StringUtil.decamelize("Ab") === "AB"
      StringUtil.decamelize("Abc") === "ABC"
      StringUtil.decamelize("AB") === "A_B"
      StringUtil.decamelize("") === ""
      StringUtil.decamelize(null) === null
    }
}
}
