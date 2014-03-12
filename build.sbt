name := "CloudNotes"

version := "1.0"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings
