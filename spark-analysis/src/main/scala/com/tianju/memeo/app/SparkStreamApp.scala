package com.tianju.memeo.app

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

// real-time streaming recommendation service haven't been implemented yet
object SparkStreamApp extends App {

  // Create a new Spark Context
  val conf = new SparkConf().setAppName("Memeo").setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("WARN")
  val ssc = new StreamingContext(sc, Seconds(10))

  // read data from kafka stream
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092, localhost:9093",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "spark-stream-kafka-1",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val topics = Array("memeo")
  val stream = KafkaUtils.createDirectStream[String, String](
    ssc,
    LocationStrategies.PreferConsistent,
    ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
  )

  ssc.start()
  ssc.awaitTermination()
}
