package com.tianju.memeo.service

import java.sql.{Connection, ResultSet}

import com.tianju.memeo.model.MemeoMovieLog
import com.tianju.memeo.resource.Queries

object MySqlConnectionService{

  def deleteOlderRecommendation(userId: String): Unit = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    val statement = connection.prepareStatement(Queries.DELETE_OLDER_RECOMMENDATION)
    try {
      statement.setString(1, userId)
      statement.execute()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      statement.close()
      connection.close()
    }
  }

  def updateMovieRecommendation(movieIds: List[Long], userId: String): Unit = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    val statement = connection.prepareStatement(Queries.UPDATE_MOVIE_RECOMMENDATION)
    try {
      movieIds.foreach(movieId => {
        statement.setLong(1, movieId)
        statement.setString(2, userId)
        statement.addBatch()
      })
      statement.executeBatch()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      statement.close()
      connection.close()
    }
  }

  /**
   * Get recent user log. There data will be used
   * for real time analysis and movie recommendation
   */
  def selectRecentRatingLogByUser(userId: String): List[MemeoMovieLog] = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    val statement = connection.prepareStatement(Queries.RECENT_RATING_BY_USER)
    try{
      statement.setString(1, userId)
      val rs: ResultSet = statement.executeQuery()
      var movieList = List[MemeoMovieLog]()
      while(rs.next()) {
        val timestamp = rs.getTimestamp("timestamp")
        val userId = rs.getString("userId")
        val movieId = rs.getLong("movieId")
        val title = rs.getString("title")
        val genres = rs.getString("genres")
        val rating = rs.getInt("rating");
        movieList ::= new MemeoMovieLog(timestamp, userId, movieId, title, genres, rating)
      }
      movieList
    } catch {
      case e: Exception => e.printStackTrace()
        Nil
    } finally {
      statement.close()
      connection.close()
    }
  }
}
