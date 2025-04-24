package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.PerformerService;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;

@RestController
@RequestMapping("/performers")
public class PerformersController {

    private final PerformerService service;

    PerformersController(PerformerService service) {
        this.service = service;
    }

    // Получение списка исполнителей для которых у нас есть пластинки
    @GetMapping("/list")
    public List<Performer> all() {
        return service.getAll();
    }

    // Получаем конкретного исполнителя
    @GetMapping("/get/{id}")
    public ResponseEntity<Performer> getPerformer(@PathVariable Integer id) {
        Performer performer = service.getById(id);
        if (performer == null) {
            throw new ResourceNotFoundException("Performer", "id", id);
        }
        return ResponseEntity.ok(performer);
    }
}