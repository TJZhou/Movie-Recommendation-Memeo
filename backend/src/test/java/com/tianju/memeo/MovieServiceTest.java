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

import java.util.Collection;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MemeoApplication.class})
public class MovieServiceTest {

    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @Test
    public void movieRatingTest() {
        Optional<MovieRating> movieRating = movieServiceImpl.getMovieRating(10001L, 123L);
        Assert.assertFalse(movieRating.isPresent());
        movieRating = movieServiceImpl.getMovieRating(10001L, 296L);
        Assert.assertTrue(movieRating.isPresent());
    }

    @Test
    public void movieRecommendationTest() {
        // skip this test as Elasticache doesn't allow inbound traffic outside AWS VPC
//        Collection<Movie> movieList = movieServiceImpl.getRealTimeUserRecommendation(10000L);
//        Assert.assertEquals(movieList.size(), 10);
//        Assert.assertThrows(NullPointerException.class, () -> movieServiceImpl.getRealTimeUserRecommendation(0L));
    }
}
