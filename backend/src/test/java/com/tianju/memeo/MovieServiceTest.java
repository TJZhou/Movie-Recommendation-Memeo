package com.tianju.memeo;

import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.MovieRating;
import com.tianju.memeo.service.MovieServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MemeoApplication.class})
public class MovieServiceTest {

    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @Test
    public void movieRatingTest() {
        Optional<MovieRating> movieRating = movieServiceImpl.getMovieRating(10000L, 123L);
        Assert.assertFalse(movieRating.isPresent());
        movieRating = movieServiceImpl.getMovieRating(10000L, 296L);
        Assert.assertTrue(movieRating.isPresent());
    }
}
