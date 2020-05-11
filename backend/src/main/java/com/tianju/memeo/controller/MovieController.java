package com.tianju.memeo.controller;

import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.Response;
import com.tianju.memeo.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response> getRecommendedMovieByUser(@PathVariable String userId) {
        Response resp = new Response(movieServiceImpl.getUserRecommendation(userId));
        return ResponseEntity.ok(resp);
    }

    /**
     * This function is used to update movie recommendation by user.
     * @param userId
     * @param movie: the movie user clicked
     * @return
     */
    @PutMapping(value = "/{userId}")
    public ResponseEntity<Response> updateRecommendation(@PathVariable String userId, @RequestBody Movie movie) {
        movieServiceImpl.updateUserRecommendation(userId, movie);
        return null;
    }

    @GetMapping(value = "/genre")
    public ResponseEntity<Response> getMoviesByGenre(@RequestParam String genre, @RequestParam Long page) {
        Response resp = new Response(movieServiceImpl.getMoviesByGenre(genre, pageSize, page));
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "/count/genre")
    public ResponseEntity<Response> countMoviesByGenre(@RequestParam String genre) {
        Response resp = new Response(movieServiceImpl.countMoviesByGenre(genre));
        return ResponseEntity.ok(resp);
    }

}
