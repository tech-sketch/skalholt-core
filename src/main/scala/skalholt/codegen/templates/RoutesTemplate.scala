package skalholt.codegen.templates

import skalholt.codegen.util.StringUtil._

case class  Route(request: String, pkgNm: String, actionPkgId: String, actionClassId: String, actionId: String)
object RoutesTemplate {
  val routesTemplate = (routesList: Seq[Route]) =>
    s"""# Routes
# This file defines all application routes (Higher priority routes first)
# ~~~~

# Home page
GET     /                           controllers.Application.index

${
      routesList.map(r =>
        f"""${r.request}%-4s    ${f"/${r.pkgNm}/${r.actionClassId}/${r.actionPkgId}"}%-80s    controllers.${r.pkgNm}.${capitalize(r.actionClassId)}.${decapitalize(r.actionId)}
""").mkString
    }

# Map static resources from the /public folder to the /assets URL path
GET     /assets/*file               controllers.Assets.at(path="/public", file)
"""
}