package skalholt.codegen.database.common
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.JdbcDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import BaseDatabase.profile.simple._
  import scala.slick.model.ForeignKeyAction
  import scala.slick.collection.heterogenous._
  import scala.slick.collection.heterogenous.syntax._
  import skalholt.codegen.database.common.BaseDatabase.BaseTable
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = Annotation.ddl ++ AnnotationDefinition.ddl ++ Domain.ddl ++ Screen.ddl ++ ScreenAction.ddl ++ ScreenEntity.ddl ++ ScreenItem.ddl

  /** Entity class storing rows of table Annotation
   *  @param annotationCd Database column ANNOTATION_CD PrimaryKey
   *  @param annotationNmJa Database column ANNOTATION_NM_JA
   *  @param annotationNmEn Database column ANNOTATION_NM_EN
   *  @param argumentType Database column ARGUMENT_TYPE
   *  @param argument1 Database column ARGUMENT1
   *  @param argument2 Database column ARGUMENT2
   *  @param argument3 Database column ARGUMENT3
   *  @param argument4 Database column ARGUMENT4
   *  @param argument5 Database column ARGUMENT5
   *  @param classpathStr Database column CLASSPATH_STR  */
  case class AnnotationRow(annotationCd: String, annotationNmJa: Option[String], annotationNmEn: Option[String], argumentType: Option[String], argument1: Option[String], argument2: Option[String], argument3: Option[String], argument4: Option[String], argument5: Option[String], classpathStr: Option[String])
  /** GetResult implicit for fetching AnnotationRow objects using plain SQL queries */
  implicit def GetResultAnnotationRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[AnnotationRow] = GR{
    prs => import prs._
    AnnotationRow.tupled((<<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table ANNOTATION. Objects of this class serve as prototypes for rows in queries. */
  class Annotation(_tableTag: Tag) extends BaseTable[AnnotationRow](_tableTag, Some("GENERATOR"), "ANNOTATION") {
    def * = (annotationCd, annotationNmJa, annotationNmEn, argumentType, argument1, argument2, argument3, argument4, argument5, classpathStr) <> (AnnotationRow.tupled, AnnotationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (annotationCd.?, annotationNmJa, annotationNmEn, argumentType, argument1, argument2, argument3, argument4, argument5, classpathStr).shaped.<>({r=>import r._; _1.map(_=> AnnotationRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ANNOTATION_CD PrimaryKey */
    val annotationCd: Column[String] = column[String]("ANNOTATION_CD", O.PrimaryKey)
    /** Database column ANNOTATION_NM_JA  */
    val annotationNmJa: Column[Option[String]] = column[Option[String]]("ANNOTATION_NM_JA")
    /** Database column ANNOTATION_NM_EN  */
    val annotationNmEn: Column[Option[String]] = column[Option[String]]("ANNOTATION_NM_EN")
    /** Database column ARGUMENT_TYPE  */
    val argumentType: Column[Option[String]] = column[Option[String]]("ARGUMENT_TYPE")
    /** Database column ARGUMENT1  */
    val argument1: Column[Option[String]] = column[Option[String]]("ARGUMENT1")
    /** Database column ARGUMENT2  */
    val argument2: Column[Option[String]] = column[Option[String]]("ARGUMENT2")
    /** Database column ARGUMENT3  */
    val argument3: Column[Option[String]] = column[Option[String]]("ARGUMENT3")
    /** Database column ARGUMENT4  */
    val argument4: Column[Option[String]] = column[Option[String]]("ARGUMENT4")
    /** Database column ARGUMENT5  */
    val argument5: Column[Option[String]] = column[Option[String]]("ARGUMENT5")
    /** Database column CLASSPATH_STR  */
    val classpathStr: Column[Option[String]] = column[Option[String]]("CLASSPATH_STR")
  }
  /** Collection-like TableQuery object for table Annotation */
  lazy val Annotation = new TableQuery(tag => new Annotation(tag))

  /** Entity class storing rows of table AnnotationDefinition
   *  @param domainCd Database column DOMAIN_CD
   *  @param annotationCd Database column ANNOTATION_CD
   *  @param definitionValue1 Database column DEFINITION_VALUE1
   *  @param definitionValue2 Database column DEFINITION_VALUE2
   *  @param definitionValue3 Database column DEFINITION_VALUE3
   *  @param definitionValue4 Database column DEFINITION_VALUE4
   *  @param definitionValue5 Database column DEFINITION_VALUE5
   *  @param msgKey Database column MSG_KEY
   *  @param argumentKey Database column ARGUMENT_KEY  */
  case class AnnotationDefinitionRow(domainCd: String, annotationCd: String, definitionValue1: Option[String], definitionValue2: Option[String], definitionValue3: Option[String], definitionValue4: Option[String], definitionValue5: Option[String], msgKey: Option[String], argumentKey: Option[String])
  /** GetResult implicit for fetching AnnotationDefinitionRow objects using plain SQL queries */
  implicit def GetResultAnnotationDefinitionRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[AnnotationDefinitionRow] = GR{
    prs => import prs._
    AnnotationDefinitionRow.tupled((<<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table ANNOTATION_DEFINITION. Objects of this class serve as prototypes for rows in queries. */
  class AnnotationDefinition(_tableTag: Tag) extends BaseTable[AnnotationDefinitionRow](_tableTag, Some("GENERATOR"), "ANNOTATION_DEFINITION") {
    def * = (domainCd, annotationCd, definitionValue1, definitionValue2, definitionValue3, definitionValue4, definitionValue5, msgKey, argumentKey) <> (AnnotationDefinitionRow.tupled, AnnotationDefinitionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (domainCd.?, annotationCd.?, definitionValue1, definitionValue2, definitionValue3, definitionValue4, definitionValue5, msgKey, argumentKey).shaped.<>({r=>import r._; _1.map(_=> AnnotationDefinitionRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DOMAIN_CD  */
    val domainCd: Column[String] = column[String]("DOMAIN_CD")
    /** Database column ANNOTATION_CD  */
    val annotationCd: Column[String] = column[String]("ANNOTATION_CD")
    /** Database column DEFINITION_VALUE1  */
    val definitionValue1: Column[Option[String]] = column[Option[String]]("DEFINITION_VALUE1")
    /** Database column DEFINITION_VALUE2  */
    val definitionValue2: Column[Option[String]] = column[Option[String]]("DEFINITION_VALUE2")
    /** Database column DEFINITION_VALUE3  */
    val definitionValue3: Column[Option[String]] = column[Option[String]]("DEFINITION_VALUE3")
    /** Database column DEFINITION_VALUE4  */
    val definitionValue4: Column[Option[String]] = column[Option[String]]("DEFINITION_VALUE4")
    /** Database column DEFINITION_VALUE5  */
    val definitionValue5: Column[Option[String]] = column[Option[String]]("DEFINITION_VALUE5")
    /** Database column MSG_KEY  */
    val msgKey: Column[Option[String]] = column[Option[String]]("MSG_KEY")
    /** Database column ARGUMENT_KEY  */
    val argumentKey: Column[Option[String]] = column[Option[String]]("ARGUMENT_KEY")

    /** Primary key of AnnotationDefinition (database name PK_ANNOTATION_DEFINITION) */
    val pk = primaryKey("PK_ANNOTATION_DEFINITION", (domainCd, annotationCd))
  }
  /** Collection-like TableQuery object for table AnnotationDefinition */
  lazy val AnnotationDefinition = new TableQuery(tag => new AnnotationDefinition(tag))

  /** Entity class storing rows of table Domain
   *  @param domainCd Database column DOMAIN_CD PrimaryKey
   *  @param domainNm Database column DOMAIN_NM
   *  @param dataType Database column DATA_TYPE
   *  @param halfFullKb Database column HALF_FULL_KB
   *  @param dataLength Database column DATA_LENGTH
   *  @param displayDigit Database column DISPLAY_DIGIT
   *  @param outputSpec Database column OUTPUT_SPEC  */
  case class DomainRow(domainCd: String, domainNm: Option[String], dataType: Option[String], halfFullKb: Option[String], dataLength: Option[String], displayDigit: Option[String], outputSpec: Option[String])
  /** GetResult implicit for fetching DomainRow objects using plain SQL queries */
  implicit def GetResultDomainRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[DomainRow] = GR{
    prs => import prs._
    DomainRow.tupled((<<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table DOMAIN. Objects of this class serve as prototypes for rows in queries. */
  class Domain(_tableTag: Tag) extends BaseTable[DomainRow](_tableTag, Some("GENERATOR"), "DOMAIN") {
    def * = (domainCd, domainNm, dataType, halfFullKb, dataLength, displayDigit, outputSpec) <> (DomainRow.tupled, DomainRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (domainCd.?, domainNm, dataType, halfFullKb, dataLength, displayDigit, outputSpec).shaped.<>({r=>import r._; _1.map(_=> DomainRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DOMAIN_CD PrimaryKey */
    val domainCd: Column[String] = column[String]("DOMAIN_CD", O.PrimaryKey)
    /** Database column DOMAIN_NM  */
    val domainNm: Column[Option[String]] = column[Option[String]]("DOMAIN_NM")
    /** Database column DATA_TYPE  */
    val dataType: Column[Option[String]] = column[Option[String]]("DATA_TYPE")
    /** Database column HALF_FULL_KB  */
    val halfFullKb: Column[Option[String]] = column[Option[String]]("HALF_FULL_KB")
    /** Database column DATA_LENGTH  */
    val dataLength: Column[Option[String]] = column[Option[String]]("DATA_LENGTH")
    /** Database column DISPLAY_DIGIT  */
    val displayDigit: Column[Option[String]] = column[Option[String]]("DISPLAY_DIGIT")
    /** Database column OUTPUT_SPEC  */
    val outputSpec: Column[Option[String]] = column[Option[String]]("OUTPUT_SPEC")
  }
  /** Collection-like TableQuery object for table Domain */
  lazy val Domain = new TableQuery(tag => new Domain(tag))

  /** Entity class storing rows of table Screen
   *  @param screenId Database column SCREEN_ID PrimaryKey
   *  @param screenNm Database column SCREEN_NM
   *  @param jspNm Database column JSP_NM
   *  @param useCaseNm Database column USE_CASE_NM
   *  @param actionClassId Database column ACTION_CLASS_ID
   *  @param screenRbn Database column SCREEN_RBN
   *  @param subsystemNmJa Database column SUBSYSTEM_NM_JA
   *  @param subsystemNmEn Database column SUBSYSTEM_NM_EN
   *  @param packageNm Database column PACKAGE_NM
   *  @param screenType Database column SCREEN_TYPE
   *  @param authorityClassification Database column AUTHORITY_CLASSIFICATION
   *  @param authorityCrud Database column AUTHORITY_CRUD  */
  case class ScreenRow(screenId: String, screenNm: Option[String], jspNm: Option[String], useCaseNm: Option[String], actionClassId: Option[String], screenRbn: Option[String], subsystemNmJa: Option[String], subsystemNmEn: Option[String], packageNm: Option[String], screenType: Option[String], authorityClassification: Option[String], authorityCrud: Option[String])
  /** GetResult implicit for fetching ScreenRow objects using plain SQL queries */
  implicit def GetResultScreenRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[ScreenRow] = GR{
    prs => import prs._
    ScreenRow.tupled((<<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table SCREEN. Objects of this class serve as prototypes for rows in queries. */
  class Screen(_tableTag: Tag) extends BaseTable[ScreenRow](_tableTag, Some("GENERATOR"), "SCREEN") {
    def * = (screenId, screenNm, jspNm, useCaseNm, actionClassId, screenRbn, subsystemNmJa, subsystemNmEn, packageNm, screenType, authorityClassification, authorityCrud) <> (ScreenRow.tupled, ScreenRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (screenId.?, screenNm, jspNm, useCaseNm, actionClassId, screenRbn, subsystemNmJa, subsystemNmEn, packageNm, screenType, authorityClassification, authorityCrud).shaped.<>({r=>import r._; _1.map(_=> ScreenRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column SCREEN_ID PrimaryKey */
    val screenId: Column[String] = column[String]("SCREEN_ID", O.PrimaryKey)
    /** Database column SCREEN_NM  */
    val screenNm: Column[Option[String]] = column[Option[String]]("SCREEN_NM")
    /** Database column JSP_NM  */
    val jspNm: Column[Option[String]] = column[Option[String]]("JSP_NM")
    /** Database column USE_CASE_NM  */
    val useCaseNm: Column[Option[String]] = column[Option[String]]("USE_CASE_NM")
    /** Database column ACTION_CLASS_ID  */
    val actionClassId: Column[Option[String]] = column[Option[String]]("ACTION_CLASS_ID")
    /** Database column SCREEN_RBN  */
    val screenRbn: Column[Option[String]] = column[Option[String]]("SCREEN_RBN")
    /** Database column SUBSYSTEM_NM_JA  */
    val subsystemNmJa: Column[Option[String]] = column[Option[String]]("SUBSYSTEM_NM_JA")
    /** Database column SUBSYSTEM_NM_EN  */
    val subsystemNmEn: Column[Option[String]] = column[Option[String]]("SUBSYSTEM_NM_EN")
    /** Database column PACKAGE_NM  */
    val packageNm: Column[Option[String]] = column[Option[String]]("PACKAGE_NM")
    /** Database column SCREEN_TYPE  */
    val screenType: Column[Option[String]] = column[Option[String]]("SCREEN_TYPE")
    /** Database column AUTHORITY_CLASSIFICATION  */
    val authorityClassification: Column[Option[String]] = column[Option[String]]("AUTHORITY_CLASSIFICATION")
    /** Database column AUTHORITY_CRUD  */
    val authorityCrud: Column[Option[String]] = column[Option[String]]("AUTHORITY_CRUD")
  }
  /** Collection-like TableQuery object for table Screen */
  lazy val Screen = new TableQuery(tag => new Screen(tag))

  /** Entity class storing rows of table ScreenAction
   *  @param screenId Database column SCREEN_ID
   *  @param actionId Database column ACTION_ID
   *  @param actionNmJa Database column ACTION_NM_JA
   *  @param actionNmEn Database column ACTION_NM_EN
   *  @param forwardScreenId Database column FORWARD_SCREEN_ID
   *  @param errorScreenId Database column ERROR_SCREEN_ID
   *  @param actionSummary Database column ACTION_SUMMARY
   *  @param processingMode Database column PROCESSING_MODE  */
  case class ScreenActionRow(screenId: String, actionId: String, actionNmJa: Option[String], actionNmEn: Option[String], forwardScreenId: Option[String], errorScreenId: Option[String], actionSummary: Option[String], processingMode: Option[String])
  /** GetResult implicit for fetching ScreenActionRow objects using plain SQL queries */
  implicit def GetResultScreenActionRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[ScreenActionRow] = GR{
    prs => import prs._
    ScreenActionRow.tupled((<<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table SCREEN_ACTION. Objects of this class serve as prototypes for rows in queries. */
  class ScreenAction(_tableTag: Tag) extends BaseTable[ScreenActionRow](_tableTag, Some("GENERATOR"), "SCREEN_ACTION") {
    def * = (screenId, actionId, actionNmJa, actionNmEn, forwardScreenId, errorScreenId, actionSummary, processingMode) <> (ScreenActionRow.tupled, ScreenActionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (screenId.?, actionId.?, actionNmJa, actionNmEn, forwardScreenId, errorScreenId, actionSummary, processingMode).shaped.<>({r=>import r._; _1.map(_=> ScreenActionRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column SCREEN_ID  */
    val screenId: Column[String] = column[String]("SCREEN_ID")
    /** Database column ACTION_ID  */
    val actionId: Column[String] = column[String]("ACTION_ID")
    /** Database column ACTION_NM_JA  */
    val actionNmJa: Column[Option[String]] = column[Option[String]]("ACTION_NM_JA")
    /** Database column ACTION_NM_EN  */
    val actionNmEn: Column[Option[String]] = column[Option[String]]("ACTION_NM_EN")
    /** Database column FORWARD_SCREEN_ID  */
    val forwardScreenId: Column[Option[String]] = column[Option[String]]("FORWARD_SCREEN_ID")
    /** Database column ERROR_SCREEN_ID  */
    val errorScreenId: Column[Option[String]] = column[Option[String]]("ERROR_SCREEN_ID")
    /** Database column ACTION_SUMMARY  */
    val actionSummary: Column[Option[String]] = column[Option[String]]("ACTION_SUMMARY")
    /** Database column PROCESSING_MODE  */
    val processingMode: Column[Option[String]] = column[Option[String]]("PROCESSING_MODE")

    /** Primary key of ScreenAction (database name PK_SCREEN_ACTION) */
    val pk = primaryKey("PK_SCREEN_ACTION", (screenId, actionId))
  }
  /** Collection-like TableQuery object for table ScreenAction */
  lazy val ScreenAction = new TableQuery(tag => new ScreenAction(tag))

  /** Entity class storing rows of table ScreenEntity
   *  @param screenId Database column SCREEN_ID
   *  @param lineNo Database column LINE_NO
   *  @param entityNmJa Database column ENTITY_NM_JA
   *  @param entityNmEn Database column ENTITY_NM_EN  */
  case class ScreenEntityRow(screenId: String, lineNo: scala.math.BigDecimal, entityNmJa: Option[String], entityNmEn: Option[String])
  /** GetResult implicit for fetching ScreenEntityRow objects using plain SQL queries */
  implicit def GetResultScreenEntityRow(implicit e0: GR[String], e1: GR[scala.math.BigDecimal], e2: GR[Option[String]]): GR[ScreenEntityRow] = GR{
    prs => import prs._
    ScreenEntityRow.tupled((<<[String], <<[scala.math.BigDecimal], <<?[String], <<?[String]))
  }
  /** Table description of table SCREEN_ENTITY. Objects of this class serve as prototypes for rows in queries. */
  class ScreenEntity(_tableTag: Tag) extends BaseTable[ScreenEntityRow](_tableTag, Some("GENERATOR"), "SCREEN_ENTITY") {
    def * = (screenId, lineNo, entityNmJa, entityNmEn) <> (ScreenEntityRow.tupled, ScreenEntityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (screenId.?, lineNo.?, entityNmJa, entityNmEn).shaped.<>({r=>import r._; _1.map(_=> ScreenEntityRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column SCREEN_ID  */
    val screenId: Column[String] = column[String]("SCREEN_ID")
    /** Database column LINE_NO  */
    val lineNo: Column[scala.math.BigDecimal] = column[scala.math.BigDecimal]("LINE_NO")
    /** Database column ENTITY_NM_JA  */
    val entityNmJa: Column[Option[String]] = column[Option[String]]("ENTITY_NM_JA")
    /** Database column ENTITY_NM_EN  */
    val entityNmEn: Column[Option[String]] = column[Option[String]]("ENTITY_NM_EN")

    /** Primary key of ScreenEntity (database name PK_SCREEN_ENTITY) */
    val pk = primaryKey("PK_SCREEN_ENTITY", (screenId, lineNo))
  }
  /** Collection-like TableQuery object for table ScreenEntity */
  lazy val ScreenEntity = new TableQuery(tag => new ScreenEntity(tag))

  /** Row type of table ScreenItem */
  type ScreenItemRow = HCons[String,HCons[scala.math.BigDecimal,HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HNil]]]]]]]]]]]]]]]]]]]]]]]]]]]
  /** Constructor for ScreenItemRow providing default values if available in the database schema. */
  def ScreenItemRow(screenId: String, itemNo: scala.math.BigDecimal, itemNmJa: Option[String], insideItemNmJa: Option[String], itemNmEn: Option[String], updatedItemNmEn: Option[String], insideItemNmEn: Option[String], updatedInsideItemNmEn: Option[String], component: Option[String], activeKb: Option[String], inputKb: Option[String], domainCd: Option[String], updatedDomainCd: Option[String], displayDigit: Option[String], updatedDisplayDigit: Option[String], requiredKb: Option[String], defaultDisplay: Option[String], outputColumn: Option[String], outputSpec: Option[String], updatedOutputSpec: Option[String], comments: Option[String], codegrpId: Option[String], searchConditionKb: Option[String], updatedSearchConditionKb: Option[String], actionId: Option[String], searchresultFlag: Option[String], searchresultUpdateFlag: Option[String]): ScreenItemRow = {
    screenId :: itemNo :: itemNmJa :: insideItemNmJa :: itemNmEn :: updatedItemNmEn :: insideItemNmEn :: updatedInsideItemNmEn :: component :: activeKb :: inputKb :: domainCd :: updatedDomainCd :: displayDigit :: updatedDisplayDigit :: requiredKb :: defaultDisplay :: outputColumn :: outputSpec :: updatedOutputSpec :: comments :: codegrpId :: searchConditionKb :: updatedSearchConditionKb :: actionId :: searchresultFlag :: searchresultUpdateFlag :: HNil
  }
  /** GetResult implicit for fetching ScreenItemRow objects using plain SQL queries */
  implicit def GetResultScreenItemRow(implicit e0: GR[String], e1: GR[scala.math.BigDecimal], e2: GR[Option[String]]): GR[ScreenItemRow] = GR{
    prs => import prs._
    <<[String] :: <<[scala.math.BigDecimal] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: HNil
  }
  /** Table description of table SCREEN_ITEM. Objects of this class serve as prototypes for rows in queries. */
  class ScreenItem(_tableTag: Tag) extends BaseTable[ScreenItemRow](_tableTag, Some("GENERATOR"), "SCREEN_ITEM") {
    def * = screenId :: itemNo :: itemNmJa :: insideItemNmJa :: itemNmEn :: updatedItemNmEn :: insideItemNmEn :: updatedInsideItemNmEn :: component :: activeKb :: inputKb :: domainCd :: updatedDomainCd :: displayDigit :: updatedDisplayDigit :: requiredKb :: defaultDisplay :: outputColumn :: outputSpec :: updatedOutputSpec :: comments :: codegrpId :: searchConditionKb :: updatedSearchConditionKb :: actionId :: searchresultFlag :: searchresultUpdateFlag :: HNil

    /** Database column SCREEN_ID  */
    val screenId: Column[String] = column[String]("SCREEN_ID")
    /** Database column ITEM_NO  */
    val itemNo: Column[scala.math.BigDecimal] = column[scala.math.BigDecimal]("ITEM_NO")
    /** Database column ITEM_NM_JA  */
    val itemNmJa: Column[Option[String]] = column[Option[String]]("ITEM_NM_JA")
    /** Database column INSIDE_ITEM_NM_JA  */
    val insideItemNmJa: Column[Option[String]] = column[Option[String]]("INSIDE_ITEM_NM_JA")
    /** Database column ITEM_NM_EN  */
    val itemNmEn: Column[Option[String]] = column[Option[String]]("ITEM_NM_EN")
    /** Database column UPDATED_ITEM_NM_EN  */
    val updatedItemNmEn: Column[Option[String]] = column[Option[String]]("UPDATED_ITEM_NM_EN")
    /** Database column INSIDE_ITEM_NM_EN  */
    val insideItemNmEn: Column[Option[String]] = column[Option[String]]("INSIDE_ITEM_NM_EN")
    /** Database column UPDATED_INSIDE_ITEM_NM_EN  */
    val updatedInsideItemNmEn: Column[Option[String]] = column[Option[String]]("UPDATED_INSIDE_ITEM_NM_EN")
    /** Database column COMPONENT  */
    val component: Column[Option[String]] = column[Option[String]]("COMPONENT")
    /** Database column ACTIVE_KB  */
    val activeKb: Column[Option[String]] = column[Option[String]]("ACTIVE_KB")
    /** Database column INPUT_KB  */
    val inputKb: Column[Option[String]] = column[Option[String]]("INPUT_KB")
    /** Database column DOMAIN_CD  */
    val domainCd: Column[Option[String]] = column[Option[String]]("DOMAIN_CD")
    /** Database column UPDATED_DOMAIN_CD  */
    val updatedDomainCd: Column[Option[String]] = column[Option[String]]("UPDATED_DOMAIN_CD")
    /** Database column DISPLAY_DIGIT  */
    val displayDigit: Column[Option[String]] = column[Option[String]]("DISPLAY_DIGIT")
    /** Database column UPDATED_DISPLAY_DIGIT  */
    val updatedDisplayDigit: Column[Option[String]] = column[Option[String]]("UPDATED_DISPLAY_DIGIT")
    /** Database column REQUIRED_KB  */
    val requiredKb: Column[Option[String]] = column[Option[String]]("REQUIRED_KB")
    /** Database column DEFAULT_DISPLAY  */
    val defaultDisplay: Column[Option[String]] = column[Option[String]]("DEFAULT_DISPLAY")
    /** Database column OUTPUT_COLUMN  */
    val outputColumn: Column[Option[String]] = column[Option[String]]("OUTPUT_COLUMN")
    /** Database column OUTPUT_SPEC  */
    val outputSpec: Column[Option[String]] = column[Option[String]]("OUTPUT_SPEC")
    /** Database column UPDATED_OUTPUT_SPEC  */
    val updatedOutputSpec: Column[Option[String]] = column[Option[String]]("UPDATED_OUTPUT_SPEC")
    /** Database column COMMENTS  */
    val comments: Column[Option[String]] = column[Option[String]]("COMMENTS")
    /** Database column CODEGRP_ID  */
    val codegrpId: Column[Option[String]] = column[Option[String]]("CODEGRP_ID")
    /** Database column SEARCH_CONDITION_KB  */
    val searchConditionKb: Column[Option[String]] = column[Option[String]]("SEARCH_CONDITION_KB")
    /** Database column UPDATED_SEARCH_CONDITION_KB  */
    val updatedSearchConditionKb: Column[Option[String]] = column[Option[String]]("UPDATED_SEARCH_CONDITION_KB")
    /** Database column ACTION_ID  */
    val actionId: Column[Option[String]] = column[Option[String]]("ACTION_ID")
    /** Database column SEARCHRESULT_FLAG  */
    val searchresultFlag: Column[Option[String]] = column[Option[String]]("SEARCHRESULT_FLAG")
    /** Database column SEARCHRESULT_UPDATE_FLAG  */
    val searchresultUpdateFlag: Column[Option[String]] = column[Option[String]]("SEARCHRESULT_UPDATE_FLAG")

    /** Primary key of ScreenItem (database name PK_SCREEN_ITEM) */
    val pk = primaryKey("PK_SCREEN_ITEM", screenId :: itemNo :: HNil)
  }
  /** Collection-like TableQuery object for table ScreenItem */
  lazy val ScreenItem = new TableQuery(tag => new ScreenItem(tag))
}