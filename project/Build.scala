import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName = "CloudNotes"
    val appVersion = "1.0"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "net.vz.mongodb.jackson" %% "play-mongo-jackson-mapper" % "1.1.0"
    )

    val main = play.Project(appName, appVersion, appDependencies)
    .settings(
     version <<= version,
     name <<= name//,
     //resolvers ++= Seq(DefaultMavenRepository, Resolvers.githubRepository, Resolvers.morphiaRepository)
    )


}
