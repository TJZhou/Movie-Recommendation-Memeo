package com.tianju.memeo.app

import com.tianju.memeo.service.RecommendationService
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * online streaming K-Means recommendation service
 * Tianju Zhou - 05.24.2020
 */
object SparkStreamApp extends App {

//  final val testLog = Seq(
//    "2020-05-18 15:26:25.554--10001--3435--Double Indemnity (1944)--Crime|Drama|Film-Noir--3",
//    "2020-05-18 15:26:31.996--10001--5995--Pianist, The (2002)--Drama|War--5",
//    "2020-05-18 15:29:37.543--10003--356--Forrest Gump (1994)--Comedy|Drama|Romance|War--5",
//    "2020-05-18 15:29:39.031--10003--318--Shawshank Redemption, The (1994)--Crime|Drama--5")

  Logger.getLogger("spark").setLevel(Level.WARN)
  Logger.getLogger("org").setLevel(Level.WARN)

  val conf = new SparkConf().setAppName("Memeo").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(10))

  // read data from kafka stream
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

  stream.foreachRDD(record => {
    RecommendationService.KMeansRecommendation(record.map(_.value()))
  })

  ssc.start()
  ssc.awaitTermination()
}
