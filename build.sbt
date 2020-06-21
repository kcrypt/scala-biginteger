import sbtcrossproject.CrossPlugin.autoImport.crossProject

lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.11"
lazy val scala213 = "2.13.2"

lazy val scalatestVersion = "3.1.2"

name := "biginteger"
organization in ThisBuild := "ky.korins"
version in ThisBuild := "1.0.0-SNAPSHOT"
scalaVersion in ThisBuild := scala213
crossScalaVersions in ThisBuild := Seq(scala212, scala211, scala213)
scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation")

publishTo in ThisBuild := sonatypePublishTo.value
sonatypeProfileName in ThisBuild := "ky.korins"
publishMavenStyle in ThisBuild := true
sonatypeProjectHosting in ThisBuild := Some(xerial.sbt.Sonatype.GitHubHosting("catap", "scala-biginteger", "kirill@korins.ky"))
licenses in ThisBuild := Seq("Apache-2.0" -> url("https://github.com/catap/scala-biginteger/blob/master/LICENSE.txt"))
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

skip in publish := true

lazy val biginteger = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(
    skip in publish := false,
    publishArtifact in Test := false,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % Test,
    )
  )
  .nativeSettings(
    scalaVersion := scala211,
    crossScalaVersions := Seq(scala211),
    nativeLinkStubs := true,
    Test / test := {}
  )

lazy val bench = project.in(file("bench"))
  .dependsOn(biginteger.jvm)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(JmhPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.openjdk.jmh" % "jmh-core" % "1.23",
      "org.openjdk.jmh" % "jmh-generator-annprocess" % "1.23",
    ),
    crossScalaVersions := Seq(scala213),
    assemblyJarName in assembly := "bench.jar",
    mainClass in assembly := Some("org.openjdk.jmh.Main"),
    test in assembly := {},
    scalacOptions ++= Seq(
      "-opt:_",
      "-Xlint:_,-nonlocal-return,-unit-special",
    ),
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
      case _ => MergeStrategy.first
    },
    assembly in Jmh := (assembly in Jmh).dependsOn(Keys.compile in Jmh).value
  )

