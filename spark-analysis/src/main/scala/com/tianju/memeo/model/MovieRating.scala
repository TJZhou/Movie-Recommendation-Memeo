package com.tianju.memeo.model

import java.sql.Timestamp

// original scheme from CSV file
case class MovieRatingOldScheme(userId:String, movieId:Long, rating:Double, timestamp:Long)

//case class MovieRating(userId:String, movieId:Long, rating:Double, timestamp:Timestamp)
case class MovieRating(userId:Int, movieId:Long, rating:Double, timestamp:Timestamp)
