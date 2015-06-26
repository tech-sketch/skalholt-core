package skalholt.codegen.templates

object DaoTemplate {
  def abstractDaoTemplate(tableName: String, pks: Seq[(String, slick.model.Column)], columns: List[slick.model.Column], slickDriver: String, pkg: String) =
    s"""package daos

import ${pkg}.Tables._
import daos.common.AbstractDao
import ${slickDriver}.api._

abstract class Abstract${tableName}s extends AbstractDao {
  /** insert */
  def insert(e: ${tableName}Row) = DBIO.seq($tableName += e)

  /** update */
  def update(e: ${tableName}Row) =
    $tableName.filter(${pks.zipWithIndex.map { case ((colName, col), index) => s"t.${colName} === ${if (columns.length > 22) s"e(${index})" else s"e.${colName}"}" }.mkString("t => ", " && ", "")}).update(e)

  /** delete */
  def remove(${pks.map { case (colName, col) => s"${colName}: ${col.tpe}" }.mkString(", ")}) =
    $tableName.filter(${pks.map { case (colName, col) => s"t.${colName} === ${colName}" }.mkString("t => ", " && ", "")}).delete

  /** find by id */
  def filterById(${pks.map { case (colName, col) => s"${colName}: ${col.tpe}" }.mkString(", ")}): DBIO[Option[${tableName}Row]] =
    $tableName.filter(${pks.map { case (colName, col) => s"t.${colName} === ${colName}" }.mkString("t => ", " && ", "")}).result.headOption

  /** find all data */
  def all: DBIO[Seq[${tableName}Row]] =
    $tableName.sortBy(f => (${pks.map { case (colName, col) => s"f.${colName}" }.mkString(", ")})).result

  /** filter everything */
  def filter(condition: Any): DBIO[Seq[${tableName}Row]] =
    filter(condition, ${tableName}).result

  /** data length */
  def length(condition: Any): DBIO[Int] =
    filter(condition, ${tableName}).length.result

}"""

  def daoTemplate(tableName: String, slickDriver: String, pkg: String) =
    s"""package daos

import ${pkg}.Tables._
import ${pkg}.Tables.profile.simple._

import play.api.db.DB
import play.api.Play.current
import ${slickDriver}.api._
import scala.language.postfixOps

object ${tableName}s extends Abstract${tableName}s {


}"""

}