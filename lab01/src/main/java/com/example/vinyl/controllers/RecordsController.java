package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.exceptions.RecordNotFoundException;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.User;

@RestController
@RequestMapping("/records")
class RecordsController {

    private final RecordService service;
    private final UserService userService;
    private final PersonalRecordService personalService;

    RecordsController(RecordService service, PersonalRecordService personalService, UserService userService) {
        this.service = service;
        this.personalService = personalService;
        this.userService = userService;
    }

    // List of items
    @GetMapping("/list")
    List<Record> all() {
        return service.getAllRecords();
    }

    // Single item
    @GetMapping("/get/{id}")
    public Record getRecord(@PathVariable Integer id) {
        return service.getRecord(id);
    }

    // Edit single item
    @PostMapping("/edit/{id}")
    OpResult editRecord(@RequestBody Record editRecord) {
        service.updateRecord(editRecord);
        return new OpResult(true);
    }

    // Add new single item
    @PostMapping("/new")
    OpResult newRecord(@RequestBody Record newRecord) {
        service.addNewRecord(newRecord);
        return new OpResult(true);
    }

    // Add existing single item
    @GetMapping("/add/{id}")
    OpResult existRecord(@RequestBody Integer id) {
        User user = userService.getSessionUser();
        personalService.addExistRecord(id, user);
        return new OpResult(true);
    }

    @PostMapping("/delete/{id}")
    OpResult deleteRecord(@RequestBody Integer id) {
        service.deleteRecord(id);
        return new OpResult(true);
    }

}