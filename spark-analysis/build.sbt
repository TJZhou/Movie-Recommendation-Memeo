name := "spark-analysis"

version := "0.1"

scalaVersion := "2.12.11"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.5"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.5"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.5"
libraryDependencies += "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % "2.4.5"