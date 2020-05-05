package com.tianju.memeo.controller;

import com.tianju.memeo.model.Response;
import com.tianju.memeo.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private MovieServiceImpl movieServiceImpl;

    @Autowired
    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

//    @PostMapping(value = "/{userId}")
//    public ResponseEntity<Response> initRecommendedMovieByUser(@PathVariable String userId) {
//        Response resp = new Response(movieServiceImpl.initUserRecommendation(userId));
//        return ResponseEntity.ok(resp);
//    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Response> getRecommendedMovieByUser(@PathVariable String userId) {
        Response resp = new Response(movieServiceImpl.getUserRecommendation(userId));
        return ResponseEntity.ok(resp);
    }
}
