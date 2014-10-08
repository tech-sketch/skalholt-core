package skalholt.codegen.templates

import scala.slick.model.Column
import skalholt.codegen.util.StringUtil._
import skalholt.codegen.util.MethodParam
import skalholt.codegen.util.MethodParam

case class Controllers(pkgNm: String, entityNm: String, actionClassId: String, actionNms: List[String], keys: List[MethodParam], tablePkg: String, rows: List[MethodParam], columns: List[Column], isMatch: Boolean)
object ControllerTemplate {

  /** Controller */
  val controllerTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}

import ${c.actionClassId.toLowerCase}.{ Index${if (c.actionNms.isEmpty) "" else ", "}${c.actionNms.mkString(", ")} }

object ${capitalize(c.actionClassId)} extends Index${if (c.actionNms.isEmpty) "" else " with "}${c.actionNms.mkString(" with ")}
"""

  /** Controller XxxCreate Index */
  val indexCreateTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}.${c.actionClassId.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import logics.${c.entityNm.toLowerCase}.IndexLogic
import forms.${c.pkgNm}.${capitalize(c.actionClassId)}Form

trait Index extends Controller with ${capitalize(c.actionClassId)}Form {

  def index = DBAction.transaction { implicit request =>
    IndexLogic.logic
    Ok(views.html.${c.pkgNm}.${c.actionClassId}(${c.actionClassId}Form))
  }
}"""

  /** Controller XxxUpdate Index */
  val indexUpdateTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}.${c.actionClassId.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import logics.${c.entityNm.toLowerCase}.IndexLogic
import daos.${capitalize(c.entityNm)}s
import ${c.tablePkg}.Tables._
import forms.${c.pkgNm}.{ ${capitalize(c.actionClassId)}Form, ${capitalize(c.actionClassId)}Data }

trait Index extends Controller with ${capitalize(c.actionClassId)}Form {

  def index(${c.keys.map(p => s"${p.pname}: ${p.ptype}").mkString(", ")}) = DBAction.transaction { implicit request =>
    IndexLogic.logic(${c.keys.map(p => s"${p.pname}").mkString(", ")}) match {
      case Some(${c.entityNm}) =>
        ${
      if (c.isMatch)
        ""
      else
        s"val ${c.entityNm}Form = ${
          capitalize(c.actionClassId)
        }Data(${
          c.rows.zipWithIndex.map {
            case (r, index) => c.columns.filter(col => decapitalize(camelize(col.name)) == r.iname).headOption match {
              case Some(col) =>
                talbe2Form(col, r, s"${c.entityNm}${
                  if (c.rows.length <= 15) s".${r.iname}"
                  else s"($index)"
                }")
              case _ =>
                "None"
            }
          }.mkString(" ,")
        })"
    }
        Ok(views.html.${c.pkgNm}.${c.actionClassId}(${c.actionClassId}Form.fill(${if (c.isMatch) s"${c.entityNm}" else s"${c.entityNm}Form"}), ${c.keys.map(p => s"${p.pname}").mkString(", ")}))
      case _ =>
        BadRequest(views.html.${c.pkgNm}.${c.actionClassId}(
          ${c.actionClassId}Form.withGlobalError("No data found.").bindFromRequest, ${c.keys.map(p => s"${p.pname}").mkString(", ")}))
    }
  }
}"""

  /** Controller XxxSearch Index */
  val indexSearchTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}.${c.actionClassId.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import logics.${c.entityNm.toLowerCase}.IndexLogic
import forms.${c.pkgNm}.${capitalize(c.actionClassId)}Form

trait Index extends Controller with ${capitalize(c.actionClassId)}Form {

  def index = DBAction.transaction { implicit request =>
    IndexLogic.logic
    Ok(views.html.${c.pkgNm}.${c.actionClassId}(${c.actionClassId}Form, null))
  }
}"""

  /** Controller Default Index */
  val indexTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}.${c.actionClassId.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import logics.${c.entityNm.toLowerCase}.IndexLogic
import forms.${c.pkgNm}.${capitalize(c.actionClassId)}Form

trait Index extends Controller with ${capitalize(c.actionClassId)}Form {

  def index = DBAction.transaction { implicit request =>
    Ok(views.html.${c.pkgNm}.${c.actionClassId}(${c.actionClassId}Form))
  }
}"""

  /** Controller CreateLogic */
  val createTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}.${c.actionClassId.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import logics.${c.entityNm.toLowerCase}.CreateLogic
import forms.${c.pkgNm}.${capitalize(c.actionClassId)}Form

trait Create extends Controller with ${capitalize(c.actionClassId)}Form {

  def create = DBAction.transaction { implicit request =>
    ${c.actionClassId}Form.bindFromRequest.fold(
      hasErrors = { form =>
        BadRequest(views.html.${c.pkgNm}.${c.actionClassId}(form))
      },
      success = { form =>
        CreateLogic.logic(form)
        Redirect(controllers.${c.pkgNm}.routes.${capitalize(c.actionClassId)}.index)
          .flashing("success" -> "Registration was successful.")
      })
  }
}
"""

  /** Controller UpdateLogic */
  def updateTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}.${c.actionClassId.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import daos.${capitalize(c.entityNm)}s
import models.Tables._
import logics.${c.entityNm.toLowerCase}.UpdateLogic
import forms.${c.pkgNm}.${capitalize(c.actionClassId)}Form

trait Update extends Controller with ${capitalize(c.actionClassId)}Form {

  def update(${c.keys.map(p => s"${p.pname}: ${p.ptype}").mkString(", ")}) = DBAction.transaction { implicit request =>
    ${c.actionClassId}Form.bindFromRequest.fold(
      hasErrors = { form =>
        BadRequest(views.html.${c.pkgNm}.${c.actionClassId}(form, ${c.keys.map(p => s"${p.pname}").mkString(", ")}))
      },
      success = { form =>
        UpdateLogic.logic(form) match {
          case count: Int if (count > 0) =>
            Redirect(controllers.${c.pkgNm}.routes.${capitalize(c.actionClassId)}.index(${c.keys.map(p => s"${p.pname}").mkString(", ")}))
              .flashing("success" -> "Update was successful.")
          case _ =>
            Redirect(controllers.${c.pkgNm}.routes.${capitalize(c.actionClassId)}.index(${c.keys.map(p => s"${p.pname}").mkString(", ")}))
              .flashing("error" -> "Update was not successful.")
        }
      })
  }
}
"""

  /** Controller SearchLogic */
  def searchTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}.${c.actionClassId.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import daos.${capitalize(c.entityNm)}s
import models.Tables._
import logics.${c.entityNm.toLowerCase}.SearchLogic
import forms.${c.pkgNm}.${capitalize(c.actionClassId)}Form

trait Search extends Controller with ${capitalize(c.actionClassId)}Form {

  def search = DBAction.transaction { implicit request =>
    ${c.actionClassId}Form.bindFromRequest.fold(
      hasErrors = { form =>
        BadRequest(views.html.${c.pkgNm}.${c.actionClassId}(form, List.empty[${capitalize(c.entityNm)}Row]))
      },
      success = { form =>
        SearchLogic.logic(form) match {
          case ${c.entityNm}s if (${c.entityNm}s.length > 0) =>
            Ok(views.html.${c.pkgNm}.${c.actionClassId}(${c.actionClassId}Form.bindFromRequest, ${c.entityNm}s))
          case _ =>
            BadRequest(views.html.${c.pkgNm}.${c.actionClassId}(
              ${c.actionClassId}Form.withGlobalError("No data found.").bindFromRequest, List.empty[${capitalize(c.entityNm)}Row]))
        }
      })
  }
}
"""

  /** Controller DeleteLogic */
  def deleteTemplate = (c: Controllers) =>
    s"""package controllers.${c.pkgNm}.${c.actionClassId.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import daos.${capitalize(c.entityNm)}s
import logics.${c.entityNm.toLowerCase}.DeleteLogic

trait Delete extends Controller {

  def delete(${c.keys.map(p => s"${p.pname}: ${p.ptype}").mkString(", ")}) = DBAction.transaction { implicit request =>
    DeleteLogic.logic(${c.keys.map(p => s"${p.pname}").mkString(", ")}) match {
      case count if (count > 0) =>
        Redirect(controllers.${c.pkgNm}.routes.${capitalize(c.actionClassId)}.index)
          .flashing("success" -> "Delete was successful.")
      case _ =>
        Redirect(controllers.${c.pkgNm}.routes.${capitalize(c.actionClassId)}.index)
          .flashing("error" -> "Delete was not successful.")
    }
  }
}
"""

  /** Logic IndexLogic */
  def indexCreateLogicTemplate = (c: Controllers) =>
    s"""package logics.${c.entityNm.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import daos.${capitalize(c.entityNm)}s
import models.Tables.${capitalize(c.entityNm)}Row

object IndexLogic extends Controller {

  def logic = {
    None
  }

  def logic(${c.keys.map(p => s"${p.pname}: ${p.ptype}").mkString(" ,")})(implicit request: DBSessionRequest[AnyContent]): Option[${capitalize(c.entityNm)}Row] = {
    ${capitalize(c.entityNm)}s.filterById(${c.keys.map(p => if (p.pmaptype == "bigDecimal") s"BigDecimal(${p.pname})" else s"${p.pname}").mkString(" ,")})
  }
}
"""

  /** Logic CreateLogic */
  def createLogicTemplate = (c: Controllers) =>
    s"""package logics.${c.entityNm.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import daos.${capitalize(c.entityNm)}s
import models.Tables._
import models.Tables.${capitalize(c.entityNm)}Row
${if (c.isMatch) "" else s"import forms.${c.pkgNm.toLowerCase}.${capitalize(c.actionClassId)}Data"}

object CreateLogic extends Controller {

  def logic(${if (c.isMatch) s"${c.entityNm}Row: ${capitalize(c.entityNm)}Row" else s"data: ${capitalize(c.actionClassId)}Data"})(implicit request: DBSessionRequest[AnyContent]): Int = {
    ${
      if (c.isMatch) "" else s"val ${c.entityNm}Row = ${capitalize(c.entityNm)}Row(${
        c.columns.map {
          col =>
            if (col.options.exists(o => o.toString.eq("AutoInc"))) "0"
            else c.rows.filter(_.iname == decapitalize(camelize(col.name))).headOption match {
              case Some(row) => form2Table(col, row, s"data.${decapitalize(camelize(col.name))}")
              case _ => "None"
            }
        }.mkString(" ,")
      })"
    }
    ${capitalize(c.entityNm)}s.insert(${c.entityNm}Row)
  }
}
"""
  /** Logic UpdateLogic */
  def updateLogicTemplate = (c: Controllers) =>
    s"""package logics.${c.entityNm.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import daos.${capitalize(c.entityNm)}s
import models.Tables.${capitalize(c.entityNm)}Row
${if (c.isMatch) "" else s"import forms.${c.pkgNm.toLowerCase}.${capitalize(c.actionClassId)}Data"}

object UpdateLogic extends Controller {
  def logic(${if (c.isMatch) s"${c.entityNm}Row: ${capitalize(c.entityNm)}Row" else s"data: ${capitalize(c.actionClassId)}Data"})(implicit request: DBSessionRequest[AnyContent]): Int = {
    ${
      if (c.isMatch) "" else s"val ${c.entityNm}Row = ${capitalize(c.entityNm)}Row(${
        c.columns.map {
          col =>
            c.rows.filter(_.iname == decapitalize(camelize(col.name))).headOption match {
              case Some(row) => form2Table(col, row, s"data.${decapitalize(camelize(col.name))}")
              case _ => "None"
            }
        }.mkString(" ,")
      })"
    }
    ${capitalize(c.entityNm)}s.update(${c.entityNm}Row)
  }
}
"""

  /** Logic SearchLogic */
  def searchLogicTemplate = (c: Controllers) =>
    s"""package logics.${c.entityNm.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import daos.${capitalize(c.entityNm)}s
import models.Tables._
import models.Tables.${capitalize(c.entityNm)}Row
import forms.${c.pkgNm}.${capitalize(c.actionClassId)}Data

object SearchLogic {

  def logic(data: ${capitalize(c.actionClassId)}Data)(implicit request: DBSessionRequest[AnyContent]): List[${capitalize(c.entityNm)}Row] = {
    ${capitalize(c.entityNm)}s.filter(data)
  }
}
"""

  /** Logic DeleteLogic */
  def deleteLogicTemplate = (c: Controllers) =>
    s"""package logics.${c.entityNm.toLowerCase}

import play.api.db.slick._
import play.api.mvc._
import daos.${capitalize(c.entityNm)}s

object DeleteLogic extends Controller {
  def logic(${c.keys.map(p => s"${p.pname}: ${p.ptype}").mkString(" ,")})(implicit request: DBSessionRequest[AnyContent]): Int = {
    ${capitalize(c.entityNm)}s.remove(${c.keys.map(p => if (p.pmaptype == "bigDecimal") s"BigDecimal(${p.pname})" else s"${p.pname}").mkString(" ,")})
  }
}
"""
  def form2Table(col: Column, param: MethodParam, value: String): String =
    (col.nullable, param.ptype) match {
      case (_, x) if x.isEmpty() => value
      case (false, "Option[Date]")  => s"""${value}.getOrElse(utils.DateUtils.newSDate)"""
      case (false, "Option[Int]")  => s"""${value}.getOrElse(0)"""
      case (false, "Option[Long]")  => s"""${value}.getOrElse(0)"""
      case (false, "Option[BigDecimal]")  => s"""${value}.getOrElse(0)"""
      case (false, x) if x.startsWith("Option[") && x.endsWith("]") => s"""${value}.getOrElse("")"""
      case (false, x) => value
      case (true, x) if x.startsWith("Option[") && x.endsWith("]") => value
      case (true, x) => s"Some(${value})"
    }

  def talbe2Form(col: Column, param: MethodParam, value: String): String =
    (col.nullable, param.ptype) match {
      case (_, x) if x.isEmpty() => value
      case (false, x) if x.startsWith("Option[") && x.endsWith("]") => s"Some(${value})"
      case (false, x) => value
      case (true, x) if x.startsWith("Option[") && x.endsWith("]") => value
      case (true, "Date") => s"""${value}.getOrElse(utils.DateUtils.newSDate)"""
      case (true, "Int") => s"""${value}.getOrElse(0)"""
      case (true, "Long") => s"""${value}.getOrElse(0)"""
      case (true, "BigDecimal") => s"""${value}.getOrElse(0)"""
      case (true, x) => s"""${value}.getOrElse("")"""
    }

}

