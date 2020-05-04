package com.tianju.memeo.repository;

import com.tianju.memeo.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Query(value = "SELECT * FROM movie order by rater limit 10", nativeQuery = true)
    Collection<Movie> findMostPopularMovies();
}
