package com.tianju.memeo.service

import com.tianju.memeo.model.MemeoMovieLog
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.streaming.dstream.InputDStream

object RecommendationService {



  def streamProcess(stream: InputDStream[ConsumerRecord[String, String]]): Unit = {
    val memeoMovieLog = stream
      .map(_.value.split("--"))
      .map(variables => MemeoMovieLog.schema(variables))
    memeoMovieLog.print()
  }
}
