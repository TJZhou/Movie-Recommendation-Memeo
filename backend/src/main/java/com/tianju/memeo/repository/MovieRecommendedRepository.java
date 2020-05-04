package com.tianju.memeo.repository;

import com.tianju.memeo.model.MovieRecommended;
import org.springframework.data.repository.CrudRepository;

public interface MovieRecommendedRepository extends CrudRepository<MovieRecommended, String> {
}
