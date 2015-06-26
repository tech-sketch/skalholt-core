package skalholt.codegen.database.common
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  import slick.collection.heterogeneous._
  import slick.collection.heterogeneous.syntax._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Array(Annotation.schema, AnnotationDefinition.schema, Domain.schema, Screen.schema, ScreenAction.schema, ScreenEntity.schema, ScreenItem.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Annotation
    *  @param annotationCd Database column ANNOTATION_CD SqlType(VARCHAR), PrimaryKey, Length(100,true)
    *  @param annotationNmJa Database column ANNOTATION_NM_JA SqlType(VARCHAR), Length(100,true)
    *  @param annotationNmEn Database column ANNOTATION_NM_EN SqlType(VARCHAR), Length(100,true)
    *  @param argumentType Database column ARGUMENT_TYPE SqlType(VARCHAR), Length(100,true)
    *  @param argument1 Database column ARGUMENT1 SqlType(VARCHAR), Length(100,true)
    *  @param argument2 Database column ARGUMENT2 SqlType(VARCHAR), Length(100,true)
    *  @param argument3 Database column ARGUMENT3 SqlType(VARCHAR), Length(100,true)
    *  @param argument4 Database column ARGUMENT4 SqlType(VARCHAR), Length(100,true)
    *  @param argument5 Database column ARGUMENT5 SqlType(VARCHAR), Length(100,true)
    *  @param classpathStr Database column CLASSPATH_STR SqlType(VARCHAR), Length(300,true) */
  case class AnnotationRow(annotationCd: String, annotationNmJa: Option[String], annotationNmEn: Option[String], argumentType: Option[String], argument1: Option[String], argument2: Option[String], argument3: Option[String], argument4: Option[String], argument5: Option[String], classpathStr: Option[String])
  /** GetResult implicit for fetching AnnotationRow objects using plain SQL queries */
  implicit def GetResultAnnotationRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[AnnotationRow] = GR{
    prs => import prs._
      AnnotationRow.tupled((<<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table ANNOTATION. Objects of this class serve as prototypes for rows in queries. */
  class Annotation(_tableTag: Tag) extends Table[AnnotationRow](_tableTag, Some("GENERATOR"), "ANNOTATION") {
    def * = (annotationCd, annotationNmJa, annotationNmEn, argumentType, argument1, argument2, argument3, argument4, argument5, classpathStr) <> (AnnotationRow.tupled, AnnotationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(annotationCd), annotationNmJa, annotationNmEn, argumentType, argument1, argument2, argument3, argument4, argument5, classpathStr).shaped.<>({r=>import r._; _1.map(_=> AnnotationRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ANNOTATION_CD SqlType(VARCHAR), PrimaryKey, Length(100,true) */
    val annotationCd: Rep[String] = column[String]("ANNOTATION_CD", O.PrimaryKey, O.Length(100,varying=true))
    /** Database column ANNOTATION_NM_JA SqlType(VARCHAR), Length(100,true) */
    val annotationNmJa: Rep[Option[String]] = column[Option[String]]("ANNOTATION_NM_JA", O.Length(100,varying=true))
    /** Database column ANNOTATION_NM_EN SqlType(VARCHAR), Length(100,true) */
    val annotationNmEn: Rep[Option[String]] = column[Option[String]]("ANNOTATION_NM_EN", O.Length(100,varying=true))
    /** Database column ARGUMENT_TYPE SqlType(VARCHAR), Length(100,true) */
    val argumentType: Rep[Option[String]] = column[Option[String]]("ARGUMENT_TYPE", O.Length(100,varying=true))
    /** Database column ARGUMENT1 SqlType(VARCHAR), Length(100,true) */
    val argument1: Rep[Option[String]] = column[Option[String]]("ARGUMENT1", O.Length(100,varying=true))
    /** Database column ARGUMENT2 SqlType(VARCHAR), Length(100,true) */
    val argument2: Rep[Option[String]] = column[Option[String]]("ARGUMENT2", O.Length(100,varying=true))
    /** Database column ARGUMENT3 SqlType(VARCHAR), Length(100,true) */
    val argument3: Rep[Option[String]] = column[Option[String]]("ARGUMENT3", O.Length(100,varying=true))
    /** Database column ARGUMENT4 SqlType(VARCHAR), Length(100,true) */
    val argument4: Rep[Option[String]] = column[Option[String]]("ARGUMENT4", O.Length(100,varying=true))
    /** Database column ARGUMENT5 SqlType(VARCHAR), Length(100,true) */
    val argument5: Rep[Option[String]] = column[Option[String]]("ARGUMENT5", O.Length(100,varying=true))
    /** Database column CLASSPATH_STR SqlType(VARCHAR), Length(300,true) */
    val classpathStr: Rep[Option[String]] = column[Option[String]]("CLASSPATH_STR", O.Length(300,varying=true))
  }
  /** Collection-like TableQuery object for table Annotation */
  lazy val Annotation = new TableQuery(tag => new Annotation(tag))

  /** Entity class storing rows of table AnnotationDefinition
    *  @param domainCd Database column DOMAIN_CD SqlType(VARCHAR), Length(100,true)
    *  @param annotationCd Database column ANNOTATION_CD SqlType(VARCHAR), Length(100,true)
    *  @param definitionValue1 Database column DEFINITION_VALUE1 SqlType(VARCHAR), Length(100,true)
    *  @param definitionValue2 Database column DEFINITION_VALUE2 SqlType(VARCHAR), Length(100,true)
    *  @param definitionValue3 Database column DEFINITION_VALUE3 SqlType(VARCHAR), Length(100,true)
    *  @param definitionValue4 Database column DEFINITION_VALUE4 SqlType(VARCHAR), Length(100,true)
    *  @param definitionValue5 Database column DEFINITION_VALUE5 SqlType(VARCHAR), Length(100,true)
    *  @param msgKey Database column MSG_KEY SqlType(VARCHAR), Length(100,true)
    *  @param argumentKey Database column ARGUMENT_KEY SqlType(VARCHAR), Length(100,true) */
  case class AnnotationDefinitionRow(domainCd: String, annotationCd: String, definitionValue1: Option[String], definitionValue2: Option[String], definitionValue3: Option[String], definitionValue4: Option[String], definitionValue5: Option[String], msgKey: Option[String], argumentKey: Option[String])
  /** GetResult implicit for fetching AnnotationDefinitionRow objects using plain SQL queries */
  implicit def GetResultAnnotationDefinitionRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[AnnotationDefinitionRow] = GR{
    prs => import prs._
      AnnotationDefinitionRow.tupled((<<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table ANNOTATION_DEFINITION. Objects of this class serve as prototypes for rows in queries. */
  class AnnotationDefinition(_tableTag: Tag) extends Table[AnnotationDefinitionRow](_tableTag, Some("GENERATOR"), "ANNOTATION_DEFINITION") {
    def * = (domainCd, annotationCd, definitionValue1, definitionValue2, definitionValue3, definitionValue4, definitionValue5, msgKey, argumentKey) <> (AnnotationDefinitionRow.tupled, AnnotationDefinitionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(domainCd), Rep.Some(annotationCd), definitionValue1, definitionValue2, definitionValue3, definitionValue4, definitionValue5, msgKey, argumentKey).shaped.<>({r=>import r._; _1.map(_=> AnnotationDefinitionRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DOMAIN_CD SqlType(VARCHAR), Length(100,true) */
    val domainCd: Rep[String] = column[String]("DOMAIN_CD", O.Length(100,varying=true))
    /** Database column ANNOTATION_CD SqlType(VARCHAR), Length(100,true) */
    val annotationCd: Rep[String] = column[String]("ANNOTATION_CD", O.Length(100,varying=true))
    /** Database column DEFINITION_VALUE1 SqlType(VARCHAR), Length(100,true) */
    val definitionValue1: Rep[Option[String]] = column[Option[String]]("DEFINITION_VALUE1", O.Length(100,varying=true))
    /** Database column DEFINITION_VALUE2 SqlType(VARCHAR), Length(100,true) */
    val definitionValue2: Rep[Option[String]] = column[Option[String]]("DEFINITION_VALUE2", O.Length(100,varying=true))
    /** Database column DEFINITION_VALUE3 SqlType(VARCHAR), Length(100,true) */
    val definitionValue3: Rep[Option[String]] = column[Option[String]]("DEFINITION_VALUE3", O.Length(100,varying=true))
    /** Database column DEFINITION_VALUE4 SqlType(VARCHAR), Length(100,true) */
    val definitionValue4: Rep[Option[String]] = column[Option[String]]("DEFINITION_VALUE4", O.Length(100,varying=true))
    /** Database column DEFINITION_VALUE5 SqlType(VARCHAR), Length(100,true) */
    val definitionValue5: Rep[Option[String]] = column[Option[String]]("DEFINITION_VALUE5", O.Length(100,varying=true))
    /** Database column MSG_KEY SqlType(VARCHAR), Length(100,true) */
    val msgKey: Rep[Option[String]] = column[Option[String]]("MSG_KEY", O.Length(100,varying=true))
    /** Database column ARGUMENT_KEY SqlType(VARCHAR), Length(100,true) */
    val argumentKey: Rep[Option[String]] = column[Option[String]]("ARGUMENT_KEY", O.Length(100,varying=true))

    /** Primary key of AnnotationDefinition (database name PK_ANNOTATION_DEFINITION) */
    val pk = primaryKey("PK_ANNOTATION_DEFINITION", (domainCd, annotationCd))
  }
  /** Collection-like TableQuery object for table AnnotationDefinition */
  lazy val AnnotationDefinition = new TableQuery(tag => new AnnotationDefinition(tag))

  /** Entity class storing rows of table Domain
    *  @param domainCd Database column DOMAIN_CD SqlType(VARCHAR), PrimaryKey, Length(100,true)
    *  @param domainNm Database column DOMAIN_NM SqlType(VARCHAR), Length(100,true)
    *  @param dataType Database column DATA_TYPE SqlType(VARCHAR), Length(100,true)
    *  @param halfFullKb Database column HALF_FULL_KB SqlType(VARCHAR), Length(100,true)
    *  @param dataLength Database column DATA_LENGTH SqlType(VARCHAR), Length(100,true)
    *  @param displayDigit Database column DISPLAY_DIGIT SqlType(VARCHAR), Length(100,true)
    *  @param outputSpec Database column OUTPUT_SPEC SqlType(VARCHAR), Length(500,true) */
  case class DomainRow(domainCd: String, domainNm: Option[String], dataType: Option[String], halfFullKb: Option[String], dataLength: Option[String], displayDigit: Option[String], outputSpec: Option[String])
  /** GetResult implicit for fetching DomainRow objects using plain SQL queries */
  implicit def GetResultDomainRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[DomainRow] = GR{
    prs => import prs._
      DomainRow.tupled((<<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table DOMAIN. Objects of this class serve as prototypes for rows in queries. */
  class Domain(_tableTag: Tag) extends Table[DomainRow](_tableTag, Some("GENERATOR"), "DOMAIN") {
    def * = (domainCd, domainNm, dataType, halfFullKb, dataLength, displayDigit, outputSpec) <> (DomainRow.tupled, DomainRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(domainCd), domainNm, dataType, halfFullKb, dataLength, displayDigit, outputSpec).shaped.<>({r=>import r._; _1.map(_=> DomainRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column DOMAIN_CD SqlType(VARCHAR), PrimaryKey, Length(100,true) */
    val domainCd: Rep[String] = column[String]("DOMAIN_CD", O.PrimaryKey, O.Length(100,varying=true))
    /** Database column DOMAIN_NM SqlType(VARCHAR), Length(100,true) */
    val domainNm: Rep[Option[String]] = column[Option[String]]("DOMAIN_NM", O.Length(100,varying=true))
    /** Database column DATA_TYPE SqlType(VARCHAR), Length(100,true) */
    val dataType: Rep[Option[String]] = column[Option[String]]("DATA_TYPE", O.Length(100,varying=true))
    /** Database column HALF_FULL_KB SqlType(VARCHAR), Length(100,true) */
    val halfFullKb: Rep[Option[String]] = column[Option[String]]("HALF_FULL_KB", O.Length(100,varying=true))
    /** Database column DATA_LENGTH SqlType(VARCHAR), Length(100,true) */
    val dataLength: Rep[Option[String]] = column[Option[String]]("DATA_LENGTH", O.Length(100,varying=true))
    /** Database column DISPLAY_DIGIT SqlType(VARCHAR), Length(100,true) */
    val displayDigit: Rep[Option[String]] = column[Option[String]]("DISPLAY_DIGIT", O.Length(100,varying=true))
    /** Database column OUTPUT_SPEC SqlType(VARCHAR), Length(500,true) */
    val outputSpec: Rep[Option[String]] = column[Option[String]]("OUTPUT_SPEC", O.Length(500,varying=true))
  }
  /** Collection-like TableQuery object for table Domain */
  lazy val Domain = new TableQuery(tag => new Domain(tag))

  /** Entity class storing rows of table Screen
    *  @param screenId Database column SCREEN_ID SqlType(VARCHAR), PrimaryKey, Length(100,true)
    *  @param screenNm Database column SCREEN_NM SqlType(VARCHAR), Length(100,true)
    *  @param jspNm Database column JSP_NM SqlType(VARCHAR), Length(100,true)
    *  @param useCaseNm Database column USE_CASE_NM SqlType(VARCHAR), Length(100,true)
    *  @param actionClassId Database column ACTION_CLASS_ID SqlType(VARCHAR), Length(100,true)
    *  @param screenRbn Database column SCREEN_RBN SqlType(VARCHAR), Length(10,true)
    *  @param subsystemNmJa Database column SUBSYSTEM_NM_JA SqlType(VARCHAR), Length(100,true)
    *  @param subsystemNmEn Database column SUBSYSTEM_NM_EN SqlType(VARCHAR), Length(100,true)
    *  @param packageNm Database column PACKAGE_NM SqlType(VARCHAR), Length(100,true)
    *  @param screenType Database column SCREEN_TYPE SqlType(VARCHAR), Length(100,true)
    *  @param authorityClassification Database column AUTHORITY_CLASSIFICATION SqlType(VARCHAR), Length(100,true)
    *  @param authorityCrud Database column AUTHORITY_CRUD SqlType(VARCHAR), Length(100,true) */
  case class ScreenRow(screenId: String, screenNm: Option[String], jspNm: Option[String], useCaseNm: Option[String], actionClassId: Option[String], screenRbn: Option[String], subsystemNmJa: Option[String], subsystemNmEn: Option[String], packageNm: Option[String], screenType: Option[String], authorityClassification: Option[String], authorityCrud: Option[String])
  /** GetResult implicit for fetching ScreenRow objects using plain SQL queries */
  implicit def GetResultScreenRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[ScreenRow] = GR{
    prs => import prs._
      ScreenRow.tupled((<<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table SCREEN. Objects of this class serve as prototypes for rows in queries. */
  class Screen(_tableTag: Tag) extends Table[ScreenRow](_tableTag, Some("GENERATOR"), "SCREEN") {
    def * = (screenId, screenNm, jspNm, useCaseNm, actionClassId, screenRbn, subsystemNmJa, subsystemNmEn, packageNm, screenType, authorityClassification, authorityCrud) <> (ScreenRow.tupled, ScreenRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(screenId), screenNm, jspNm, useCaseNm, actionClassId, screenRbn, subsystemNmJa, subsystemNmEn, packageNm, screenType, authorityClassification, authorityCrud).shaped.<>({r=>import r._; _1.map(_=> ScreenRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column SCREEN_ID SqlType(VARCHAR), PrimaryKey, Length(100,true) */
    val screenId: Rep[String] = column[String]("SCREEN_ID", O.PrimaryKey, O.Length(100,varying=true))
    /** Database column SCREEN_NM SqlType(VARCHAR), Length(100,true) */
    val screenNm: Rep[Option[String]] = column[Option[String]]("SCREEN_NM", O.Length(100,varying=true))
    /** Database column JSP_NM SqlType(VARCHAR), Length(100,true) */
    val jspNm: Rep[Option[String]] = column[Option[String]]("JSP_NM", O.Length(100,varying=true))
    /** Database column USE_CASE_NM SqlType(VARCHAR), Length(100,true) */
    val useCaseNm: Rep[Option[String]] = column[Option[String]]("USE_CASE_NM", O.Length(100,varying=true))
    /** Database column ACTION_CLASS_ID SqlType(VARCHAR), Length(100,true) */
    val actionClassId: Rep[Option[String]] = column[Option[String]]("ACTION_CLASS_ID", O.Length(100,varying=true))
    /** Database column SCREEN_RBN SqlType(VARCHAR), Length(10,true) */
    val screenRbn: Rep[Option[String]] = column[Option[String]]("SCREEN_RBN", O.Length(10,varying=true))
    /** Database column SUBSYSTEM_NM_JA SqlType(VARCHAR), Length(100,true) */
    val subsystemNmJa: Rep[Option[String]] = column[Option[String]]("SUBSYSTEM_NM_JA", O.Length(100,varying=true))
    /** Database column SUBSYSTEM_NM_EN SqlType(VARCHAR), Length(100,true) */
    val subsystemNmEn: Rep[Option[String]] = column[Option[String]]("SUBSYSTEM_NM_EN", O.Length(100,varying=true))
    /** Database column PACKAGE_NM SqlType(VARCHAR), Length(100,true) */
    val packageNm: Rep[Option[String]] = column[Option[String]]("PACKAGE_NM", O.Length(100,varying=true))
    /** Database column SCREEN_TYPE SqlType(VARCHAR), Length(100,true) */
    val screenType: Rep[Option[String]] = column[Option[String]]("SCREEN_TYPE", O.Length(100,varying=true))
    /** Database column AUTHORITY_CLASSIFICATION SqlType(VARCHAR), Length(100,true) */
    val authorityClassification: Rep[Option[String]] = column[Option[String]]("AUTHORITY_CLASSIFICATION", O.Length(100,varying=true))
    /** Database column AUTHORITY_CRUD SqlType(VARCHAR), Length(100,true) */
    val authorityCrud: Rep[Option[String]] = column[Option[String]]("AUTHORITY_CRUD", O.Length(100,varying=true))
  }
  /** Collection-like TableQuery object for table Screen */
  lazy val Screen = new TableQuery(tag => new Screen(tag))

  /** Entity class storing rows of table ScreenAction
    *  @param screenId Database column SCREEN_ID SqlType(VARCHAR), Length(100,true)
    *  @param actionId Database column ACTION_ID SqlType(VARCHAR), Length(100,true)
    *  @param actionNmJa Database column ACTION_NM_JA SqlType(VARCHAR), Length(100,true)
    *  @param actionNmEn Database column ACTION_NM_EN SqlType(VARCHAR), Length(100,true)
    *  @param forwardScreenId Database column FORWARD_SCREEN_ID SqlType(VARCHAR), Length(100,true)
    *  @param errorScreenId Database column ERROR_SCREEN_ID SqlType(VARCHAR), Length(100,true)
    *  @param actionSummary Database column ACTION_SUMMARY SqlType(VARCHAR), Length(4000,true)
    *  @param processingMode Database column PROCESSING_MODE SqlType(VARCHAR), Length(100,true) */
  case class ScreenActionRow(screenId: String, actionId: String, actionNmJa: Option[String], actionNmEn: Option[String], forwardScreenId: Option[String], errorScreenId: Option[String], actionSummary: Option[String], processingMode: Option[String])
  /** GetResult implicit for fetching ScreenActionRow objects using plain SQL queries */
  implicit def GetResultScreenActionRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[ScreenActionRow] = GR{
    prs => import prs._
      ScreenActionRow.tupled((<<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table SCREEN_ACTION. Objects of this class serve as prototypes for rows in queries. */
  class ScreenAction(_tableTag: Tag) extends Table[ScreenActionRow](_tableTag, Some("GENERATOR"), "SCREEN_ACTION") {
    def * = (screenId, actionId, actionNmJa, actionNmEn, forwardScreenId, errorScreenId, actionSummary, processingMode) <> (ScreenActionRow.tupled, ScreenActionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(screenId), Rep.Some(actionId), actionNmJa, actionNmEn, forwardScreenId, errorScreenId, actionSummary, processingMode).shaped.<>({r=>import r._; _1.map(_=> ScreenActionRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column SCREEN_ID SqlType(VARCHAR), Length(100,true) */
    val screenId: Rep[String] = column[String]("SCREEN_ID", O.Length(100,varying=true))
    /** Database column ACTION_ID SqlType(VARCHAR), Length(100,true) */
    val actionId: Rep[String] = column[String]("ACTION_ID", O.Length(100,varying=true))
    /** Database column ACTION_NM_JA SqlType(VARCHAR), Length(100,true) */
    val actionNmJa: Rep[Option[String]] = column[Option[String]]("ACTION_NM_JA", O.Length(100,varying=true))
    /** Database column ACTION_NM_EN SqlType(VARCHAR), Length(100,true) */
    val actionNmEn: Rep[Option[String]] = column[Option[String]]("ACTION_NM_EN", O.Length(100,varying=true))
    /** Database column FORWARD_SCREEN_ID SqlType(VARCHAR), Length(100,true) */
    val forwardScreenId: Rep[Option[String]] = column[Option[String]]("FORWARD_SCREEN_ID", O.Length(100,varying=true))
    /** Database column ERROR_SCREEN_ID SqlType(VARCHAR), Length(100,true) */
    val errorScreenId: Rep[Option[String]] = column[Option[String]]("ERROR_SCREEN_ID", O.Length(100,varying=true))
    /** Database column ACTION_SUMMARY SqlType(VARCHAR), Length(4000,true) */
    val actionSummary: Rep[Option[String]] = column[Option[String]]("ACTION_SUMMARY", O.Length(4000,varying=true))
    /** Database column PROCESSING_MODE SqlType(VARCHAR), Length(100,true) */
    val processingMode: Rep[Option[String]] = column[Option[String]]("PROCESSING_MODE", O.Length(100,varying=true))

    /** Primary key of ScreenAction (database name PK_SCREEN_ACTION) */
    val pk = primaryKey("PK_SCREEN_ACTION", (screenId, actionId))
  }
  /** Collection-like TableQuery object for table ScreenAction */
  lazy val ScreenAction = new TableQuery(tag => new ScreenAction(tag))

  /** Entity class storing rows of table ScreenEntity
    *  @param screenId Database column SCREEN_ID SqlType(VARCHAR), Length(100,true)
    *  @param lineNo Database column LINE_NO SqlType(DECIMAL)
    *  @param entityNmJa Database column ENTITY_NM_JA SqlType(VARCHAR), Length(100,true)
    *  @param entityNmEn Database column ENTITY_NM_EN SqlType(VARCHAR), Length(100,true) */
  case class ScreenEntityRow(screenId: String, lineNo: scala.math.BigDecimal, entityNmJa: Option[String], entityNmEn: Option[String])
  /** GetResult implicit for fetching ScreenEntityRow objects using plain SQL queries */
  implicit def GetResultScreenEntityRow(implicit e0: GR[String], e1: GR[scala.math.BigDecimal], e2: GR[Option[String]]): GR[ScreenEntityRow] = GR{
    prs => import prs._
      ScreenEntityRow.tupled((<<[String], <<[scala.math.BigDecimal], <<?[String], <<?[String]))
  }
  /** Table description of table SCREEN_ENTITY. Objects of this class serve as prototypes for rows in queries. */
  class ScreenEntity(_tableTag: Tag) extends Table[ScreenEntityRow](_tableTag, Some("GENERATOR"), "SCREEN_ENTITY") {
    def * = (screenId, lineNo, entityNmJa, entityNmEn) <> (ScreenEntityRow.tupled, ScreenEntityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(screenId), Rep.Some(lineNo), entityNmJa, entityNmEn).shaped.<>({r=>import r._; _1.map(_=> ScreenEntityRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column SCREEN_ID SqlType(VARCHAR), Length(100,true) */
    val screenId: Rep[String] = column[String]("SCREEN_ID", O.Length(100,varying=true))
    /** Database column LINE_NO SqlType(DECIMAL) */
    val lineNo: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("LINE_NO")
    /** Database column ENTITY_NM_JA SqlType(VARCHAR), Length(100,true) */
    val entityNmJa: Rep[Option[String]] = column[Option[String]]("ENTITY_NM_JA", O.Length(100,varying=true))
    /** Database column ENTITY_NM_EN SqlType(VARCHAR), Length(100,true) */
    val entityNmEn: Rep[Option[String]] = column[Option[String]]("ENTITY_NM_EN", O.Length(100,varying=true))

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
  class ScreenItem(_tableTag: Tag) extends Table[ScreenItemRow](_tableTag, Some("GENERATOR"), "SCREEN_ITEM") {
    def * = screenId :: itemNo :: itemNmJa :: insideItemNmJa :: itemNmEn :: updatedItemNmEn :: insideItemNmEn :: updatedInsideItemNmEn :: component :: activeKb :: inputKb :: domainCd :: updatedDomainCd :: displayDigit :: updatedDisplayDigit :: requiredKb :: defaultDisplay :: outputColumn :: outputSpec :: updatedOutputSpec :: comments :: codegrpId :: searchConditionKb :: updatedSearchConditionKb :: actionId :: searchresultFlag :: searchresultUpdateFlag :: HNil

    /** Database column SCREEN_ID SqlType(VARCHAR), Length(100,true) */
    val screenId: Rep[String] = column[String]("SCREEN_ID", O.Length(100,varying=true))
    /** Database column ITEM_NO SqlType(DECIMAL) */
    val itemNo: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("ITEM_NO")
    /** Database column ITEM_NM_JA SqlType(VARCHAR), Length(200,true) */
    val itemNmJa: Rep[Option[String]] = column[Option[String]]("ITEM_NM_JA", O.Length(200,varying=true))
    /** Database column INSIDE_ITEM_NM_JA SqlType(VARCHAR), Length(200,true) */
    val insideItemNmJa: Rep[Option[String]] = column[Option[String]]("INSIDE_ITEM_NM_JA", O.Length(200,varying=true))
    /** Database column ITEM_NM_EN SqlType(VARCHAR), Length(200,true) */
    val itemNmEn: Rep[Option[String]] = column[Option[String]]("ITEM_NM_EN", O.Length(200,varying=true))
    /** Database column UPDATED_ITEM_NM_EN SqlType(VARCHAR), Length(200,true) */
    val updatedItemNmEn: Rep[Option[String]] = column[Option[String]]("UPDATED_ITEM_NM_EN", O.Length(200,varying=true))
    /** Database column INSIDE_ITEM_NM_EN SqlType(VARCHAR), Length(200,true) */
    val insideItemNmEn: Rep[Option[String]] = column[Option[String]]("INSIDE_ITEM_NM_EN", O.Length(200,varying=true))
    /** Database column UPDATED_INSIDE_ITEM_NM_EN SqlType(VARCHAR), Length(200,true) */
    val updatedInsideItemNmEn: Rep[Option[String]] = column[Option[String]]("UPDATED_INSIDE_ITEM_NM_EN", O.Length(200,varying=true))
    /** Database column COMPONENT SqlType(VARCHAR), Length(100,true) */
    val component: Rep[Option[String]] = column[Option[String]]("COMPONENT", O.Length(100,varying=true))
    /** Database column ACTIVE_KB SqlType(VARCHAR), Length(100,true) */
    val activeKb: Rep[Option[String]] = column[Option[String]]("ACTIVE_KB", O.Length(100,varying=true))
    /** Database column INPUT_KB SqlType(VARCHAR), Length(100,true) */
    val inputKb: Rep[Option[String]] = column[Option[String]]("INPUT_KB", O.Length(100,varying=true))
    /** Database column DOMAIN_CD SqlType(VARCHAR), Length(100,true) */
    val domainCd: Rep[Option[String]] = column[Option[String]]("DOMAIN_CD", O.Length(100,varying=true))
    /** Database column UPDATED_DOMAIN_CD SqlType(VARCHAR), Length(100,true) */
    val updatedDomainCd: Rep[Option[String]] = column[Option[String]]("UPDATED_DOMAIN_CD", O.Length(100,varying=true))
    /** Database column DISPLAY_DIGIT SqlType(VARCHAR), Length(100,true) */
    val displayDigit: Rep[Option[String]] = column[Option[String]]("DISPLAY_DIGIT", O.Length(100,varying=true))
    /** Database column UPDATED_DISPLAY_DIGIT SqlType(VARCHAR), Length(100,true) */
    val updatedDisplayDigit: Rep[Option[String]] = column[Option[String]]("UPDATED_DISPLAY_DIGIT", O.Length(100,varying=true))
    /** Database column REQUIRED_KB SqlType(VARCHAR), Length(100,true) */
    val requiredKb: Rep[Option[String]] = column[Option[String]]("REQUIRED_KB", O.Length(100,varying=true))
    /** Database column DEFAULT_DISPLAY SqlType(VARCHAR), Length(1000,true) */
    val defaultDisplay: Rep[Option[String]] = column[Option[String]]("DEFAULT_DISPLAY", O.Length(1000,varying=true))
    /** Database column OUTPUT_COLUMN SqlType(VARCHAR), Length(1000,true) */
    val outputColumn: Rep[Option[String]] = column[Option[String]]("OUTPUT_COLUMN", O.Length(1000,varying=true))
    /** Database column OUTPUT_SPEC SqlType(VARCHAR), Length(1000,true) */
    val outputSpec: Rep[Option[String]] = column[Option[String]]("OUTPUT_SPEC", O.Length(1000,varying=true))
    /** Database column UPDATED_OUTPUT_SPEC SqlType(VARCHAR), Length(1000,true) */
    val updatedOutputSpec: Rep[Option[String]] = column[Option[String]]("UPDATED_OUTPUT_SPEC", O.Length(1000,varying=true))
    /** Database column COMMENTS SqlType(VARCHAR), Length(4000,true) */
    val comments: Rep[Option[String]] = column[Option[String]]("COMMENTS", O.Length(4000,varying=true))
    /** Database column CODEGRP_ID SqlType(VARCHAR), Length(128,true) */
    val codegrpId: Rep[Option[String]] = column[Option[String]]("CODEGRP_ID", O.Length(128,varying=true))
    /** Database column SEARCH_CONDITION_KB SqlType(VARCHAR), Length(100,true) */
    val searchConditionKb: Rep[Option[String]] = column[Option[String]]("SEARCH_CONDITION_KB", O.Length(100,varying=true))
    /** Database column UPDATED_SEARCH_CONDITION_KB SqlType(VARCHAR), Length(100,true) */
    val updatedSearchConditionKb: Rep[Option[String]] = column[Option[String]]("UPDATED_SEARCH_CONDITION_KB", O.Length(100,varying=true))
    /** Database column ACTION_ID SqlType(VARCHAR), Length(100,true) */
    val actionId: Rep[Option[String]] = column[Option[String]]("ACTION_ID", O.Length(100,varying=true))
    /** Database column SEARCHRESULT_FLAG SqlType(VARCHAR), Length(100,true) */
    val searchresultFlag: Rep[Option[String]] = column[Option[String]]("SEARCHRESULT_FLAG", O.Length(100,varying=true))
    /** Database column SEARCHRESULT_UPDATE_FLAG SqlType(VARCHAR), Length(100,true) */
    val searchresultUpdateFlag: Rep[Option[String]] = column[Option[String]]("SEARCHRESULT_UPDATE_FLAG", O.Length(100,varying=true))

    /** Primary key of ScreenItem (database name PK_SCREEN_ITEM) */
    val pk = primaryKey("PK_SCREEN_ITEM", screenId :: itemNo :: HNil)
  }
  /** Collection-like TableQuery object for table ScreenItem */
  lazy val ScreenItem = new TableQuery(tag => new ScreenItem(tag))
}
