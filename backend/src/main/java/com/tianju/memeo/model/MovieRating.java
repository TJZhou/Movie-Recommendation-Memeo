package com.tianju.memeo.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The movie rated by user
 */
@Entity
@IdClass(MovieRatingId.class)
@Table(name = "movie_rating")
public class MovieRating implements Serializable {
    @Id
    private String userId;
    @Id
    private Long movieId;
    private Integer rating;
    private String timestamp;

    public MovieRating() {

    }

    public MovieRating(String userId, Long movieId, Integer rating, String timestamp) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.timestamp = timestamp;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
