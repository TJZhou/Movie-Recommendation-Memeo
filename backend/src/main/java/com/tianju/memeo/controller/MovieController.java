package com.tianju.memeo.controller;

import com.tianju.memeo.interfaces.MoviePOJO;
import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.Response;
import com.tianju.memeo.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private MovieServiceImpl movieServiceImpl;

    // this can be changed according to frontend requirements
    private final Long pageSize = 30l;

    @Autowired
    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Response<MoviePOJO>> getRecommendedMovieByUser(@PathVariable Long userId) {
        Response<MoviePOJO> resp = new Response<>(movieServiceImpl.getUserRecommendation(userId));
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "/recommend/{userId}")
    public ResponseEntity<Response<Movie>> getRealTimeRecommendationByUser(@PathVariable Long userId) {
        Response<Movie> resp = new Response<>(movieServiceImpl.getRealTimeUserRecommendation(userId));
        return ResponseEntity.ok(resp);
    }

    /**
     * The user rate the movie.
     * This function will trigger the Spark recommendation analysis
     * according to the movie rating (rating score: 1-5)
     * @param userId
     * @param movie: the movie user rated, it's a MoviePOJO like object
     * @return
     */
    @PutMapping(value = "/{userId}")
    public ResponseEntity<Void> updateMovieRating(@PathVariable Long userId, @RequestBody Map<String, String> movie) {
        movieServiceImpl.updateMovieRating(userId, movie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/genre")
    public ResponseEntity<Response<MoviePOJO>> getMoviesByGenre(@RequestParam String genre, @RequestParam Long userId, @RequestParam Long page) {
        Response<MoviePOJO> resp = new Response<>(movieServiceImpl.getMoviesByGenre(genre, userId, pageSize, page));
        return ResponseEntity.ok(resp);
    }

    /**
     * @param genre
     * @return: total number of movies according to the current genre
     */
    @GetMapping(value = "/count/genre")
    public ResponseEntity<Response<Long>> countMoviesByGenre(@RequestParam String genre) {
        Response<Long> resp = new Response<>(movieServiceImpl.countMoviesByGenre(genre));
        return ResponseEntity.ok(resp);
    }

}
