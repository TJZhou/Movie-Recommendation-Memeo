name := "spark-analysis"

version := "0.1"

scalaVersion := "2.12.11"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.5" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.5" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.5" % "provided"
libraryDependencies += "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % "2.4.5"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.12" % "2.4.5"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.5"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.15"
libraryDependencies += "org.apache.commons" % "commons-dbcp2" % "2.7.0"
libraryDependencies += "net.debasishg" %% "redisclient" % "3.30"

assemblyMergeStrategy in assembly := {
  case "META-INF/services/com.mysql.cj.jdbc.Driver" => MergeStrategy.concat
  case "META-INF/services/java.sql.Driver" => MergeStrategy.concat
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

mainClass in assembly := Some("com.tianju.memeo.app.ModelBuildApp")