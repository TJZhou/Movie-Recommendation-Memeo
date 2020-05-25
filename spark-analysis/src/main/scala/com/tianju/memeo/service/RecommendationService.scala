package com.tianju.memeo.service

import com.tianju.memeo.model.{MemeoMovieLog, Movie}
import org.apache.spark.rdd.RDD
import com.redis._
import com.tianju.memeo.resource.Resource

import scala.annotation.tailrec

object RecommendationService {

  def KMeansRecommendation(logStream: RDD[String]): Unit = {
    try {
      val logScheme = logStream.map(_.split("--")).map(variables => MemeoMovieLog.schema(variables))
      // get the most recent log of the user
      val distinctUserLog = logScheme.groupBy(_.userId).map(_._2.head)
      // for each movie log in kafka stream
      distinctUserLog.foreach(log => {
        val title = log.title.split("\\(|\\)")
        val year = title(title.length - 1)
        val genres = log.genres.split("\\|")
        val movieList = MySqlConnectionService.preFilterMovies(log.movieId, year, genres)
        val movieWeights = KMeansWeightedRules(movieList, year, genres, log.movieId)
        val initialMeans = Seq(2.0, 4.0, 6.0, 8.0, 10.0)
        val KMeansCluster = KMeans(initialMeans, movieWeights, 10, 10-4)
        resultPersist(log.userId, weightedRules(Movie(log.movieId, 0, log.title, log.genres, log.userRating, 50), year, genres), KMeansCluster)
      })
    } catch {
      case e: RuntimeException => e.printStackTrace()
    }
  }

  /**
   * Select the cluster which has the most closest mean value with input movie weight
   * Persist results into Redis. Key: userId, Value: sequence of movieId separated by comma
   */
  private def resultPersist(userId: Int, weight: Double, KMeansCluster: Map[Double, Seq[(Long, Double)]]) = {
    var closestCluster: Double = -1.0
    val redisClient = new RedisClient(Resource.redisUrl, Resource.redisPort)
    for(cluster <- KMeansCluster) {
      if(closestCluster == -1.0 || Math.abs(cluster._1 - weight) < Math.abs(closestCluster - weight)) {
        closestCluster = cluster._1
      }
    }
    if(KMeansCluster.get(closestCluster).isEmpty) {
      throw new IllegalArgumentException("Empty Cluster!")
    }
    val movieIds = KMeansCluster(closestCluster)
    println(movieIds)
    if(movieIds.size <= 10) {
      val idStr = movieIds.map(movieId => movieId._1).mkString(",")
      redisClient.set(userId, idStr)
    } else {
      val idStr = movieIds.map(movieId => movieId._1).sorted.take(10).mkString(",")
      redisClient.set(userId, idStr)
    }
  }

  /**
   * @param means: mean value of cluster weights
   * @param movieWeights: sequence of (movieId, movieWeight)
   * @param maxIter: maximum iteration
   * @param eta: converged acceptance criteria
   * @return sequence of cluster (means, sequence(movieId, movieWeight))
   */
  @tailrec
  private def KMeans(means: Seq[Double], movieWeights: Seq[(Long, Double)], maxIter: Int, eta: Double): Map[Double, Seq[(Long, Double)]] = {
    val cluster = classify(means, movieWeights)
    // get new mean value
    // c: Map[Double, Seq[(Long, Double)]; c._2: Seq[(Long, Double)
    // cc: (Long, Double); cc._2: Double -- which is movie weight
    val newMeans = (for (c <- cluster) yield c._2.map(cc => cc._2).sum / c._2.length).toSeq
    if (maxIter > 0 && !converged(means, newMeans, eta)) {
      KMeans(newMeans, movieWeights, maxIter - 1, eta)
    } else {
      cluster
    }
  }

  /**
   * This method takes a generic sequence of movieWeights and a generic sequence of means.
   * It returns a generic map collection, which maps each mean to the sequence of
   * points in the corresponding cluster.
   */
  private def classify(means: Seq[Double], movieWeights: Seq[(Long, Double)]): Map[Double, Seq[(Long, Double)]] = {
    movieWeights.groupBy(movieWeight => findClosest(means, movieWeight))
  }

  private def converged(means: Seq[Double], newMeans: Seq[Double], eta: Double): Boolean = {
    for (i <- means.indices) {
      if (Math.abs(means(i) * means(i) - newMeans(i) * newMeans(i)) > eta)
        return false
    }
    true
  }

  /**
   * Find the closest mean value of each point
   */
  private def findClosest(means: Seq[Double], movieWeight: (Long, Double)): Double = {
    var minDistance = Math.abs(means(0) * means(0) - movieWeight._2 * movieWeight._2)
    var closet = means(0)
    var i = 1
    while (i < means.length) {
      val distance = Math.abs(means(i) * means(i) - movieWeight._2 * movieWeight._2)
      if (distance < minDistance) {
        minDistance = distance
        closet = means(i)
      }
      i += 1
    }
    closet
  }

  /**
   * Weight based rule reference: https://ieeexplore.ieee.org/document/8190928
   * Weight based movie recommendation system using K-means algorithm
   */
  private def KMeansWeightedRules(movieList: Seq[Movie], year: String, genres: Array[String], movieId: Long): Seq[(Long, Double)] = {
    movieList.map(movie => {
      (movie.movieId, weightedRules(movie, year, genres))
    })
  }

  private def weightedRules(movie: Movie, year: String, genres: Array[String]): Double = {
    var weight = 0.0
    weight += (if (movie.title.contains(year)) 0.1 else 0.0)
    genres.foreach(genre => {
      weight += (if (movie.genres.contains(genre)) 0.15 else 0.0)
    })
    weight += ratingRules(movie.rating, movie.rater)
    weight
  }

  private def ratingRules(rating: Double, rater: Long): Double = {
    rating match {
      case x if (x >= 1.0 && x < 2.0) => {
        rater match {
          case y if (y >= 0 && y < 5) => 1
          case y if (y >= 5 && y < 25) => 2
          case y if (y >= 25) => 3
        }
      }
      case x if (x >= 2.0 && x < 3.0) => {
        rater match {
          case y if (y >= 0 && y < 5) => 2
          case y if (y >= 5 && y < 25) => 4
          case y if (y >= 25) => 6
        }
      }
      case x if (x >= 3.0 && x < 4.0) => {
        rater match {
          case y if (y >= 0 && y < 5) => 3
          case y if (y >= 5 && y < 25) => 6
          case y if (y >= 25) => 9
        }
      }
      case x if (x >= 4.0 && x <= 5.0) => {
        rater match {
          case y if (y >= 0 && y < 5) => 4
          case y if (y >= 5 && y < 25) => 8
          case y if (y >= 25) => 12
        }
      }
    }
  }
}
