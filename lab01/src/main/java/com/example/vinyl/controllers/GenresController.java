package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.GenreService;
import com.example.vinyl.model.Genre;

@RestController
class GenresController {

    private final GenreService service;

    GenresController(GenreService service) {
        this.service = service;
    }

    // List of items
    @GetMapping("/genres")
    List<Genre> all() {
        return service.getAll();
    }

}