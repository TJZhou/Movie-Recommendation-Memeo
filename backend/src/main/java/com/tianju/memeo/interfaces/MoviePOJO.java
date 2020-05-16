package com.tianju.memeo.interfaces;

/**
 * Pojo interface. Used for serializing complex joined query results
 */
public interface MoviePOJO {
    Long getMovieId();
    Long getImdbId();
    String getTitle();
    String getGenres();
    Double getRating();
    Long getRater();
    String getUserId();
    String getUserRating();
    String getTimestamp();
}
