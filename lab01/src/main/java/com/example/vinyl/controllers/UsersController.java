package com.example.vinyl.controllers;

import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.User;
import com.example.vinyl.repository.UserRepository;
import com.example.vinyl.service.UserService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UsersController {

    private final UserRepository repository;
    private final UserService userService;

    UsersController(UserRepository repository, UserService service) {
        this.repository = repository;
        this.userService = service;
    }

    // Получение всех жанров
    @GetMapping("/userinfo")
    public ResponseEntity<User> getUser() {
        var user = userService.getSessionUser();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

}