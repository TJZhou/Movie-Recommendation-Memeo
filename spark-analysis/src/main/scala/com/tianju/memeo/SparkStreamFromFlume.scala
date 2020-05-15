package com.tianju.memeo

import org.apache.spark.sql.SparkSession

object SparkStreamFromFlume extends App {

  val spark = SparkSession
    .builder
    .appName("Demo")
    .config("spark.master", "local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  val df = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "memeo")
    .load()

    val query = df
    .writeStream
    .outputMode("append")
    .format("console")
    .start()

  query.awaitTermination()
}
