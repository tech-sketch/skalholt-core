package skalholt.codegen.templates

import skalholt.codegen.util.StringUtil._
import skalholt.codegen.util.Row
import scala.slick.model.Column

case class Link(pkgNm: String, fwAction: String, param: String, icon: String)
case class Views(actionClassId: String, submitActionClassId: String, submitActionId: String, tableTitle: String, rows: List[Row], buttons: List[String], pkgNm: String, pkg: String, entityNm: String, columns: List[Column], resultItems: List[String])
object ViewTemplate {
  def searchTemplate(v: Views, links: List[Link]) =
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
      v.buttons.map(button => s"""<input type="submit" value="${button}" class="btn btn-info">""").mkString("""
          """)
    }
        </div>
      </fieldset>

      @if(${v.entityNm}s) {
      <fieldset>
        <legend>results</legend>
        <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover table-condensed">
          <thead>
            <tr>
              ${
      v.resultItems.map(r => s"""<th>${r}</th>""").mkString(s"""
              """)
    }
              <th>action</th>
            </tr>
          </thead>
          <tbody>
            @for((${v.entityNm}) <- ${v.entityNm}s){
              <tr>
                ${
      v.resultItems.zipWithIndex.map { case (r, index) => s"""<td>@${if (v.rows.length > 22) s"${v.entityNm}(${index})" else s"${v.entityNm}.${r}"}</td>""" }.mkString(s"""
                """)
    }
                <td>${
      links.map(l => s"""
                  <a href="@controllers.${l.pkgNm.toLowerCase}.routes.${l.fwAction}(${l.param})">
                    <i class="${l.icon}"></i>
                  </a>""").mkString("")
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
    s"""@(${decapitalize(v.actionClassId)}Form: Form[${if (isMatch(v.columns, v.rows)) s"${v.pkg}.Tables.${capitalize(v.entityNm)}Row" else s"forms.${v.pkgNm.toLowerCase}.${capitalize(v.actionClassId)}Data"}])(implicit flash: Flash)

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
      v.buttons.map(button => s"""<input type="submit" value="${button}" class="btn btn-info">""").mkString("""
          """)
    }
        </div>
      </fieldset>
    </div>
  }
}"""

  def updateTemplate(v: Views, param: String, paramNm: String) =
    s"""@(${decapitalize(v.actionClassId)}Form: Form[${if (isMatch(v.columns, v.rows)) s"${v.pkg}.Tables.${capitalize(v.entityNm)}Row" else s"forms.${v.pkgNm.toLowerCase}.${capitalize(v.actionClassId)}Data"}], ${param})(implicit flash: Flash)

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
          }  @helper.${r.ptype}(${v.actionClassId}Form("${
            if (v.rows.length > 22) s"""_${index / 15}.${r.pname}"), ${r.poptions}${
              if (r.poptions != "") "," else ""
            } '_label -> "${r.pname}""""
            else s"""${r.pname}")${
              if (r.poptions != "") s", ${r.poptions}"
              else ""
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
        ${
      v.buttons.map(button => s"""<input type="submit" value="${button}" class="btn btn-info">""").mkString("""
          """)
    }
      </fieldset>
    </div>
  }
}"""

  def isMatch(cols: Seq[Column], params: Seq[Row]): Boolean =
    if (cols.length != params.length) false else !cols.zip(params).exists {
      case (c, p) => (c.nullable, p.requireKbn) match {
        case (false, x) if x == "true" => false
        case (false, x) => true
        case (true, x) if x == "true" => true
        case (true, x) => false
      }
    }
}