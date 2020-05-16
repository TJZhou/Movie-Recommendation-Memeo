package com.tianju.memeo.repository;

import com.tianju.memeo.model.MovieRating;
import com.tianju.memeo.model.MovieRatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRatingRepository extends JpaRepository<MovieRating, MovieRatingId> {
}
