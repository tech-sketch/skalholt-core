package skalholt.codegen.templates

import skalholt.codegen.util.StringUtil._

case class Menu(pkgNm: String, actionId: String, actionClassId: String)
object MenuTemplate {
  def menuTemplate(menus: Seq[Menu]) =
    s"""@()
  <ul class="dropdown-menu">
    ${
      menus.map(m => s"""<li><a href ="@controllers.${m.pkgNm}.routes.${capitalize(m.actionClassId)}.index">${m.actionClassId}</a></li>""").mkString(s"""
    """)
    }
  </ul>
"""

}