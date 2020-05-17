package com.tianju.memeo.resource

object Queries {

  final val RECENT_RATING_BY_USER =
    "SELECT * FROM movie_rating mr " +
    "LEFT JOIN movie m " +
    "ON mr.movie_id = m.movie_id " +
    "WHERE TO_DAYS( NOW( ) ) - TO_DAYS( mr.timestamp) <= 7 AND mr.user_id = ? "

  final val DELETE_OLDER_RECOMMENDATION =
    "DELETE FROM movie_recommend mr WHERE mr.user_id = ?"

  final val UPDATE_MOVIE_RECOMMENDATION =
    "INSERT INTO movie_recommend() VALUES(?,?)"

  final val GET_USER_RATING =
    "SELECT * FROM memeo.movie_rating"
}
