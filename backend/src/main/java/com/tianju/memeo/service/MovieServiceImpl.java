package com.tianju.memeo.service;

import com.tianju.memeo.interfaces.MoviePOJO;
import com.tianju.memeo.model.Movie;
import com.tianju.memeo.model.MovieRating;
import com.tianju.memeo.model.MovieRatingId;
import com.tianju.memeo.model.MovieRecommend;
import com.tianju.memeo.repository.MovieRatingRepository;
import com.tianju.memeo.repository.MovieRecommendRepository;
import com.tianju.memeo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.sql.Timestamp;

@Service(value = "MovieServiceImpl")
public class MovieServiceImpl {

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer redisPort;
    private final String userLogPath = "log-user/memeo-user.log";
    private final MovieRepository movieRepository;
    private final MovieRatingRepository movieRatingRepository;
    private final MovieRecommendRepository movieRecommendedRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieRecommendRepository movieRecommendedRepository, MovieRatingRepository movieRatingRepository) {
        this.movieRepository = movieRepository;
        this.movieRecommendedRepository = movieRecommendedRepository;
        this.movieRatingRepository = movieRatingRepository;
    }

    public Collection<MoviePOJO> getUserRecommendation(Long userId) {
        boolean userExists = movieRecommendedRepository.existsByUserId(userId);
        if (!userExists) {
            initUserRecommendation(userId);
        }
        return movieRepository.findRecommendedMovieByUserId(userId);
    }

    // Movie doesn't have related userId compared to MoviePOJO
    public Collection<Movie>  getRealTimeUserRecommendation(Long userId) {
        Jedis jedis = new Jedis(redisHost, redisPort);
        String[] movieIds = jedis.get(userId.toString()).split(",");
        List<Movie> movieList = new ArrayList<>();
        for(String movieId : movieIds) {
            Movie movie = movieRepository.findById(Long.parseLong(movieId)).orElseThrow(NoSuchElementException::new);
            movieList.add(movie);
        }
        return movieList;
    }

    /**
     * If this is the first time users use this system.
     * Create a default recommend movie list with the 10 most popular movies.
     */
    private void initUserRecommendation(Long userId) {
        Collection<Movie> mostPopularMovies = movieRepository.findMostPopularMovies();
        for(Movie movie: mostPopularMovies) {
            movieRecommendedRepository.save(new MovieRecommend(userId, movie.getMovieId()));
        }
    }

    public Collection<MoviePOJO> getMoviesByGenre(String genre, Long userId, Long pageSize, Long page) {
        return movieRepository.findMoviesByGenre('%' + genre + '%', userId, pageSize, page * pageSize);
    }

    public Long countMoviesByGenre(String genre) {
        return movieRepository.countMoviesByGenre('%' + genre + '%');
    }

    /**
     * Update movie rating first.
     * Then update the movie recommendation according to current user and current movie rating.
     * Note: this is not an "asynchronized" process. The movie clicked by current user will
     * be added to the flume user log. And Kafka & Spark will consume the log data. After
     * Spark finishes the ASL algorithm the recommended movie will be updated to the database.
     */
    public void updateMovieRating(Long userId, Map<String, String> movie) {
        Long movieId = Long.parseLong(movie.get("movieId"));
        Integer userRating = Integer.parseInt(movie.get("userRating"));
        String genres = movie.get("genres");
        String title = movie.get("title");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MovieRating movieRating = getMovieRating(userId, movieId)
                .orElse(new MovieRating(userId, movieId, userRating, timestamp));
        movieRating.setRating(userRating);
        movieRatingRepository.save(movieRating);
        try (FileWriter fw = new FileWriter(userLogPath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(timestamp+ "--" + userId + "--" + movieId+ "--" + title + "--" + genres + "--" + userRating);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<MovieRating> getMovieRating(Long userId, Long movieId) {
        return movieRatingRepository.findById(new MovieRatingId(userId, movieId));
    }
}
