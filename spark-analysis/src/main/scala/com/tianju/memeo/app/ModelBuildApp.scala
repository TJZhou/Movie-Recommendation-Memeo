package com.tianju.memeo.app

import com.tianju.memeo.model._
import com.tianju.memeo.resource.Resource
import com.tianju.memeo.service.{MySqlConnectionPool, MySqlConnectionService}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession

/**
 * offline ALS recommendation service
 * Tianju Zhou - 05.17.2020
 */
object ModelBuildApp extends App{
  Logger.getLogger("spark").setLevel(Level.OFF)
  Logger.getLogger("org").setLevel(Level.OFF)

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

  val Array(trainingData, testData) = DS.randomSplit(Array(0.8, 0.2))

  trainingData.persist()

  val als = new ALS()
    .setMaxIter(10)
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

//  save model is necessary
//  model.save("src/data/movie_recommendation_v1.model")

  // count user who gives rating
  println(trainingData.select("userId").distinct().count())

  // update movie recommendations
  val recommendations = model.recommendForAllUsers(10).as[ALSModel]
  recommendations.foreach(x => {
    MySqlConnectionService.deleteOlderRecommendation(x.userId)
    MySqlConnectionService.updateMovieRecommendation(x.userId, x.recommendations.map(_._1))
  })

  MySqlConnectionPool.connectionPool.close()
}
