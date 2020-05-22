package com.tianju.memeo.repository;

import com.tianju.memeo.interfaces.MoviePOJO;
import com.tianju.memeo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select * from movie order by rater desc limit 10", nativeQuery = true)
    Collection<Movie> findMostPopularMovies();

    @Query(value =
            "select m.movie_id as movieId, m.imdb_id as imdbId, m.genres, m.rating, m.rater, m.title, " +
            "mr.user_id as userId, mr.rating as userRating, mr.timestamp from " +
            "movie m left join movie_rating mr " +
            "on m.movie_id = mr.movie_id and mr.user_id = ?2 " +
            "where m.genres like ?1 " +
            "order by m.rater desc " +
            "limit ?3 offset ?4 ", nativeQuery = true)
    Collection<MoviePOJO> findMoviesByGenre(String genres, Long userId, Long pageSize, Long page);

    @Query(value =
            "select m.movie_id as movieId, m.imdb_id as imdbId, m.genres, m.rating, m.rater, m.title, " +
            "mr.user_id as userId, mr.rating as userRating, mr.timestamp from " +
            "(movie_recommend md inner join movie m " +
            "on md.user_id = ?1 and m.movie_id = md.movie_id) " +
            "left join " +
            "movie_rating mr " +
            "on m.movie_id = mr.movie_id and md.user_id = mr.user_id ", nativeQuery = true)
    Collection<MoviePOJO> findRecommendedMovieByUserId(Long userId);

    @Query(value = "select count(*) from movie where genres like ?1", nativeQuery = true)
    Long countMoviesByGenre(String genres);
}
