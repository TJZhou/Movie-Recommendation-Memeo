package com.tianju.memeo.model

import java.sql.Timestamp

// original scheme from CSV file
case class MovieRatingOldScheme(userId:String, movieId:Long, rating:Double, timestamp:Long)

case class MovieRating(userId:String, movieId:Long, rating:Double, timestamp:Timestamp)

// ASL require userId as int type. Need to use hashCode function to transfer userId type
case class MovieRatingNewScheme(userId:Int, movieId:Long, rating:Double, timestamp:Timestamp)