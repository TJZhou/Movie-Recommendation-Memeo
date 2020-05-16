package com.tianju.memeo.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Movie Recommended by user id
 */
@Entity
@IdClass(MovieRecommendId.class)
@Table(name = "movie_recommend")
public class MovieRecommend implements Serializable {
    @Id
    private String userId;
    @Id
    private Long movieId;

    public MovieRecommend() {
    }

    public MovieRecommend(String userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
