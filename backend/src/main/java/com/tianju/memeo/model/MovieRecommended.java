package com.tianju.memeo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Movie Recommended by user id
 */
@Entity
@Table(name = "movie_recommended")
public class MovieRecommended {
    @Id
    private String userId;
    private String movieIds;

    public MovieRecommended() {
    }

    public MovieRecommended(String userId, String movieIds) {
        this.userId = userId;
        this.movieIds = movieIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(String movieIds) {
        this.movieIds = movieIds;
    }
}
