package com.tianju.memeo.repository;

import com.tianju.memeo.model.MovieRecommended;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRecommendedRepository extends JpaRepository<MovieRecommended, String> {
    // it will return 1 if current user exists in DB otherwise return 0
    @Query(value = "select exists(select * from movie_recommended where user_id = ?1)", nativeQuery = true)
    Integer whetherUserExists(String userId);
}
