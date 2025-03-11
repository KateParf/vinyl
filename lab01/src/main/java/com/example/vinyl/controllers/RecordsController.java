package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.repository.RecordRepository;
import com.example.vinyl.model.Record;

@RestController
class RecordsController {

    private final RecordRepository repository;

    RecordsController(RecordRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/records/list")
    List<Record> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    // Single item
    @GetMapping("/records/get/{id}")
    Record one(@PathVariable Integer id) {

        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @PostMapping("/records/edit/{id}")
    OpResult editRecord(@RequestBody Record editRecord) {
        repository.findById(editRecord.getId())
                .map(record -> {
                    record.setName(editRecord.getName());
                    return repository.save(record);
                })
                .orElseGet(() -> {
                    return repository.save(editRecord);
                });
        return new OpResult(true);
    }

    @PostMapping("/records/add/{id}")
    OpResult newRecord(@RequestBody Record newRecord) {
        repository.save(newRecord);
        return new OpResult(true);
    }

    @PostMapping("/records/delete/{id}")
    OpResult deleteRecord(@RequestBody Integer id) {
        repository.deleteById(id);
        return new OpResult(true);
    }

}