import sbtcrossproject.CrossPlugin.autoImport.crossProject

lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.13"
lazy val scala213 = "2.13.6"
lazy val scala3 = "3.0.0-RC3"

lazy val scalatestVersion = "3.2.8"
lazy val jmhVersion = "1.31"

name := "biginteger"
organization in ThisBuild := "ky.korins"
version in ThisBuild := "1.0.0-SNAPSHOT"
scalaVersion in ThisBuild := scala3
crossScalaVersions in ThisBuild := Seq()

scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.8",
  "-unchecked",
  "-deprecation"
)

licenses := LicenseDefinition.licenses
headerLicense := LicenseDefinition.template

// This code isn't ready to publishing yet
publishTo in ThisBuild := None // sonatypePublishToBundle.value

lazy val biginteger = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .enablePlugins(BuildInfoPlugin)
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    skip in publish := false,
    publishArtifact in Test := false,
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
    scalaVersion := scala213,
    // scalatests doesn't support scala3 for scala.js yet
    crossScalaVersions := Seq(scala211, scala212, scala213)
  )
  .nativeSettings(
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala211, scala212, scala213),
    nativeLinkStubs := true
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
    skip in publish := true,
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala213),
    assemblyJarName in assembly := "bench.jar",
    mainClass in assembly := Some("org.openjdk.jmh.Main"),
    test in assembly := {},
    scalacOptions ++= Seq(
      "-opt:_",
      "-Xlint:_,-nonlocal-return,-unit-special",
    ),
    licenses := LicenseDefinition.licenses,
    headerLicense := LicenseDefinition.template,
    excludeFilter in headerSources := HiddenFileFilter || {
      new SimpleFileFilter(_.getCanonicalPath contains "/original/")
    },
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
      case _ => MergeStrategy.first
    },
    assembly in Jmh := (assembly in Jmh).dependsOn(Keys.compile in Jmh).value
  )
