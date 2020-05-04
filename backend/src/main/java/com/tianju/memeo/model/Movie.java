package com.tianju.memeo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long movie_id;
    private Long imdb_id;
    private String genres;
    private String title;
    private Double rating;
    private Integer rater;

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public Long getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(Long imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRater() {
        return rater;
    }

    public void setRater(Integer rater) {
        this.rater = rater;
    }
}
