package com.tianju.memeo.service;

import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.MovieRecommended;
import com.tianju.memeo.repository.MovieRecommendedRepository;
import com.tianju.memeo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
        boolean userExists = movieRecommendedRepository.existsByUserId(userId);
        String movieRecommended;
        if(userExists)
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

    public Collection<Movie> getMoviesByGenre(String genre, Long pageSize, Long page) {
        return movieRepository.findMostPopularMoviesByGenre('%'+genre+'%', pageSize, page * pageSize);
    }

    public Long countMoviesByGenre(String genre) {
        return movieRepository.countMoviesByGenre('%'+genre+'%');
    }

    /**
     * Update movie recommendation.
     * Note: this is not an "asynchronized" process. The movie clicked by current user will
     *      be added to the flume user log. And Kafka & Spark will consume the log data. After
     *      Spark finishes the ASL algorithm the recommended movie will be updated to the database.
     * @param userId
     * @param movie
     * @return: Nothing in current process.
     */
    public void updateUserRecommendation(String userId, Movie movie) {
        try(FileWriter fw = new FileWriter("log-user/memeo-user.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(new Date() + "--" +userId + "--" + movie.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
