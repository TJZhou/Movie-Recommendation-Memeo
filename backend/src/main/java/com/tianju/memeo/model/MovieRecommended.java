package com.tianju.memeo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Movie Recommended by user id
 */
@Entity
public class MovieRecommended {
    @Id
    private String user_id;
    private String movie_ids;

    public MovieRecommended() {
    }

    public MovieRecommended(String user_id, String movie_ids) {
        this.user_id = user_id;
        this.movie_ids = movie_ids;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMovie_ids() {
        return movie_ids;
    }

    public void setMovie_ids(String movie_ids) {
        this.movie_ids = movie_ids;
    }
}
