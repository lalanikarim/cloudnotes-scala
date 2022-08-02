name := "CloudNotes"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.12"

resolvers ++= Seq(
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.reactivemongo" %% "reactivemongo" % "0.11.11",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.11"
)

includeFilter in (Assets, LessKeys.less) := "site.less"
