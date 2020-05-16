package com.tianju.memeo.model;

import java.io.Serializable;

/**
 *  Composite key for MovieRating
 */
public class MovieRatingId implements Serializable {
    private String userId;
    private Long movieId;

    public MovieRatingId() {
    }

    public MovieRatingId(String userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
