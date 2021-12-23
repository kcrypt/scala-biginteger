import sbtcrossproject.CrossPlugin.autoImport.crossProject

lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.15"
lazy val scala213 = "2.13.7"
lazy val scala3 = "3.0.0"

lazy val scalatestVersion = "3.2.10"
lazy val jmhVersion = "1.34"

name := "biginteger"
ThisBuild / organization := "ky.korins"
ThisBuild / dynverSeparator := "-"

ThisBuild / scalaVersion := scala3
ThisBuild / crossScalaVersions := Seq()

ThisBuild / scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-unchecked",
  "-deprecation"
)

licenses := LicenseDefinition.licenses
headerLicense := LicenseDefinition.template

// This code isn't ready to publishing yet
ThisBuild / publishTo := None // sonatypePublishToBundle.value

lazy val biginteger = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .enablePlugins(BuildInfoPlugin)
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    publish / skip := false,
    Test / publishArtifact := false,
    buildInfoKeys := Seq(
      BuildInfoKey.action("commit") {
        scala.sys.process.Process("git rev-parse HEAD").!!.trim
      }
    ),
    buildInfoPackage := "ky.korins.math",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % Test,
    ),
    licenses := LicenseDefinition.licenses,
    headerLicense := LicenseDefinition.template
  )
  .jvmSettings(
    scalaVersion := scala3,
    crossScalaVersions := Seq(scala212, scala211, scala213, scala3)
  )
  .jsSettings(
    scalaVersion := scala3,
    crossScalaVersions := Seq(scala211, scala212, scala213, scala3),
    Test / scalaJSStage := FullOptStage
  )
  .nativeSettings(
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala211, scala212, scala213),
    Test / nativeMode:= "release-fast",
    Test / nativeLinkStubs := true
  )

lazy val bench = project.in(file("bench"))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(JmhPlugin)
  .dependsOn(biginteger.jvm)
  .settings(
    libraryDependencies ++= Seq(
      "org.openjdk.jmh" % "jmh-core" % jmhVersion,
      "org.openjdk.jmh" % "jmh-generator-annprocess" % jmhVersion,
    ),
    publish / skip := true,
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala213),
    assembly / assemblyJarName := "bench.jar",
    assembly / mainClass := Some("org.openjdk.jmh.Main"),
    assembly / test := {},
    scalacOptions ++= Seq(
      "-opt:_",
      "-Xlint:_,-nonlocal-return,-unit-special",
    ),
    licenses := LicenseDefinition.licenses,
    headerLicense := LicenseDefinition.template,
    headerSources / excludeFilter := HiddenFileFilter || {
      new SimpleFileFilter(_.getCanonicalPath contains "/original/")
    },
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
      case _ => MergeStrategy.first
    },
    Jmh / assembly := (Jmh / assembly).dependsOn(Jmh / Keys.compile).value
  )
