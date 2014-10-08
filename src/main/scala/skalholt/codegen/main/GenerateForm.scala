package skalholt.codegen.main

import skalholt.codegen.database.{ Screens, ScreenItems, ScreenActions, ScreenEntitys }
import skalholt.codegen.util.StringUtil._
import skalholt.codegen.templates.FormTemplate._
import skalholt.codegen.util.FileUtil
import skalholt.codegen.constants.GenConstants._
import skalholt.codegen.database.common.Tables.{ ScreenRow, ScreenItemRow, DomainRow }
import skalholt.codegen.templates._
import skalholt.codegen.templates.FormTemplate._
import skalholt.codegen.util.GenUtil
import scala.collection.immutable.Nil
import com.typesafe.scalalogging.slf4j.LazyLogging
import skalholt.codegen.database.common.DBUtils

object GenerateForm extends App with LazyLogging {

  logger.info("start GenerateForm")

  require(ScreenItems.getScreenItemCount > 0, "出力対象の画面設計書項目が存在しません。")

  // Formのジェネレート
  val Array(slickDriver, jdbcDriver, url, catalog, schema, folder, tablesPkg, _*) =
    if (args == null || args.isEmpty)
      Array(bizSlickDriver,
        bizJdbcDriver,
        bizUrl,
        bizCatalog,
        bizSchema,
        outputFolder,
        pkg,
        bizUser,
        bizPassword)
    else args

  val (user, password) = if (args.size > 7) (Some(args(7)), Some(args(8))) else (None, None)

  // 処理対象のデータリスト(ACTION_CLASS_ID/SCREEN_ID)を取得
  val actionClassIdList = Screens.find()
  actionClassIdList.foreach {
    case (Some(actionClassId), Some(subsystemNmEn)) => generateForm(jdbcDriver, url, schema, user, password)(folder, actionClassId, subsystemNmEn, tablesPkg)
    case _ =>
  }

  logger.info("end   GenerateForm")

  private def generateForm(jdbcDriver: String, url: String, schema: String, user: Option[String], password: Option[String])(folder: String, actionClassId: String, subsystemNmEn: String, tablesPkg: String) = {

    val screenItemList =
      ScreenItems.findWithScreenAndDomain(actionClassId, subsystemNmEn);

    require(screenItemList.size > 0, "ユースケース[" + actionClassId +
      "]の項目定義が存在しません。画面設計書を確認してください。")

    val screen = Screens.findByActionClassIdAndSubsystem(actionClassId, subsystemNmEn).head
    require(screen != null, "ユースケースに対応する画面情報が存在しません。")

    val screenActionList = ScreenActions.findByScreenId(screen.screenId)
    val pkgNm = screen.subsystemNmEn.get.toLowerCase

    def createMaps(screenItem: ScreenItemRow, screen: ScreenRow, domain: DomainRow) = {
      val (annotationCd, typeParam) = GenUtil.getTypeParam(domain)
      val typeName = GenUtil.getTypeName(screenItem, screen, annotationCd)
      val mapValue = GenUtil.addOptional(screenItem, screen, typeName + parenthesis(typeParam.map(p => s"${p.vname} = ${p.vvalue}").mkString(", ")))
      Mappings(screenItem(4).get, screenItem(6).get,mapValue)
    }

    val (entityImport, entityNm) = ScreenEntitys.filterByScreen(screen.screenId) match {
      case Some(se) => se.entityNmJa match {
        case Some(entity) => (List(s"import ${tablesPkg}.Tables.${capitalize(entity)}Row"), entity)
        case _ => (List.empty, "")
      }
      case _ => (List.empty, "")
    }

    val sqlImport =
      if (screenItemList.exists {
        case (screenItem, screen, domain) =>
          domain.dataType == Some("sqlDate") || domain.dataType == Some("sqlTimestamp")
      }) List("import java.sql.Date")
      else List.empty
    val condImport =
      if (screenItemList.exists {
        case (screenItem, screen, domain) =>
          !screenItem(22).isEmpty
      }) List(
        "import scala.annotation.meta.field",
        "import utils.annotations.searchcondition.{ Eq => ==, Ne => !=, Lt => <, Le => <=, Gt => >, Ge => >=, Contains => contains, StartWith => startwith, EndWith => endwith, In => in }")
      else List.empty

    val imports = entityImport ::: sqlImport ::: condImport

    val allColumns = DBUtils.getColumns(jdbcDriver, url, schema, user, password)(entityNm)
    val columnLength = allColumns.length
    val columns = allColumns.filter(!_.options.exists(o => o.toString.eq("AutoInc")))
    val keyColumns = allColumns.filter(_.options.exists(o => o.toString.eq("AutoInc")))

    val mappings: List[Mappings] = screenItemList
      .map { case (screenItem, screen, domain) => createMaps(screenItem, screen, domain) }
    val params = screenItemList
      .map { case (screenItem, screen, domain) => GenUtil.createRows(screenItem, screen, domain) }

    val itemIsMatch = GenUtil.isMatch(allColumns, params)

    val paramsExcluded = params
      .filter(p => !allColumns.filter(_.options.exists(o => o.toString.eq("AutoInc"))).exists(_.name == decamelize(p.iname)))
    val mappingsExcluded = mappings
      .filter(p => !allColumns.filter(_.options.exists(o => o.toString.eq("AutoInc"))).exists(_.name == decamelize(p.iname)))

    val forms =
      if (screen.screenType == Some("Update"))
        Forms(pkgNm, imports, GenUtil.nested(mappings), actionClassId, GenUtil.nested(params), entityNm, screen.screenType.get, allColumns.toList, itemIsMatch)
      else
        Forms(pkgNm, imports, GenUtil.nested(mappingsExcluded), actionClassId, GenUtil.nested(paramsExcluded), entityNm, screen.screenType.get, allColumns.toList, itemIsMatch)

    val str = formTemplate(forms)

    FileUtil.createFile(str, s"${folder}/app/forms/$pkgNm", capitalize(actionClassId) + "Form.scala", overrideForm)
  }
}
