package com.tianju.memeo.service

import java.sql.Connection

object MySqlConnectionService{
  def updateMovieRecommendation(movieIds: String, userId: String): Unit = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    try {
      val statement = connection.prepareStatement("update movie_recommended set movie_ids = ? where user_id = ? ")
      statement.setString(1, movieIds)
      statement.setString(2, userId)
      statement.execute()
    } catch {
      case e: Exception => e.printStackTrace
    } finally {
      connection.close
    }
  }
}
