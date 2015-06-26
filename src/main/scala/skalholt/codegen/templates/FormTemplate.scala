package skalholt.codegen.templates

import scala.collection.immutable.Map
import skalholt.codegen.util.StringUtil._
import skalholt.codegen.util.MethodParam
import slick.model.Column

case class Mappings(name: String, iname: String, mapValue: String)
case class Forms(pkgNm: String, imports: List[String], mappings: Seq[Seq[Mappings]], actionClassId: String, params: Seq[Seq[MethodParam]], entityNm: String, screenType: String, columns: List[Column], isMatch : Boolean)
object FormTemplate {
  def formTemplate(f: Forms) =
    s"""package forms.${f.pkgNm}

import play.api.data._
import play.api.data.Forms._
${
      f.imports.mkString("""
""")
    }

${// Tableのカラム数が15を超える場合はcaseクラスを2階層にする。
      if (f.params.length > 1) {
        f.params.zipWithIndex.map {
          case (p, index) =>
            s"""case class ${capitalize(f.actionClassId)}Data${index}(${
              p.map(p => s"""
  ${
                if (p.searchConditionKb.isEmpty || p.searchConditionKb.get.isEmpty) ""
                else s"""@(${p.searchConditionKb.get} @field) """
              }${p.iname}: ${p.ptype}""").mkString(""",""")
            })

"""
        }.mkString("")
      } else ""
    }case class ${capitalize(f.actionClassId)}Data(${
      f.params.flatten.map(p => s"""
  ${
        if (p.searchConditionKb.isEmpty || p.searchConditionKb.get.isEmpty) ""
        else s"""@(${p.searchConditionKb.get} @field) """
      }${p.iname}: ${p.ptype}""").mkString(""",""")
    })

trait ${capitalize(f.actionClassId)}Form {
  val ${decapitalize(f.actionClassId)}Form = Form(
    ${
      if (f.mappings.length > 1) {
        "mapping(" +
          f.mappings.zipWithIndex.map {
            case (m, index) =>
              s"""
      "_${index}" -> mapping(
        """ +
                m.map(m => "\"" + m.name + "\" -> " + m.mapValue).mkString(""",
        """) +
                s"""
        )(${capitalize(f.actionClassId)}Data${index}.apply)(${capitalize(f.actionClassId)}Data${index}.unapply)"""
          }.mkString(""",""") +
          s"""
      )(( ${
            f.mappings.zipWithIndex.map {
              case (m, index) =>
                s"_${index}"
            }.mkString(", ")
          }) => ${if (f.screenType == "Search" || !f.isMatch) { s"${capitalize(f.actionClassId)}Data(" } else s"${capitalize(f.entityNm)}Row("}${
            f.mappings.zipWithIndex.map {
              case (m, index) =>
                m.map(f => s"_${index}.${f.name}").mkString(", ")
            }.mkString(", ")
          }))
      ((f) => Some((${
            f.mappings.zipWithIndex.map {
              case (m, index) =>
                if (f.screenType == "Search" || !f.isMatch) {
                  s"${capitalize(f.actionClassId)}Data${index}(${
                    m.map(f => s"f.${f.name}").mkString(", ")
                  })"
                } else {
                  s"${capitalize(f.actionClassId)}Data${index}(${
                    m.zipWithIndex.map { case (f, i) => s"f(${i + index * 15})" }.mkString(", ")
                  })"
                }
            }.mkString(", ")
          }))))"""
      } else {
        """mapping(
      """ +
          f.mappings(0).map(m => "\"" + m.name + "\" -> " + m.mapValue).mkString(""",
      """) +
          s"""
      )${
            if (f.screenType == "Search" || !f.isMatch) {
              s"(${capitalize(f.actionClassId)}Data.apply)(${capitalize(f.actionClassId)}Data.unapply)"
            } else {
              s"(${capitalize(f.entityNm)}Row.apply)(${capitalize(f.entityNm)}Row.unapply)"
            }
          })"""
      }
    }
}"""

}
