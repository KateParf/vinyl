package com.example.vinyl.controllers;

import java.util.List;

import com.example.vinyl.model.RoleEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.repository.UserRepository;
import com.example.vinyl.service.UserService;
import com.example.vinyl.model.User;

@RestController
class UsersController {

    private final UserRepository repository;
    private final UserService service;

    UsersController(UserRepository repository, UserService service) {
        this.repository = repository;
        this.service = service;
    }

    // Login
    @PostMapping("/login")
    OpResult existUser(@RequestBody String login, String password) {
        return new OpResult(true);
    }

    // Add new user
    @PostMapping("/register")
    OpResult registerUser(@RequestBody String login, String password, String email, RoleEnum role) {
        return service.registerUser(login, email, password, role);
    }

}