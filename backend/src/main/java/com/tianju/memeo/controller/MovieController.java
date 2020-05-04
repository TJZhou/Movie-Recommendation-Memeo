package com.tianju.memeo.controller;

import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.Response;
import com.tianju.memeo.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private MovieServiceImpl movieServiceImpl;

    @Autowired
    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }


    @PostMapping(value = "/{userId}")
    public ResponseEntity<Response> initRecommendedMovieByUser(@PathVariable String userId) {
        return ResponseEntity.ok(movieServiceImpl.createUserRecommendation(userId));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Response> getRecommendedMovieByUser(@PathVariable String userId) {
        return ResponseEntity.ok(movieServiceImpl.movieRecommendedByUser(userId));
    }
}
