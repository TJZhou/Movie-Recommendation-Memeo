package com.tianju.memeo.repository;

import com.tianju.memeo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select * from movie order by rater desc limit 10", nativeQuery = true)
    Collection<Movie> findMostPopularMovies();

    @Query(value = "select * from movie where genres like ?1 order by rater desc limit ?2 offset ?3", nativeQuery = true)
    Collection<Movie> findMostPopularMoviesByGenre(String genres, Long pageSize, Long page);

    @Query(value = "select count(*) from movie where genres like ?1", nativeQuery = true)
    Long countMoviesByGenre(String genres);
}
