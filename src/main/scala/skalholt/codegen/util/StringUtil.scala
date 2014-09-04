package skalholt.codegen.util

object StringUtil {

  def parenthesis(value: String, start: String = "(", end: String = ")"): String = if (value.isEmpty()) value else start + value + end

  /**
   * JavaBeansの仕様にしたがってデキャピタライズを行ないます。大文字が2つ以上続く場合は、小文字にならないので注意してください。
   *
   * @param name
   *            名前
   * @return 結果の文字列
   */
  def decapitalize(s: String) =
    if (isEmpty(s) || (s.length >= 2 && s(0).isUpper && s(1).isUpper)) s else s.take(1).toLowerCase + s.drop(1)

  /**
   * JavaBeansの仕様にしたがってキャピタライズを行ないます。大文字が2つ以上続く場合は、大文字にならないので注意してください。
   *
   * @param name
   *            名前
   * @return 結果の文字列
   */
  def capitalize(s: String) =
    if (isEmpty(s)) s else s.take(1).toUpperCase + s.drop(1)

  /**
   * _記法をキャメル記法に変換します。
   *
   * @param s
   *            テキスト
   * @return 結果の文字列
   */
  def camelize(s: String) =
    if (isEmpty(s)) s else s.toLowerCase.split("_").map(capitalize).mkString

  /**
   * キャメル記法を_記法に変換します。
   *
   * @param s
   *            テキスト
   * @return 結果の文字列
   */
  def decamelize(s: String) =
    if (isEmpty(s)) s else s.zipWithIndex.map { case (c, i) => if (i != 0 && c.isUpper) "_" + c else c }.mkString.toUpperCase

  def isEmpty(text: String) = text == null || text.isEmpty
}