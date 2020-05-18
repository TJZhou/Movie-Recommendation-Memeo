package com.tianju.memeo.service;

import com.tianju.memeo.model.User;
import com.tianju.memeo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service(value = "UserService")
public class UserServiceImpl {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User u) {
        if(!userRepository.existsByUsername(u.getUsername())) {
            User user = new User();
            user.setUsername(u.getUsername());
            return userRepository.save(user);
        }
        return userRepository.getUserByUsername(u.getUsername()).get();
    }

    public User getUserById(Long id) {
        return userRepository.getUserByUserId(id).orElseThrow(() -> {throw new NoSuchElementException("User Not Found");});
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username).orElseThrow(() -> {throw new NoSuchElementException("User Not Found");});
    }
}
