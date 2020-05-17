package com.tianju.memeo.app

import com.tianju.memeo.model._
import com.tianju.memeo.resource.Resource
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession

object ModelBuildApp extends App{
//  Logger.getLogger("spark").setLevel(Level.OFF)
//  Logger.getLogger("org").setLevel(Level.OFF)

  val spark = SparkSession.builder().appName("MovieRate").master("local[*]").getOrCreate()

  import spark.implicits._

  val prop=new java.util.Properties()

  prop.put("user", Resource.dbUser)
  prop.put("password", Resource.dbPassword)
  val DS = spark.read
    .jdbc(Resource.dbUrl,"movie_rating",prop)
    .withColumnRenamed("user_id", "userId")
    .withColumnRenamed("movie_id", "movieId")
    .as[MovieRating]

  val ratingData = for(row <- DS) yield (MovieRatingNewScheme(row.userId.hashCode, row.movieId, row.rating, row.timestamp))

  val Array(trainingData, testData) = ratingData.randomSplit(Array(0.8, 0.2))

  val als = new ALS()
    .setMaxIter(20)
    .setRegParam(0.01)
    .setUserCol("userId")
    .setItemCol("movieId")
    .setRatingCol("rating")
  val model = als.fit(trainingData)

  model.setColdStartStrategy("drop")
  val predictions = model.transform(testData)

  val evaluator = new RegressionEvaluator()
    .setMetricName("rmse")
    .setLabelCol("rating")
    .setPredictionCol("prediction")
  val rmse = evaluator.evaluate(predictions)
  println(s"Root-mean-square error = $rmse")

  val userRecs = model.recommendForAllUsers(10)

  userRecs.take(10).foreach(x => println(x))
}
