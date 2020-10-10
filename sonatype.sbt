import sbt.url
import xerial.sbt.Sonatype.GitHubHosting

publishMavenStyle in ThisBuild := true
sonatypeProjectHosting in ThisBuild := Some(GitHubHosting("catap", "scala-biginteger", "kirill@korins.ky"))
licenses in ThisBuild := Seq(
  "Apache-2.0" -> url("https://github.com/catap/scala-biginteger/blob/master/LICENSE.txt")
)
homepage in ThisBuild := Some(url("https://github.com/catap/scala-biginteger"))
scmInfo in ThisBuild := Some(
  ScmInfo(
    url("https://github.com/catap/scala-biginteger"),
    "scm:git@github.com:catap/scala-biginteger.git"
  )
)
developers in ThisBuild := List(
  Developer(id="catap", name="Kirill A. Korinsky", email="kirill@korins.ky", url=url("https://github.com/catap"))
)