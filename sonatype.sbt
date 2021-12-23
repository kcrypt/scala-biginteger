import sbt.url
import xerial.sbt.Sonatype.GitHubHosting

ThisBuild / sonatypeProfileName := "pt.kcry"
ThisBuild / publishMavenStyle := true
ThisBuild / sonatypeProjectHosting := Some(
  GitHubHosting("kcrypt", "scala-biginteger", "k@kcry.pt")
)
ThisBuild / licenses := LicenseDefinition.licenses
ThisBuild / homepage := Some(url("https://github.com/kcrypt/scala-biginteger"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/kcrypt/scala-biginteger"),
    "scm:git@github.com:kcrypt/scala-biginteger.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "catap",
    name = "Kirill A. Korinsky",
    email = "kirill@korins.ky",
    url = url("https://github.com/catap")
  )
)
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"