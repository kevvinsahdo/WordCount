name := "WordCount"

version := "0.1"

scalaVersion := "2.12.0"

fork in run := true
javaOptions in run ++= Seq(
  "-Dlog4j.configuration=log4j.properties"
)

outputStrategy := Some(StdoutOutput)

val sparkVersion = "3.0.0"

libraryDependencies ++=  Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.scalatest" %% "scalatest" % "3.2.0" % "test"
)