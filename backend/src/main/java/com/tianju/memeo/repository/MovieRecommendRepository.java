package com.tianju.memeo.repository;

import com.tianju.memeo.model.MovieRecommend;
import com.tianju.memeo.model.MovieRecommendId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRecommendRepository extends JpaRepository<MovieRecommend, MovieRecommendId> {
    Boolean existsByUserId(String userId);
}
