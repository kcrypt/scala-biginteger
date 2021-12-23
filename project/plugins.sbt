addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.1.0")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.1.0")

val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("1.8.0")
val scalaNativeJSVersion =
  Option(System.getenv("SCALANATIVE_VERSION")).getOrElse("0.4.2")

addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % scalaJSVersion)
addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % scalaNativeJSVersion)

addSbtPlugin("pl.project13.scala" % "sbt-jmh"                       % "0.4.3")
addSbtPlugin("com.eed3si9n"       % "sbt-assembly"                  % "1.1.0")
addSbtPlugin("com.typesafe.sbt"   % "sbt-native-packager"           % "1.8.1")
addSbtPlugin("com.eed3si9n"       % "sbt-buildinfo"                 % "0.10.0")
addSbtPlugin("com.jsuereth"       % "sbt-pgp"                       % "2.1.1")
addSbtPlugin("org.xerial.sbt"     % "sbt-sonatype"                  % "3.9.10")
addSbtPlugin("com.dwijnand"       % "sbt-dynver"                    % "4.1.1")
addSbtPlugin("de.heikoseeberger"  % "sbt-header"                    % "5.6.0")