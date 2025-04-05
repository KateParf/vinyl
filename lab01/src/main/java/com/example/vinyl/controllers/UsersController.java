package com.example.vinyl.controllers;

import com.example.vinyl.repository.UserRepository;
import com.example.vinyl.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UsersController {

    private final UserRepository repository;
    private final UserService service;

    UsersController(UserRepository repository, UserService service) {
        this.repository = repository;
        this.service = service;
    }


}