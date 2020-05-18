package com.tianju.memeo.repository;

import com.tianju.memeo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> getUserByUserId(Long id);
    Optional<User> getUserByUsername(String username);
}
