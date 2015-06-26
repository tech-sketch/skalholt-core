package skalholt.codegen.main

import skalholt.codegen.database.common.DBUtils._
import slick.codegen.SourceCodeGenerator
import slick.model.Model
import skalholt.codegen.util.StringUtil._
import skalholt.codegen.database.{ Screens, ScreenItems, ScreenActions, ScreenEntitys, Domains, AnnotationDefinitions }
import skalholt.codegen.database.common.Tables.{ ScreenItemRow, ScreenRow, ScreenActionRow, ScreenEntityRow, DomainRow, AnnotationRow, AnnotationDefinitionRow }
import skalholt.codegen.constants.GenConstants._
import skalholt.codegen.constants.GenConstants.ScreenType._
import skalholt.codegen.util.GenUtil
import skalholt.codegen.database.common.DBUtils
import com.typesafe.scalalogging.slf4j.LazyLogging

object ImportDesign extends App with LazyLogging {

  // 設計情報のインポート
  val importargs =
    if (args == null)
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
  importDesign(importargs)

  logger.info("end   ImportDesign")

  private def importDesign(args: Array[String]) = {

    val Array(slickDriver, jdbcDriver, url, catalog, schema, outputDir, pkg, _*) = args
    val (user, password) = if (args.size > 7) (Some(args(7)), Some(args(8))) else (None, None)
    val model = DBUtils.getModel(jdbcDriver, url, schema, user, password)
    logger.info("----- tables -----")
    model.tables.zipWithIndex.foreach{case (table, index) => logger.info(s"${f"${index + 1}% 4d"}.${table.name.table}") }
    logger.info("------------------")

    truncates

    val importsExecute = imports(model, args(5))_
    importsExecute(Update)
    importsExecute(Search)
    importsExecute(Create)

  }

  private def truncates = {
    Screens.truncate
    ScreenItems.truncate
    ScreenActions.truncate
    ScreenEntitys.truncate
    Domains.truncate
    AnnotationDefinitions.truncate
  }

  private def imports(model: Model, folder: String)(screenType: ScreenType) = {
    val codegen = new SourceCodeGenerator(model)
    def outputByTable(table: slick.model.Table) = {
      import slick.ast.ColumnOption.PrimaryKey

      // -----Screen-----
      val tableNm = decapitalize(camelize(table.name.table))
      val screenId = s"${tableNm}${screenType}"
      val screenNm = Some(screenId)
      val jspNm = screenNm
      val useCaseNm = screenNm
      val actionClassId = screenNm
      val screenRbn = None
      val subsystemNmJa = table.name.schema
      val subsystemNmEn = subsystemNmJa
      val packageNm = table.name.catalog
      //val screenType = Some("Create")
      val authorityClassification = None
      val authorityCrud = None

      val screen = ScreenRow(screenId, screenNm, jspNm, useCaseNm, actionClassId, screenRbn, subsystemNmJa, subsystemNmEn, packageNm, Some(screenType.toString), authorityClassification, authorityCrud)
      Screens.create(screen)

      // -----ScreenItem-----
      def importScreenItem(column: slick.model.Column, itemNo: Int) = {
        //val screenId = None
        //val itemNo = 1
        val columnNm = decapitalize(camelize(column.name))
        val itemNmJa = Some(columnNm)
        val insideItemNmJa = Some(columnNm)
        val itemNmEn = itemNmJa
        val updatedItemNmEn = Some("-")
        val insideItemNmEn = insideItemNmJa
        val updatedInsideItemNmEn = Some("-")
        val component = GenUtil.toComponent(column.tpe)
        val isPk = (table.primaryKey match {
          case Some(x) => x.columns.exists(_.name == column.name)
          case None => column.options.contains(PrimaryKey)
        })
        val activeKb = if (isPk) Some("pk") else None

        val inputKb = None
        val domainCd = Some(s"${screenId}-${itemNo}")
        val updatedDomainCd = domainCd
        val displayDigit = Some("-")
        val updatedDisplayDigit = None
        val requiredKb = Some(if (column.nullable.toString == "true" || screen.screenType == Some("Search")) "false" else "true")
        val defaultDisplay = Some("-")
        val outputColumn = Some("-")
        val outputSpec = Some("-")
        val updatedOutputSpec = None
        val comments = Some("-")
        val codegrpId = None
        val searchConditionKb = screen.screenType match {
          case Some("Search") => Some("==")
          case _ => None
        }
        val updatedSearchConditionKb = searchConditionKb
        val actionId = None
        val searchresultFlag = Some("0")
        val searchresultUpdateFlag = None

        val screenItem = ScreenItemRow(screenId, itemNo, itemNmJa, insideItemNmJa, itemNmEn, updatedItemNmEn, insideItemNmEn, updatedInsideItemNmEn, component, activeKb, inputKb, domainCd, updatedDomainCd, displayDigit, updatedDisplayDigit, requiredKb, defaultDisplay, outputColumn, outputSpec, updatedOutputSpec, comments, codegrpId, searchConditionKb, updatedSearchConditionKb, actionId, searchresultFlag, searchresultUpdateFlag)
        ScreenItems.create(screenItem)

        if (screenType == Search) {
          val columnNmR = columnNm
          val itemNmJaR = Some(columnNmR)
          val insideItemNmJaR = itemNmJaR
          val itemNmEnR = itemNmJaR
          val updatedItemNmEnR = Some("-")
          val insideItemNmEnR = insideItemNmJaR
          val updatedInsideItemNmEnR = Some("-")
          val componentR = Some("label")
          val activeKbR = None
          val inputKbR = None
          val domainCdR = None
          val updatedDomainCdR = domainCdR
          val displayDigitR = Some("-")
          val updatedDisplayDigitR = Some("-")
          val requiredKbR = Some("false")
          val defaultDisplayR = Some("-")
          val outputColumnR = Some("-")
          val outputSpecR = Some("-")
          val updatedOutputSpecR = None
          val commentsR = Some("-")
          val codegrpIdR = None
          val searchConditionKbR = Some("--")
          val updatedSearchConditionKbR = None
          val actionIdR = None
          val searchresultFlagR = Some("1")
          val searchresultUpdateFlagR = None
          val screenItemR = ScreenItemRow(screenId, itemNo + 2000, itemNmJaR, insideItemNmJaR, itemNmEnR, updatedItemNmEnR, insideItemNmEnR, updatedInsideItemNmEnR, componentR, activeKbR, inputKbR, domainCdR, updatedDomainCdR, displayDigitR, updatedDisplayDigitR, requiredKbR, defaultDisplayR, outputColumnR, outputSpecR, updatedOutputSpecR, commentsR, codegrpIdR, searchConditionKbR, updatedSearchConditionKbR, actionIdR, searchresultFlagR, searchresultUpdateFlagR)
          ScreenItems.create(screenItemR)
        }

        // -----Domain-----
        //val domainCd = Some(domainCd)
        val domainNm = domainCd
        val dataType = GenUtil.typeToMap(column.tpe)
        val halfFullKb = None
        val dataLength = None
        //val displayDigit = None
        //val outputSpec = None

        val domain = DomainRow(domainCd.get, domainNm, dataType, halfFullKb, dataLength, displayDigit, outputSpec)
        Domains.create(domain)

        // -----AnnotationDefinition-----
        //val domainCd = None
        val annotationList = GenUtil.getAnnltatoinData(column.tpe)

        def importAnnotations(annotationCd: String, definitionValue1: Option[String], definitionValue2: Option[String], definitionValue3: Option[String], definitionValue4: Option[String], definitionValue5: Option[String]) = {
          val msgKey = None
          val argumentKey = None

          val annotationDefinition = AnnotationDefinitionRow(domainCd.get, annotationCd, definitionValue1, definitionValue2, definitionValue3, definitionValue4, definitionValue5, msgKey, argumentKey)
          AnnotationDefinitions.create(annotationDefinition)
        }
        annotationList.foreach {
          case (annotationCd, definitionValue1, definitionValue2, definitionValue3, definitionValue4, definitionValue5) =>
            importAnnotations(annotationCd, definitionValue1, definitionValue2, definitionValue3, definitionValue4, definitionValue5)
        }

      }

      def importScreenItemButton(column: slick.model.Column, columnNm: String = screenType.toString, itemNo: Int = 9000, component: Option[String] = Some("button")) = {
        //val screenId = None
        //val itemNo = 9999
        //val columnNm = screenType
        val itemNmJa = Some(columnNm)
        val insideItemNmJa = Some("-")
        val itemNmEn = itemNmJa
        val updatedItemNmEn = Some("-")
        val insideItemNmEn = Some("-")
        val updatedInsideItemNmEn = Some("-")
        //val component = Some("button")
        val activeKb = None
        val inputKb = None
        val domainCd = None
        val updatedDomainCd = domainCd
        val displayDigit = Some("-")
        val updatedDisplayDigit = None
        val requiredKb = None
        val defaultDisplay = Some("-")
        val outputColumn = Some("-")
        val outputSpec = Some("-")
        val updatedOutputSpec = None
        val comments = Some("-")
        val codegrpId = None
        val searchConditionKb = Some("--")
        val updatedSearchConditionKb = None
        val actionId = Some(itemNo.toString)
        val searchresultFlag = (screenType, columnNm) match {
          case (Search, "Search") => Some("0")
          case (Search, _) => Some("1")
          case _ => Some("0")
        }
        val searchresultUpdateFlag = None

        val screenItem = ScreenItemRow(screenId, itemNo, itemNmJa, insideItemNmJa, itemNmEn, updatedItemNmEn, insideItemNmEn, updatedInsideItemNmEn, component, activeKb, inputKb, domainCd, updatedDomainCd, displayDigit, updatedDisplayDigit, requiredKb, defaultDisplay, outputColumn, outputSpec, updatedOutputSpec, comments, codegrpId, searchConditionKb, updatedSearchConditionKb, actionId, searchresultFlag, searchresultUpdateFlag)
        ScreenItems.create(screenItem)
      }

      table.columns.zipWithIndex.foreach { case (column, i) => importScreenItem(column, i + 1) }
      importScreenItemButton(table.columns.head)
      if (screenType == Search) {
        importScreenItemButton(table.columns.head, "Update", 9001, Some("link"))
        importScreenItemButton(table.columns.head, "Delete", 9002, Some("link"))
      }

      // -----ScreenAction-----
      def importScreenAction(actionId: String = "9000", actionNmJa: Option[String] = Some(screenType.toString)) = {
        val actionNmEn = actionNmJa
        val forwardScreenId = if (screenType.toString == actionNmJa.get || actionNmJa.get == "Delete") Some("myself") else Some(s"${tableNm}${actionNmJa.get}")

        val errorScreenId = forwardScreenId
        val actionSummary = actionNmJa
        val processingMode = None

        val screenAction = ScreenActionRow(screenId, actionId, actionNmJa, actionNmEn, forwardScreenId, errorScreenId, actionSummary, processingMode)
        ScreenActions.create(screenAction)
      }
      importScreenAction()
      if (screenType == Search) {
        importScreenAction("9001", Some("Update"))
        importScreenAction("9002", Some("Delete"))
      }

      // -----ScreenEntity-----
      val lineNo = 1
      val entityNmJa = Some(decapitalize(camelize(table.name.table)))
      val entityNmEn = entityNmJa
      val screenEntity = ScreenEntityRow(screenId, lineNo, entityNmJa, entityNmEn)
      ScreenEntitys.create(screenEntity)

    }

    model.tables.foreach(outputByTable)
    logger.info(s"${model.tables.length} tables imported as '${screenType}' type")
  }
}
