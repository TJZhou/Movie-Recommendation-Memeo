package com.tianju.memeo.controller;

import com.tianju.memeo.model.Response;
import com.tianju.memeo.model.User;
import com.tianju.memeo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<Response<User>> createUser(@RequestBody User u) {
        User user = userService.createUser(u);
        return ResponseEntity.ok(new Response<>(user));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Response<User>> getUserByUserId(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new Response<>(user));
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<Response<User>> getUserByUserId(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(new Response<>(user));
    }
}
