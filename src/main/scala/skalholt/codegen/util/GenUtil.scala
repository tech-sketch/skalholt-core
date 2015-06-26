package skalholt.codegen.util

import skalholt.codegen.database.common.Tables._
import skalholt.codegen.database.AnnotationDefinitions
import slick.model.Column
import skalholt.codegen.util.StringUtil._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

case class MethodParam(pname: String, iname: String, ptype: String, pmaptype: String, searchConditionKb: Option[String])
case class VerifyParam(vname: String, vvalue: String)
case class Row(pname: String, iname: String, ptype: String, pmaptype: String, poptions: String, requireKbn: String)

object GenUtil {

  def toComponent(columnType: String) = columnType match {
    case "String" => Some("text")
    case "java.sql.Date" => Some("date")
    case "java.sql.Timestamp" => Some("date")
    case "Int" => Some("text")
    case "Short" => Some("text")
    case "Long" => Some("text")
    case "scala.math.BigDecimal" => Some("text")
    case x => Some("text")
  }

  def typeToMap(value: String) = value match {
    case "String" => Some("text")
    case "java.sql.Date" => Some("sqlDate")
    case "java.sql.Timestamp" => Some("sqlTimestamp")
    case "Int" => Some("number")
    case "Short" => Some("number")
    case "Long" => Some("longNumber")
    case "scala.math.BigDecimal" => Some("bigDecimal")
    case x => Some("text")
  }

  def getColumnType(implicit screenItem: ScreenItemRow, domain: DomainRow) = {
    val dataType = Await.result(AnnotationDefinitions.filterByDomainCd(domain.domainCd), Duration.Inf).annotationCd

    val columnType = dataType match {
      case "text" => "String"
      case "sqlDate" => "java.sql.Date"
      case "sqlTimestamp" => "java.sql.Date"
      case "number" => "Int"
      case "smalInt" => "Short"
      case "longNumber" => "Long"
      case "bigDecimal" => "String"
      case _ => "String"
    }
    MethodParam(screenItem(4).get, screenItem(6).get, columnType, domain.dataType.get, screenItem(22))
  }

  def createRows(screenItem: ScreenItemRow, screen: ScreenRow, domain: DomainRow) = {
    def convOption(compType: String, requireKbn: Option[String], searchConditionKbn: Option[String]) = {
      (screen.screenType, requireKbn, searchConditionKbn) match {
        case (Some("Search"), Some("true"), Some("in")) => s"List[$compType]"
        case (Some("Search"), _, Some("in")) => s"Option[List[$compType]]"
        case (_, Some("true"), _) => compType
        case (_, _, _) => s"Option[$compType]"
      }
    }

    val dataType = Await.result(AnnotationDefinitions.filterByDomainCd(domain.domainCd), Duration.Inf).annotationCd
    val componentType = dataType match {
      case "text" => convOption("String", screenItem(15), screenItem(22))
      case "sqlDate" => convOption("Date", screenItem(15), screenItem(22))
      case "sqlTimestamp" => convOption("Date", screenItem(15), screenItem(22))
      case "number" => convOption("Int", screenItem(15), screenItem(22))
      case "smalInt" => convOption("Int", screenItem(15), screenItem(22))
      case "longNumber" => convOption("Long", screenItem(15), screenItem(22))
      case "bigDecimal" => convOption("BigDecimal", screenItem(15), screenItem(22))
      case _ => convOption("String", screenItem(15), screenItem(22))
    }

    MethodParam(screenItem(4).get, screenItem(6).get, componentType, dataType, screenItem(22))
  }

  def createRows(screenItem: ScreenItemRow, domain: DomainRow) = {
    val seqstr = """Seq(("1","abcd"),("2","efg"),("3","hijk"))"""
    val (componentType, options) = (screenItem(8), screenItem(21), domain.dataType, screenItem(15)) match {
      case (Some("checkbox"), Some(g), d, r) => ("cinputCheckboxGroup", s""""$g"""")
      case (Some("checkbox"), g, d, r) => ("inputCheckboxGroup", seqstr)
      case (Some("radio"), Some(g), d, r) => ("cinputRadioGroup", s""""$g"""")
      case (Some("radio"), g, d, r) => ("inputRadioGroup", seqstr)
      case (Some("select"), Some(g), d, Some("true")) => ("cselect", s""""$g"""")
      case (Some("select"), Some(g), d, r) => ("cselect", s""""$g", '_default -> """"")
      case (Some("select"), g, d, Some("true")) => ("select", seqstr)
      case (Some("select"), g, d, r) => ("select", s"""$seqstr, '_default -> """"")
      case (Some("selectmultiple"), Some(g), d, Some("true")) => ("cselect", s""""$g", 'multiple -> "multiple"""")
      case (Some("selectmultiple"), Some(g), d, r) => ("cselect", s""""$g", 'multiple -> "multiple", '_default -> """"")
      case (Some("selectmultiple"), g, d, Some("true")) => ("select", s"""$seqstr, 'multiple -> "multiple"""")
      case (Some("selectmultiple"), g, d, r) => ("select", s"""$seqstr, 'multiple -> "multiple", '_default -> """"")
      case (Some("password"), g, d, r) => ("inputPassword", "")
      case (Some("file"), g, d, r) => ("inputFile", "")
      case (Some("textarea"), g, d, r) => ("textarea", "")
      case (Some("date"), g, d, r) => ("inputDate", "")
      case (Some("text"), g, d, r) => ("inputText", "")
      case _ => ("inputText", "")
    }
    Row(screenItem(4).get, screenItem(6).get, componentType, domain.dataType.get, options, screenItem(15).getOrElse("false"))
  }

  def getTypeName(screenItem: ScreenItemRow, screen: ScreenRow, annotationCd: Option[String]) = {
    (annotationCd, screenItem(15)) match {
      case (Some("text"), Some("true")) => "nonEmptyText"
      case (Some("smalInt"), _) => "number"
      case (Some("sqlTimestamp"), _) => "sqlDate"
      case (Some(x), _) => x
      case _ => "text"
    }
  }

  def getAnnltatoinData(columnType: String) = columnType match {
    case "String" => List(("text", Some("100"), None, None, None, None))
    case "java.sql.Date" => List(("sqlDate", None, None, None, None, None))
    case "java.sql.Timestamp" => List(("sqlTimestamp", None, None, None, None, None))
    case "Int" => List(("number", Some("0"), Some("100"), None, None, None))
    case "Long" => List(("longNumber", Some("0"), Some("10000"), None, None, None))
    case "scala.math.BigDecimal" => List(("bigDecimal", Some("3"), Some("2"), None, None, None))
    case x => List.empty
  }

  def getValue(columnType: String)(argValue: String): String = (columnType, argValue) match {
    case ("text", _) => "100"
    case ("sqlDate", _) => ""
    case ("sqlTimestamp", _) => ""
    case ("number", "min") => "0"
    case ("number", "max") => "100"
    case ("longNumber", "min") => "0"
    case ("longNumber", "max") => "10000"
    case ("bigDecimal", "precision") => "3"
    case ("bigDecimal", "scale") => "2"
    case x => ""
  }

  def getTypeParam(domain: DomainRow) = {
    val annotationList = Await.result(AnnotationDefinitions.getAnnotationList(domain.domainCd), Duration.Inf)
    val argList = annotationList.headOption match {
      case Some((ad, a)) =>
        val anvalue = getValue(a.annotationCd)_
        (
          a.argument1, ad.definitionValue1,
          a.argument2, ad.definitionValue2,
          a.argument3, ad.definitionValue3,
          a.argument4, ad.definitionValue4,
          a.argument5, ad.definitionValue5) match {
            case (
              Some(k1), v1, Some(k2), v2, Some(k3), v3, Some(k4), v4, Some(k5), v5) =>
              List(
                VerifyParam(k1, v1.getOrElse(anvalue(k1))),
                VerifyParam(k2, v2.getOrElse(anvalue(k2))),
                VerifyParam(k3, v3.getOrElse(anvalue(k3))),
                VerifyParam(k4, v4.getOrElse(anvalue(k4))),
                VerifyParam(k5, v5.getOrElse(anvalue(k5))))
            case (
              Some(k1), v1, Some(k2), v2, Some(k3), v3, Some(k4), v4, _, _) =>
              List(
                VerifyParam(k1, v1.getOrElse(anvalue(k1))),
                VerifyParam(k2, v2.getOrElse(anvalue(k2))),
                VerifyParam(k3, v3.getOrElse(anvalue(k3))),
                VerifyParam(k4, v4.getOrElse(anvalue(k4))))
            case (
              Some(k1), v1,
              Some(k2), v2,
              Some(k3), v3, _, _, _, _) =>
              List(
                VerifyParam(k1, v1.getOrElse(anvalue(k1))),
                VerifyParam(k2, v2.getOrElse(anvalue(k2))),
                VerifyParam(k3, v3.getOrElse(anvalue(k3))))
            case (
              Some(k1), v1,
              Some(k2), v2, _, _, _, _, _, _) =>
              List(
                VerifyParam(k1, v1.getOrElse(anvalue(k1))),
                VerifyParam(k2, v2.getOrElse(anvalue(k2))))
            case (
              Some(k1), v1, _, _, _, _, _, _, _, _) =>
              List(
                VerifyParam(k1, v1.getOrElse(anvalue(k1))))
            case _ => List.empty
          }
      case _ => List.empty
    }
    val annotationCd = annotationList.headOption match {
      case Some((ad, a)) => Some(a.annotationCd)
      case _ => None
    }
    (annotationCd, argList.filter(!_.vvalue.isEmpty))
  }

  def addList(searchConditionKbn: Option[String], value: String) = {
    searchConditionKbn match {
      case Some("in") => s"list($value)"
      case _ => value
    }
  }

  def addOptional(screenItem: ScreenItemRow, screen: ScreenRow, value: String) = {
    screenItem(15) match {
      case Some("true") => addList(screenItem(22), value)
      case Some(_) => s"optional(${addList(screenItem(22), value)})"
      case _ => value
    }
  }

  def nested[A](seq: Seq[A]): Seq[Seq[A]] = {
    if (seq.length <= 15) seq :: Nil
    else seq.splitAt(15) match { case (head, next) => head +: nested(next) }
  }

  def isMatch(cols: Seq[Column], params: Seq[MethodParam]): Boolean =
    if (params.map(p => decamelize(p.iname)) == cols.map(_.name)) false else !cols.zip(params).exists {
      case (c, p) => (c.nullable, p.ptype) match {
        case (false, x) if x.startsWith("Option[") && x.endsWith("]") => true
        case (false, x) => false
        case (true, x) if x.startsWith("Option[") && x.endsWith("]") => false
        case (true, x) => true
      }
    }

  def isRowMatch(cols: Seq[Column], params: Seq[Row]): Boolean =
    if (params.map(p => decamelize(p.iname)) == cols.map(_.name)) false else !cols.zip(params).exists {
      case (c, p) => (c.nullable, p.requireKbn) match {
        case (false, x) if x == "true" => false
        case (false, x) => true
        case (true, x) if x == "true" => true
        case (true, x) => false
      }
    }

}