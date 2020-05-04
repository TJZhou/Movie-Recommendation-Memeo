package com.tianju.memeo;

import com.tianju.memeo.model.MovieRecommended;
import com.tianju.memeo.service.MovieServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MemeoApplication.class})
public class MovieServiceTest {

    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @Test
    public void UserRecommendationTest() {
        // no element in database before init user recommendation
        Assert.assertThrows(NoSuchElementException.class, () -> {
            movieServiceImpl.getUserRecommendation("test-user-fhd219ds2hf21ds91u0932je2io12d901");
        });

        // found user recommendation after init
        MovieRecommended movieRecommended = movieServiceImpl.initUserRecommendation("test-user-fhd219ds2hf21ds91u0932je2io12d901");
        Assert.assertEquals(movieRecommended.getMovieIds(), "128,117,49,106,193609,137,83,55,129,77,");
        String movieIds = movieServiceImpl.getUserRecommendation("test-user-fhd219ds2hf21ds91u0932je2io12d901");
        Assert.assertEquals(movieIds, "128,117,49,106,193609,137,83,55,129,77,");

        // no element in database after delete user recommendation
        movieServiceImpl.deleteUserRecommendation("test-user-fhd219ds2hf21ds91u0932je2io12d901");
        Assert.assertThrows(NoSuchElementException.class, () -> {
            movieServiceImpl.getUserRecommendation("test-user-fhd219ds2hf21ds91u0932je2io12d901");
        });
    }
}
