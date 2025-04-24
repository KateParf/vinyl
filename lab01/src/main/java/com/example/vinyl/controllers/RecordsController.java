package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.PlayService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.SearchService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.dto.TrackDto;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.RecordBrief;

@RestController
@RequestMapping("/records")
public class RecordsController {

    private final RecordService service;
    private final UserService userService;
    private final PlayService playService;
    private final SearchService searchService;

    RecordsController(RecordService service, UserService userService, PlayService playService, SearchService searchService) {
        this.service = service;
        this.userService = userService;
        this.playService = playService;
        this.searchService = searchService;
    }

    // Получаем все пластинки
    @GetMapping("/list")
    List<Record> all(
            @RequestParam(required = false) Integer genre_id,
            @RequestParam(required = false) Integer performer_id,
            @RequestParam(required = false) Integer group_id,
            @RequestParam(required = false) Integer decade ) {
        //return service.getAllRecords();
        return service.getFilterRecords(genre_id, performer_id, group_id, decade);
    }

    // Получаем конкретную пластинку по ид
    @GetMapping("/get/{id}")
    public ResponseEntity<Record> getRecord(@PathVariable Integer id) {
        Record record = service.getRecord(id);
        if (record == null) {
            throw new ResourceNotFoundException("Record", "id", id);
        }
        return ResponseEntity.ok(record);
    }

    // Редактируем общую инфу о пластинке
    @PostMapping("/edit")
    public ResponseEntity<Record> editRecord(@RequestBody Record editRecord) {
        Record updatedRecord = service.updateRecord(editRecord);
        if (updatedRecord == null) {
            throw new ResourceNotFoundException("Updated record", "id", editRecord.getId());
        }
        return ResponseEntity.ok(updatedRecord);
    }

    // Добавление пластинки вручную в общий каталог
    @PostMapping("/new")
    public ResponseEntity<?> newRecord(@RequestBody Record newRecord) {
        try {
            var rec = service.addNewRecord(newRecord);
            return ResponseEntity.ok(rec);
        } catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Удаление пластинки из общего каталога
    @GetMapping("/delete/{id}")
    public void deleteRecord(@PathVariable Integer id) {
        service.deleteRecord(id);
    }

    // Получаем конкретную пластинку по имени
    @PostMapping("/search/name")
    public ResponseEntity<List<Record>> getByName(@RequestBody String name) {
        List<Record> records = service.searchRecords(name);
        if (records.isEmpty()) {
            throw new ResourceNotFoundException("Record", "name", name);
        }
        return ResponseEntity.ok(records);
    }

    // Получаем RecordBrief по штрихкоду
    @PostMapping("/search/barcode")
    public ResponseEntity<List<RecordBrief>> getByBarcode(@RequestBody String barcode) {
        List<RecordBrief> records = searchService.searchByBarcode(barcode);
        if (records.isEmpty()) {
            throw new ResourceNotFoundException("Records", "barcode", barcode);
        }
        return ResponseEntity.ok(records);
    }

    // Получать мп3 30 сек 
    // если не найдено ИД рекорда или трека то - ексепшен что ресурс не найден
    @GetMapping("/{recordId}/play/{trackId}")
    public ResponseEntity<TrackDto> play(@PathVariable Integer recordId, @PathVariable Integer trackId) {
        String trackName = service.getTrackNameById(recordId, trackId);
        if (trackName == null) {
            throw new ResourceNotFoundException("Track", "id", 
                String.format("recordId=%d, trackId=%d", recordId, trackId));
        }
        
        String urlMp3 = playService.getTrackMp3URL(trackName);
        if (urlMp3 == null) {
            throw new ResourceNotFoundException("MP3", "track", trackName);
        }
        
        return ResponseEntity.ok(new TrackDto(urlMp3));
    }

}