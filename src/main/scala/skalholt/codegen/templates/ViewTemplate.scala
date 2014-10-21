package skalholt.codegen.templates

import skalholt.codegen.util.StringUtil._
import skalholt.codegen.util.Row
import scala.slick.model.Column

case class Action(bltype: String, value: String, pkgNm: String, fwAction: String, param: String, icon: String)
case class Views(actionClassId: String, submitActionClassId: String, submitActionId: String, tableTitle: String, rows: List[Row], buttons: List[Action], pkgNm: String, pkg: String, entityNm: String, columns: List[Column], resultItems: (List[(String, String)], List[Action]), isMatch: Boolean)
object ViewTemplate {
  def searchTemplate(v: Views) =
    s"""@(${decapitalize(v.actionClassId)}Form: Form[forms.${v.pkgNm.toLowerCase}.${capitalize(v.actionClassId)}Data], ${decapitalize(v.entityNm)}s: List[${v.pkg}.Tables.${capitalize(v.entityNm)}Row])(implicit flash: Flash)

@import BootstrapHelper._

@main("${capitalize(v.actionClassId)}", ${decapitalize(v.actionClassId)}Form) {
  @helper.form(action = controllers.${v.pkgNm.toLowerCase}.routes.${capitalize(v.submitActionClassId)}.${v.submitActionId}(), 'role -> "form", 'class -> "form-horizontal"){
    <div class="container">
      <fieldset>
        <legend>${capitalize(v.tableTitle)}</legend>
        ${
      if (v.rows.length > 5) """<div class="row">"""
      else ""
    }
          ${
      v.rows.zipWithIndex.map {
        case (r, index) =>
          s"""${
            if (v.rows.length > 5 && index % (v.rows.length / 2) == 0)
              """<div class="col-md-6">
          """
            else ""
          }  @helper.${r.ptype}(${v.actionClassId}Form("${
            if (v.rows.length > 22) s"""_${index / 15}.${r.pname}"), ${r.poptions}${
              if (r.poptions != "") "," else ""
            } '_label -> "${r.pname}""""
            else s"""${r.pname}")${
              if (r.poptions != "") s", ${r.poptions}" else ""
            }"""
          })${
            if (v.rows.length > 5 && index % (v.rows.length / 2) == (v.rows.length / 2) - 1) """
          </div>"""
            else ""
          }"""
      }.mkString(s"""
          """)
    }
        ${if (v.rows.length > 5) """</div>""" else ""}
        <div align = "right">
          ${
      v.buttons.map(buttonOrLink).mkString("""
          """)
    }
        </div>
      </fieldset>

      @if(${v.entityNm}s) {
      <fieldset>
        <legend>results</legend>
        <div class="table-responsive">
        <table class="table table-striped table-hover table-condensed">
          <thead>
            <tr>
              ${
      v.resultItems._1.map { case (out, in) => s"""<th>${out}</th>""" }.mkString(s"""
              """)
    }
              <th>action</th>
            </tr>
          </thead>
          <tbody>
            @for((${v.entityNm}) <- ${v.entityNm}s){
              <tr>
                ${
      v.resultItems._1.zipWithIndex.map { case ((out, in), index) => s"""<td>@${if (v.rows.length > 22) s"${v.entityNm}(${index})" else s"${v.entityNm}.${in}"}</td>""" }.mkString(s"""
                """)
    }
                <td>${
      v.resultItems._2.map(buttonOrLink).mkString("""
          """)
    }
                </td>
              </tr>
            }
          </tbody>
        </table>
      </div>
      </fieldset>
      }
    </div>
  }
}"""

  def createTemplate(v: Views) =
    s"""@(${decapitalize(v.actionClassId)}Form: Form[${if (v.isMatch) s"${v.pkg}.Tables.${capitalize(v.entityNm)}Row" else s"forms.${v.pkgNm.toLowerCase}.${capitalize(v.actionClassId)}Data"}])(implicit flash: Flash)

@import BootstrapHelper._

@main("${capitalize(v.actionClassId)}", ${decapitalize(v.actionClassId)}Form) {
  @helper.form(action = controllers.${v.pkgNm.toLowerCase}.routes.${capitalize(v.submitActionClassId)}.${v.submitActionId}(), 'role -> "form", 'class -> "form-horizontal"){
    <div class="container">
      <fieldset>
        <legend>${capitalize(v.tableTitle)}</legend>
        ${
      if (v.rows.length > 5) """<div class="row">"""
      else ""
    }
          ${
      v.rows.zipWithIndex.map {
        case (r, index) =>
          s"""${
            if (v.rows.length > 5 && index % (v.rows.length / 2) == 0)
              """<div class="col-md-6">
          """
            else ""
          }  @helper.${r.ptype}(${v.actionClassId}Form("${
            if (v.rows.length > 22)
              s"""_${index / 15}.${r.pname}"), ${r.poptions}${
                if (r.poptions != "") "," else ""
              } '_label -> "${r.pname}""""
            else s"""${r.pname}")${
              if (r.poptions != "") s""", ${r.poptions}""" else ""
            }"""
          })${
            if (v.rows.length > 5 && index % (v.rows.length / 2) == (v.rows.length / 2) - 1) """
          </div>"""
            else ""
          }"""
      }.mkString(s"""
          """)
    }
        ${if (v.rows.length > 5) """</div>""" else ""}
        <div align = "right">
          ${
      v.buttons.map(buttonOrLink).mkString("""
          """)
    }
        </div>
      </fieldset>
    </div>
  }
}"""

  def updateTemplate(v: Views, param: String, paramNm: String) =
    s"""@(${decapitalize(v.actionClassId)}Form: Form[${if (v.isMatch) s"${v.pkg}.Tables.${capitalize(v.entityNm)}Row" else s"forms.${v.pkgNm.toLowerCase}.${capitalize(v.actionClassId)}Data"}], ${param})(implicit flash: Flash)

@import BootstrapHelper._

@main("${capitalize(v.actionClassId)}", ${decapitalize(v.actionClassId)}Form) {
  @helper.form(action = controllers.${v.pkgNm.toLowerCase}.routes.${capitalize(v.submitActionClassId)}.${v.submitActionId}(${paramNm}), 'role -> "form", 'class -> "form-horizontal"){
    <div class="container">
      <fieldset>
        <legend>${capitalize(v.tableTitle)}</legend>
        ${
      if (v.rows.length > 5) """<div class="row">"""
      else ""
    }
          ${
      v.rows.zipWithIndex.map {
        case (r, index) =>
          s"""${
            if (v.rows.length > 5 && index % (v.rows.length / 2) == 0)
              """<div class="col-md-6">
          """
            else ""
          }  ${
              if(v.columns.filter(_.options.exists(o => o.toString.eq("AutoInc"))).exists(_.name == decamelize(r.iname)))
                  s"""<input type = "hidden" id="${r.pname}" name ="${r.pname}" value="@${v.actionClassId}Form("${r.pname}").value">"""
              else
            	  s"""@helper.${r.ptype}(${v.actionClassId}Form("${
            if (v.rows.length > 22) s"""_${index / 15}.${r.pname}"), ${r.poptions}${
              if (r.poptions != "") "," else ""
            } '_label -> "${r.pname}""""
            else s"""${r.pname}")${
              if (r.poptions != "") s", ${r.poptions}"
              else ""
            }"""
          })"""}${
            if (v.rows.length > 5 && index % (v.rows.length / 2) == (v.rows.length / 2) - 1) """
          </div>"""
            else ""
          }"""
      }.mkString(s"""
          """)
    }
        ${if (v.rows.length > 5) """</div>""" else ""}
        ${
      v.buttons.map(buttonOrLink).mkString("""
          """)
    }
      </fieldset>
    </div>
  }
}"""

  def buttonOrLink(button: Action) = button.bltype match {
    case typ if typ == "button" =>
      s"""<input type="submit" value="${button.value}" class="btn btn-info">"""
    case typ if typ == "link" =>
      s"""<a href="@controllers.${button.pkgNm.toLowerCase}.routes.${button.fwAction}(${button.param})">${if (button.icon.isEmpty) button.value else "<i class=\"" + button.icon + "\"></i>"}</a>"""
  }
}
