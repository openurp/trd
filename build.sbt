import org.openurp.parent.Settings._
import org.openurp.parent.Dependencies._

ThisBuild / organization := "org.openurp.std.info"
ThisBuild / version := "0.0.3-SNAPSHOT"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/openurp/rd"),
    "scm:git@github.com:openurp/rd.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "chaostone",
    name = "Tihua Duan",
    email = "duantihua@gmail.com",
    url = url("http://github.com/duantihua")
  )
)

ThisBuild / description := "OpenURP R&amp;D Webapp"
ThisBuild / homepage := Some(url("http://openurp.github.io/rd/index.html"))

val apiVer = "0.33.2-SNAPSHOT"
val starterVer = "0.3.3"
val baseVer = "0.4.2"
val openurp_trd_api = "org.openurp.trd" % "openurp-trd-api" % apiVer
val openurp_stater_web = "org.openurp.starter" % "openurp-starter-web" % starterVer
lazy val root = (project in file("."))
  .settings()
  .aggregate(admin, webapp)

lazy val admin = (project in file("admin"))
  .settings(
    name := "openurp-trd-admin",
    common,
    libraryDependencies ++= Seq(beangle_commons_core, beangle_data_orm, beangle_webmvc_core, beangle_webmvc_support),
    libraryDependencies ++= Seq(openurp_trd_api, beangle_serializer_text),
    libraryDependencies ++= Seq(openurp_stater_web)
  )

lazy val webapp = (project in file("webapp"))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "openurp-trd-webapp",
    common
  ).dependsOn(admin)

publish / skip := true
