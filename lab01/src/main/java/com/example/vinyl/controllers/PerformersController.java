package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.PerformerService;
import com.example.vinyl.exceptions.PerformerNotFoundException;
import com.example.vinyl.model.Performer;

@RestController
class PerformersController {

    private final PerformerService service;

    PerformersController(PerformerService service) {
        this.service = service;
    }

    // Получение списка исполнителей для которых у нас есть пластинки
    @GetMapping("/performers")
    List<Performer> all() {
        return service.getAll();
    }

    // Получаем конкретного исполнителя
    @GetMapping("/performer/{id}")
    Performer one(@PathVariable Integer id) {
        return service.getById(id);
    }
}