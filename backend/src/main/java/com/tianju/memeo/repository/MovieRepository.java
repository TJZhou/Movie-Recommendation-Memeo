package com.tianju.memeo.repository;

import com.tianju.memeo.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {

}
