package com.tianju.memeo.model;

import java.io.Serializable;

/**
 *  Composite key for MovieRecommend
 */
public class MovieRecommendId implements Serializable {
    private Long userId;
    private Long movieId;

    public MovieRecommendId() {
    }

    public MovieRecommendId(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
