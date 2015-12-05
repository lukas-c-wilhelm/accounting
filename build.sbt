lazy val root = (project in file(".")).
  settings(
    name := "accounting",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.11.7",
    resolvers := Seq(
      "Maven Central" at "http://repo1.maven.org/maven2/",
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
    ),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    )
)
