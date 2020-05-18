package com.tianju.memeo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The movie rated by user
 */
@Entity
@IdClass(MovieRatingId.class)
@Table(name = "movie_rating")
public class MovieRating implements Serializable {
    @Id
    private Long userId;
    @Id
    private Long movieId;
    private Integer rating;
    private Timestamp timestamp;

    public MovieRating() {

    }

    public MovieRating(Long userId, Long movieId, Integer rating, Timestamp timestamp) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.timestamp = timestamp;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
