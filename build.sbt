import org.openurp.parent.Dependencies.*
import org.openurp.parent.Settings.*

ThisBuild / organization := "org.openurp.std.info"
ThisBuild / version := "0.0.3"

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

val apiVer = "0.44.0"
val starterVer = "0.3.58"
val baseVer = "0.4.51"
val openurp_trd_api = "org.openurp.trd" % "openurp-trd-api" % apiVer
val openurp_stater_web = "org.openurp.starter" % "openurp-starter-web" % starterVer

lazy val root = (project in file("."))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "openurp-trd-webapp",
    common,
    libraryDependencies ++= Seq(openurp_trd_api),
    libraryDependencies ++= Seq(openurp_stater_web)
  )
