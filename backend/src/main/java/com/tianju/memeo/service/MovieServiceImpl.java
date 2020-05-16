package com.tianju.memeo.service;

import com.tianju.memeo.interfaces.MoviePOJO;
import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.MovieRecommend;
import com.tianju.memeo.repository.MovieRecommendRepository;
import com.tianju.memeo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.sql.Date;

@Service(value = "MovieServiceImpl")
public class MovieServiceImpl {

    private MovieRepository movieRepository;
    private MovieRecommendRepository movieRecommendedRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieRecommendRepository movieRecommendedRepository) {
        this.movieRepository = movieRepository;
        this.movieRecommendedRepository = movieRecommendedRepository;
    }

    public Collection<MoviePOJO> getUserRecommendation(String userId) {
        boolean userExists = movieRecommendedRepository.existsByUserId(userId);
        if (!userExists) {
            initUserRecommendation(userId);
        }
        Collection<MoviePOJO> movies = movieRepository.findRecommendedMovieByUserId(userId);
        return movies;
    }

    /**
     * If this is the first time users use this system.
     * Create a default recommend movie list with the 10 most popular movies.
     *
     * @param userId
     * @return String
     */
    private void initUserRecommendation(String userId) {
        Collection<Movie> mostPopularMovies = movieRepository.findMostPopularMovies();
        for(Movie movie: mostPopularMovies) {
            movieRecommendedRepository.save(new MovieRecommend(userId, movie.getMovieId()));
        }
    }

    public Collection<MoviePOJO> getMoviesByGenre(String genre, String userId, Long pageSize, Long page) {
        return movieRepository.findMoviesByGenre('%' + genre + '%', userId, pageSize, page * pageSize);
    }

    public Long countMoviesByGenre(String genre) {
        return movieRepository.countMoviesByGenre('%' + genre + '%');
    }

    /**
     * Update movie recommendation.
     * Note: this is not an "asynchronized" process. The movie clicked by current user will
     * be added to the flume user log. And Kafka & Spark will consume the log data. After
     * Spark finishes the ASL algorithm the recommended movie will be updated to the database.
     *
     * @param userId
     * @param movie
     * @return: Nothing in current process.
     */
    public void updateUserRecommendation(String userId, Movie movie) {
        try (FileWriter fw = new FileWriter("log-user/memeo-user.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(new Date(System.currentTimeMillis()) + "--" + userId + "--" + movie.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
