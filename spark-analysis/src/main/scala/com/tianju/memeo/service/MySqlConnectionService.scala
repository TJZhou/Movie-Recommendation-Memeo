package com.tianju.memeo.service

import java.sql.{Connection, ResultSet}
import com.tianju.memeo.model._
import com.tianju.memeo.resource.Queries
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object MySqlConnectionService{

  def deleteOlderRecommendation(userId: Long): Unit = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    val statement = connection.prepareStatement(Queries.DELETE_OLDER_RECOMMENDATION)
    try {
      statement.setLong(1, userId)
      statement.execute()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      statement.close()
      connection.close()
    }
  }

  def updateMovieRecommendation(userId: Long, movieIds: ArrayBuffer[Long]): Unit = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    val statement = connection.prepareStatement(Queries.UPDATE_MOVIE_RECOMMENDATION)
    try {
      movieIds.foreach(movieId => {
        statement.setLong(1, userId)
        statement.setLong(2, movieId)
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
   * Before implementing K-Means algorithm.
   * Fetch and pre-filter the movie data which contains the log fields
   */
  def preFilterMovies(movieId: Long, movieYear: String, genres: Array[String]): Seq[Movie] = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    var query: String = "SELECT * FROM movie m WHERE (m.title LIKE ?"
    genres.foreach(genre => {
      query += " OR m.genres LIKE ?"
    })
    query += ") AND m.movie_id != ?"
    val statement = connection.prepareStatement(query)
    try {
      statement.setString(1, "%" + movieYear + "%")
      for(idx <- 1 to genres.length) {
        statement.setString(idx+1, "%" + genres(idx-1) + "%")
      }
      statement.setLong(genres.length + 2, movieId)
      val rs: ResultSet = statement.executeQuery()
      var movieList = ListBuffer[Movie]()
      while(rs.next()) {
        val movieId = rs.getLong("movie_id")
        val imdbId = rs.getLong("imdb_id")
        val title = rs.getString("title")
        val genres = rs.getString("genres")
        val rating = rs.getDouble("rating")
        val rater = rs.getLong("rater");
        movieList += Movie(movieId, imdbId, title, genres, rating, rater)
      }
      movieList.toSeq
    } catch {
      case e: Exception => e.printStackTrace()
      Nil
    } finally {
      statement.close()
      connection.close()
    }
  }

  /**
   * Deprecated -- Use Kafka Stream instead.
   * Get recent user log. There data will be used
   * for real time analysis and movie recommendation
   */
  def selectRecentRatingLogByUser(userId: String): Seq[MemeoMovieLog] = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    val statement = connection.prepareStatement(Queries.RECENT_RATING_BY_USER)
    try{
      statement.setString(1, userId)
      val rs: ResultSet = statement.executeQuery()
      var movieList = ListBuffer[MemeoMovieLog]()
      while(rs.next()) {
        val timestamp = rs.getTimestamp("timestamp")
        val userId = rs.getString("user_id")
        val movieId = rs.getLong("movie_id")
        val title = rs.getString("title")
        val genres = rs.getString("genres")
        val rating = rs.getInt("rating");
        movieList += new MemeoMovieLog(timestamp, userId, movieId, title, genres, rating)
      }
      movieList.toSeq
    } catch {
      case e: Exception => e.printStackTrace()
      Nil
    } finally {
      statement.close()
      connection.close()
    }
  }

  def getMovieRatings(): Seq[MovieRating] = {
    val connection: Connection = MySqlConnectionPool.connectionPool.getConnection()
    val statement = connection.createStatement()
    try {
      val rs: ResultSet = statement.executeQuery(Queries.GET_USER_RATING)
      val movieRatingList = new ListBuffer[MovieRating]()
      while(rs.next()) {
        val userId = rs.getInt("user_id")
        val movieId = rs.getLong("movie_id")
        val rating = rs.getInt("rating");
        val timestamp = rs.getTimestamp("timestamp")
        movieRatingList += MovieRating(userId, movieId, rating, timestamp)
      }
      movieRatingList
    } catch {
      case e: Exception => e.printStackTrace()
      Nil
    } finally {
      statement.close()
      connection.close()
    }
  }
}
