package com.tianju.memeo

import java.util.Date

import com.tianju.memeo.model.MemeoMovieLog
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._

object SparkStreamFromFlume extends App {

  // Create a new Spark Context
  val conf = new SparkConf().setAppName("Memeo").setMaster("local[*]")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")
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

  // format kafka stream
  val memeoMovieLog = stream.map(_.value.split("--")).map(variables => schema(variables))
  memeoMovieLog.print()

  ssc.start()
  ssc.awaitTermination()

  def schema(variables: Array[String]): MemeoMovieLog = {
    val time = new Date(variables(0))
    val user = variables(1)
    val movieId = variables(2).toLong
    val imdbId = variables(3).toLong
    val genres = variables(4)
    val title = variables(5)
    val rating = variables(6).toDouble
    val rater = variables(7).toLong
    MemeoMovieLog(time, user, movieId, imdbId, genres, title, rating, rater)
  }
}
