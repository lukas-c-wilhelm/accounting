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
        "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
        "com.typesafe" % "config" % "1.3.0"
      ),
      slick <<= slickCodeGenTask // register manual sbt command
      // sourceGenerators in Compile <+= slickCodeGenTask // register automatic code generation on every compile, remove for only manual use
    )
  )
  // code generation task
  lazy val slick = TaskKey[Seq[File]]("gen-tables")
  lazy val slickCodeGenTask = (baseDirectory, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
    val outputDir = (dir / "src/main/scala-2.11").getPath // place generated files in sbt's managed sources folder
  val url = "jdbc:postgresql://localhost:5432/accounting?user=postgres&password=postgres"
    val jdbcDriver = "org.postgresql.Driver"
    val slickDriver = "slick.driver.PostgresDriver"
    val pkg = "wilhelm.accounting.persistence"
    toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
    val filename = outputDir + "/wilhelm/accounting/persistence/Tables.scala"
    Seq(file(filename))
  }
  val slickVersion = "3.1.0"
}