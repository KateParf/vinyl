package com.example.vinyl.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.PerformerService;
import com.example.vinyl.service.SearchService;
import com.example.vinyl.dto.PerformerDto;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.Performer;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.RecordBrief;

@RestController
@RequestMapping("/api/performers")
public class PerformersController {

    private final PerformerService service;
    private final SearchService searchService;

    PerformersController(PerformerService service, SearchService searchService) {
        this.service = service;
        this.searchService = searchService;
    }

    // Получение списка исполнителей для которых у нас есть пластинки
    @GetMapping("/list")
    public List<PerformerDto> all() {
        List<Performer> performers = service.getAll();
        List<PerformerDto> dtos = new ArrayList<PerformerDto>();
        for (int i = 0; i < performers.size(); i++) {
            PerformerDto dto = new PerformerDto(performers.get(i).getId(), performers.get(i).getName(), performers.get(i).getPicture(), performers.get(i).getGroup());
            dtos.add(dto);
        }
        return dtos;
    }

    // Получаем конкретного исполнителя
    @GetMapping("/get/{id}")
    public ResponseEntity<PerformerDto> getPerformer(@PathVariable Integer id) {
        Performer performer = service.getById(id);
        if (performer == null) {
            throw new ResourceNotFoundException("Performer", "id", id);
        }
        PerformerDto dto = new PerformerDto(id, performer.getName(), performer.getPicture(), performer.getGroup());
        return ResponseEntity.ok(dto);
    }

    // Получаем пластинки конкретного исполнителя
    @GetMapping("/records/{performerId}")
    public ResponseEntity<List<RecordBrief>> getPerformerRecords(@PathVariable Integer performerId) {
        List<Record> records = service.getRecordsByPerformerId(performerId);
        var res = searchService.RecordsToBriefs(records);
        return ResponseEntity.ok(res);
    }
}