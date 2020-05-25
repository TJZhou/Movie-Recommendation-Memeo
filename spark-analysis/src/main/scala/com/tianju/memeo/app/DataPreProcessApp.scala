package edu.neu.coe.csye7200

import java.sql.Timestamp

import com.tianju.memeo.model._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, Encoders, SparkSession}

/**
 *  Data preprocess & Data cleaning
 */
object DataPreProcessApp extends App{

  Logger.getLogger("spark").setLevel(Level.WARN)
  Logger.getLogger("org").setLevel(Level.WARN)

  val spark = SparkSession.builder().appName("MovieRate").master("local[*]").getOrCreate()

  import spark.implicits._

  // read csv create data frame
  val ratingDF = spark.read
    .format("csv")
    .option("header", "true")
    .schema(Encoders.product[MovieRatingOldScheme].schema)
    .load("src/data/ratings.csv")

  val movieDF = spark.read
    .format("csv")
    .option("header", "true")
    .schema(Encoders.product[Movie].schema)
    .load("src/data/movies.csv")

  // create data set
  val ratingDS = ratingDF.as[MovieRatingOldScheme].filter(!_.userId.isEmpty)
  val movieDS = movieDF.as[Movie]

  // change timestamp to sql timestamp format
  val newRatingDS : Dataset[MovieRating] =
    for(rating <- ratingDS) yield MovieRating(rating.userId.toInt, rating.movieId, rating.rating, new Timestamp(rating.timestamp))
  newRatingDS.write.option("header","true").csv("src/rating.csv")

  // aggregate all ratings by movieId and calculate the average rating.
  val rater = newRatingDS.groupBy("movieId").agg(count("*").alias("rater"))
  val rate  = newRatingDS.groupBy("movieId").agg(avg("rating").as("rating"))
  val rating = rater.join(rate, "movieId")
  movieDS.join(rating, "movieId").sort("movieId").coalesce(1).write.option("header","true").csv("src/movie.csv")

  spark.stop()
}
