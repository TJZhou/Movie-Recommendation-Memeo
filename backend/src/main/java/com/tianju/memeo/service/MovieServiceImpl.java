package com.tianju.memeo.service;

import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.MovieRecommended;
import com.tianju.memeo.repository.MovieRecommendedRepository;
import com.tianju.memeo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service(value = "MovieServiceImpl")
public class MovieServiceImpl {

    private MovieRepository movieRepository;
    private MovieRecommendedRepository movieRecommendedRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieRecommendedRepository movieRecommendedRepository) {
        this.movieRepository = movieRepository;
        this.movieRecommendedRepository = movieRecommendedRepository;
    }

    /**
     * If this is the first time users use this system.
     * Create a default recommend movie list with the 10 most popular movies.
     * @param userId
     * @return Response
     */
    public MovieRecommended initUserRecommendation(String userId) {
        MovieRecommended movieRecommended = new MovieRecommended();
        Collection<Movie> mostPopularMovies = movieRepository.findMostPopularMovies();
        StringBuilder movieIds = new StringBuilder();
        for(Movie m : mostPopularMovies) movieIds.append(m.getMovieId() + ",");
        movieRecommended.setUserId(userId);
        movieRecommended.setMovieIds(movieIds.toString());
        return movieRecommendedRepository.save(movieRecommended);
    }

    private MovieRecommended updateUserRecommendation(String userId) {
        return null;
    }

    public String getUserRecommendation(String userId) {
        Optional<MovieRecommended> movieRecommended = movieRecommendedRepository.findById(userId);
        return movieRecommended.get().getMovieIds();
    }

    public void deleteUserRecommendation(String userId) {
        movieRecommendedRepository.deleteById(userId);
    }
}
