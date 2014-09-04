package skalholt.codegen.templates

object DaoTemplate {
  def abstractDaoTemplate(tableName: String, pks: Seq[(String, scala.slick.model.Column)], columns: List[scala.slick.model.Column], slickDriver: String, pkg: String) =
    s"""package daos

import ${pkg}.Tables._
import daos.common.AbstractDao
import scala.collection.immutable.List
import ${slickDriver}.simple._

abstract class Abstract${tableName}s extends AbstractDao {
  /** insert */
  def insert(e: ${tableName}Row)(implicit s: Session) = $tableName.insert(e)

  /** update */
  def update(e: ${tableName}Row)(implicit s: Session) =
    $tableName.filter(${pks.zipWithIndex.map { case ((colName, col), index) => s"t.${colName} === ${if (columns.length > 22) s"e(${index})" else s"e.${colName}"}" }.mkString("t => ", " && ", "")}).update(e)

  /** delete */
  def remove(${pks.map { case (colName, col) => s"${colName}: ${col.tpe}" }.mkString(", ")})(implicit s: Session) =
    $tableName.filter(${pks.map { case (colName, col) => s"t.${colName} === ${colName}" }.mkString("t => ", " && ", "")}).delete

  /** find by id */
  def filterById(${pks.map { case (colName, col) => s"${colName}: ${col.tpe}" }.mkString(", ")})(implicit s: Session): Option[${tableName}Row] =
    $tableName.filter(${pks.map { case (colName, col) => s"t.${colName} === ${colName}" }.mkString("t => ", " && ", "")}).firstOption

  /** find all data */
  def all(implicit s: Session): List[${tableName}Row] =
    $tableName.sortBy(f => (${pks.map { case (colName, col) => s"f.${colName}" }.mkString(", ")})).list

  /** filter everything */
  def filter(condition: Any)(implicit s: Session): List[${tableName}Row] =
    filter(condition, ${tableName}).list

  /** data length */
  def length(condition: Any)(implicit s: Session): Int =
    filter(condition, ${tableName}).length.run

}"""

  def daoTemplate(tableName: String, slickDriver: String, pkg: String) =
    s"""package daos

import ${pkg}.Tables._
import ${pkg}.Tables.profile.simple._

import play.api.db.DB
import play.api.Play.current
import ${slickDriver}.simple._
import scala.language.postfixOps

object ${tableName}s extends Abstract${tableName}s {


}"""

}