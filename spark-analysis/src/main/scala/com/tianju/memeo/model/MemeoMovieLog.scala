package com.tianju.memeo.model

import java.sql.Timestamp

case class MemeoMovieLog(time: Timestamp,
                         userId: Int,
                         movieId: Long,
                         title: String,
                         genres: String,
                         userRating: Int)

object MemeoMovieLog {

  def schema(variables: Array[String]): MemeoMovieLog = {
    val timestamp = Timestamp.valueOf(variables(0))
    val userId = variables(1).toInt
    val movieId = variables(2).toLong
    val title = variables(3)
    val genres = variables(4)
    val rating = variables(5).toInt
    MemeoMovieLog(timestamp, userId, movieId, title, genres, rating)
  }

}
