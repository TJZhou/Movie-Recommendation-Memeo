package com.tianju.memeo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "movie")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private Long imdbId;
    private String genres;
    private String title;
    private Double rating;
    private Integer rater;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getImdbId() {
        return imdbId;
    }

    public void setImdbId(Long imdbId) {
        this.imdbId = imdbId;
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

    public String toString() {
        return movieId + "--" + imdbId + "--" + genres + "--" + title + "--" + rating + "--" + rater;
    }
}
