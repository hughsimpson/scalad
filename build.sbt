/** Project */
name := "Scalad"

/** DON'T FORGET TO CHANGE version.sbt */
version := "1.3.0"

organization := "org.cakesolutions"

scalaVersion := "2.10.4"

/** Shell */
shellPrompt := { state => System.getProperty("user.name") + "> " }

shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

/** Dependencies */
resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"

publishMavenStyle := true

publishTo := {
    val repo = if (isSnapshot.value) "snapshots" else "releases"
    val url = s"https://nexus.sphoniclabs.net/nexus/content/repositories/$repo"
    Some(repo at url)
}

credentials += Credentials(Path.userHome / ".ivy2" / ".sphonic_credentials")


libraryDependencies <<= scalaVersion { scala_version =>
    Seq(
        "org.mongodb"          % "mongo-java-driver"   % "2.10.0",
        "com.typesafe"         % "config"              % "1.0.0",
        "io.spray"             %% "spray-json"          % "1.3.1",
        "org.specs2"           %% "specs2"              % "1.13" % "test",
        "org.scalacheck"       %% "scalacheck"          % "1.10.0" % "test"
    )
}

/** Compilation */
javacOptions ++= Seq("-Xmx1812m", "-Xms512m", "-Xss6m")

javaOptions += "-Xmx2G"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

maxErrors := 20

pollInterval := 1000

logBuffered := false

cancelable := true

testOptions := Seq(Tests.Filter(s =>
  Seq("Spec", "Suite", "Test", "Unit", "all").exists(s.endsWith(_)) &&
    !s.endsWith("FeaturesSpec") ||
    s.contains("UserGuide") ||
    s.contains("index") ||
    s.matches("org.specs2.guide.*")))

/** Console */
initialCommands in console := "import org.cakesolutions.scalad._"
