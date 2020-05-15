package com.tianju.memeo.model

import java.util.Date

case class MemeoMovieLog(time:Date,
                         user: String,
                         movieId: Long,
                         imdbId: Long,
                         genres: String,
                         title: String,
                         rating: Double,
                         rater: Long)

