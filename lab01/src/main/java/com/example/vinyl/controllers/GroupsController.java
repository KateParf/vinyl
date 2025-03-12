package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.GroupService;
import com.example.vinyl.exceptions.PerformerNotFoundException;
import com.example.vinyl.model.Group;

@RestController
class GroupsController {

    private final GroupService service;

    GroupsController(GroupService service) {
        this.service = service;
    }

    // List of items
    @GetMapping("/groups")
    List<Group> all() {
        return service.getAll();
    }

    // Single item
    @GetMapping("/group/{id}")
    Group one(@PathVariable Integer id) {
        return service.getById(id);
    }
}