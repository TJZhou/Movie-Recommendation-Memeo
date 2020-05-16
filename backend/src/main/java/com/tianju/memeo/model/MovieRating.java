package com.tianju.memeo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

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
    private Double rating;
    private Date timestamp;

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
