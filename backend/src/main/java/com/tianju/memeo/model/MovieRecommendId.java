package com.tianju.memeo.model;

import java.io.Serializable;

/**
 *  Composite key for MovieRecommend
 */
public class MovieRecommendId implements Serializable {
    private String userId;
    private Long movieId;

    public MovieRecommendId() {
    }

    public MovieRecommendId(String userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
