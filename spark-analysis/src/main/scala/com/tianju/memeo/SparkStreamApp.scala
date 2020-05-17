package com.tianju.memeo

import com.tianju.memeo.service.RecommendationService
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._

object SparkStreamApp extends App {

  // Create a new Spark Context
  val conf = new SparkConf().setAppName("Memeo").setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("WARN")
  val ssc = new StreamingContext(sc, Seconds(10))

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
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

  RecommendationService.streamProcess(stream)
  ssc.start()
  ssc.awaitTermination()
}
