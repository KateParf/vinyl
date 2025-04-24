package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.GroupService;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.Group;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    private final GroupService service;

    GroupsController(GroupService service) {
        this.service = service;
    }

    // Получение списка групп для которых у нас есть пластинки
    @GetMapping("/list")
    public List<Group> all() {
        return service.getAll();
    }

    // Получение конкретной группы
    // если для такого ид нет записи то вернем 404
    @GetMapping("/get/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Integer id) {
        Group group = service.getById(id);
        if (group == null) {
            throw new ResourceNotFoundException("Group", "id", id);
        }
        return ResponseEntity.ok(group);
    }
}