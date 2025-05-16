package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.GenreService;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;

@RestController
public class GenresController {

    private final GenreService service;

    GenresController(GenreService service) {
        this.service = service;
    }

    // Получение всех жанров
    @GetMapping("/api/genres")
    List<Genre> all() {
        return service.getAll();
    }

}