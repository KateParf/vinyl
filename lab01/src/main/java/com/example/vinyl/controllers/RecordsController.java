package com.example.vinyl.controllers;

import java.util.List;

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
import com.example.vinyl.exceptions.RecordNotFoundException;
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.RecordBrief;
import com.example.vinyl.model.User;

@RestController
@RequestMapping("/records")
class RecordsController {

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
    public Record getRecord(@PathVariable Integer id) {
        return service.getRecord(id);
    }

    // Редактируем общую инфу о пластинке
    @PostMapping("/edit")
    OpResult editRecord(@RequestBody Record editRecord) {
        service.updateRecord(editRecord);
        return new OpResult(true);
    }

    // Добавление пластинки вручную в общий каталог
    @PostMapping("/new")
    OpResult newRecord(@RequestBody Record newRecord) {
        service.addNewRecord(newRecord);
        return new OpResult(true);
    }

    // Удаление пластинки из общего каталога
    @GetMapping("/delete/{id}")
    OpResult deleteRecord(@PathVariable Integer id) {
        service.deleteRecord(id);
        return new OpResult(true);
    }

    // Получаем конкретную пластинку по имени
    @PostMapping("/search/name")
    public Record getByName(@RequestBody String name) {
        return service.searchRecord(name);
    }

    // Получаем RecordBrief по штрихкоду
    @PostMapping("/search/barcode")
    List<RecordBrief> getByBarcode(@RequestBody String barcode) {
        List<RecordBrief> recordBriefs = searchService.searchByBarcode(barcode);
        return recordBriefs;
    }

    // Получать мп3 30 сек и проигрывать
    @GetMapping("/{recordId}/play/{trackId}")
    String play(@PathVariable Integer recordId, @PathVariable Integer trackId) {
        String trackName = service.getTrackNameById(recordId, trackId);
        String urlMp3 = playService.getTrackMp3URL(trackName);
        return urlMp3;
    }

}