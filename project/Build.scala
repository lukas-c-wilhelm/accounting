import sbt.Keys._
import sbt._

//noinspection ScalaFileName
object AccountingBuild extends Build {

  lazy val accountingProject = Project(
    id = "accounting",
    base = file("."),
    settings = Defaults.coreDefaultSettings ++ Seq(
      name := "accounting",
      version := "0.0.1-SNAPSHOT",
      scalaVersion := "2.11.7",
      resolvers := Seq(
        "Maven Central" at "http://repo1.maven.org/maven2/",
        "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
      ),
      libraryDependencies ++= Seq(
        "com.typesafe.slick" %% "slick" % slickVersion,
        "com.typesafe.slick" %% "slick-codegen" % slickVersion,
        "ch.qos.logback" % "logback-classic" % "1.1.3",
        "org.scalatest" %% "scalatest" % "2.2.4" % "test",
        "org.scala-lang" % "scala-reflect" % "2.11.7",
        "org.scala-lang.modules" %% "scala-xml" % "1.0.4",
        "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
      )
    )
  )
  // code generation task
  lazy val slick = TaskKey[Seq[File]]("gen-tables")
  lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
    val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
  val url = "jdbc:postgresql://localhost:5432/accounting?user=postgres&password=postgres"
    val jdbcDriver = "org.postgresql.Driver"
    val slickDriver = "slick.driver.PostgresDriver"
    val pkg = "demo"
    toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
    val fname = outputDir + "/demo/Tables.scala"
    Seq(file(fname))
  }
  val slickVersion = "3.1.0"
}