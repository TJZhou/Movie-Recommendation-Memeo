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
    private Long userId;
    @Id
    private Long movieId;

    public MovieRecommend() {
    }

    public MovieRecommend(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
