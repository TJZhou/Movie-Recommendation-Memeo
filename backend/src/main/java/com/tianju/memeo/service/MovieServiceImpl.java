package com.tianju.memeo.service;

import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.MovieRecommended;
import com.tianju.memeo.repository.MovieRecommendedRepository;
import com.tianju.memeo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service(value = "MovieServiceImpl")
public class MovieServiceImpl {

    private MovieRepository movieRepository;
    private MovieRecommendedRepository movieRecommendedRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieRecommendedRepository movieRecommendedRepository) {
        this.movieRepository = movieRepository;
        this.movieRecommendedRepository = movieRecommendedRepository;
    }

    public List<Movie> getUserRecommendation(String userId) {
        int userExists = movieRecommendedRepository.whetherUserExists(userId);
        String movieRecommended;
        if(userExists == 1)
            movieRecommended = movieRecommendedRepository.findById(userId).get().getMovieIds();
        else
            movieRecommended = initUserRecommendation(userId);

        List<Long> movieIds = new ArrayList<>();
        for(String movieId: movieRecommended.split(","))
            movieIds.add(Long.parseLong(movieId));

        List<Movie> movieList = new ArrayList<>();
        for(Long movieId: movieIds)
            movieList.add(movieRepository.findById(movieId).get());

        return movieList;
    }

    /**
     * If this is the first time users use this system.
     * Create a default recommend movie list with the 10 most popular movies.
     * @param userId
     * @return String
     */
    private String initUserRecommendation(String userId) {
        MovieRecommended movieRecommended = new MovieRecommended();
        Collection<Movie> mostPopularMovies = movieRepository.findMostPopularMovies();
        StringBuilder movieIds = new StringBuilder();
        for(Movie m : mostPopularMovies) movieIds.append(m.getMovieId() + ",");
        movieRecommended.setUserId(userId);
        movieRecommended.setMovieIds(movieIds.toString());
        movieRecommendedRepository.save(movieRecommended);
        return movieRecommended.getMovieIds();
    }

    public void deleteUserRecommendation(String userId) {
        movieRecommendedRepository.deleteById(userId);
    }



    private MovieRecommended updateUserRecommendation(String userId) {
        return null;
    }
}
