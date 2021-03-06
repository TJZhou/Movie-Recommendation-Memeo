package com.tianju.memeo.model;

import java.io.Serializable;

/**
 *  Composite key for MovieRating
 */
public class MovieRatingId implements Serializable {
    private Long userId;
    private Long movieId;

    public MovieRatingId() {
    }

    public MovieRatingId(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
