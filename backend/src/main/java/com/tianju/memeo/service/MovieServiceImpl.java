package com.tianju.memeo.service;

import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.MovieRecommended;
import com.tianju.memeo.model.Response;
import com.tianju.memeo.repository.MovieRecommendedRepository;
import com.tianju.memeo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
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

    public Response createUserRecommendation(String userId) {
        MovieRecommended movieRecommended = new MovieRecommended();
        Collection<Movie> mostPopularMovies = movieRepository.findMostPopularMovies();
        StringBuilder movieIds = new StringBuilder();
        for(Movie m : mostPopularMovies) movieIds.append(m.getMovie_id() + ",");

        movieRecommended.setUser_id(userId);
        movieRecommended.setMovie_ids(movieIds.toString());
        return new Response(movieRecommendedRepository.save(movieRecommended));
    }

    private MovieRecommended updateUserRecommendation(String userId) {
        return null;
    }

    public Response movieRecommendedByUser(String userId) {
        Optional<MovieRecommended> movieRecommended = movieRecommendedRepository.findById(userId);
        return new Response(movieRecommended.get().getMovie_ids());
    }
}
