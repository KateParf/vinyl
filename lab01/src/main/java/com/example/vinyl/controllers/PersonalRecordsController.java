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
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.User;

@RestController
@RequestMapping("/userrecords")
class RecordsController {

    private final RecordService service;
    private final UserService userService;
    private final PersonalRecordService personalService;

    RecordsController(RecordService service, PersonalRecordService personalService, UserService userService) {
        this.service = service;
        this.personalService = personalService;
        this.userService = userService;
    }

    // Получаем все пластинки юзера
    @GetMapping("/list")
    List<PersonalRecord> userAll() {
        return personalService.getAllRecords();
    }

    // Получаем конкретную пластинку юзера
    @GetMapping("/get/{id}")
    public PersonalRecord getRecord(@PathVariable Integer id) {
        return personalService.getRecord(id);
    }

    // Добавляем пластинку в коллекцию из общего каталога
    @GetMapping("/add/{id}")
    OpResult existRecord(@RequestBody Integer id) {
        User user = userService.getSessionUser();
        personalService.addExistRecord(id, user);
        return new OpResult(true);
    }

    // Редактируем персональную информацию о пластинке
    @PostMapping("/edit/{id}")
    OpResult editRecord(@RequestBody PersonalRecord editRecord) {
        personalService.updateRecord(editRecord);
        return new OpResult(true);
    }

    // Удаляем пластинку из каталога юзера
    @PostMapping("/delete/{id}")
    OpResult deleteRecord(@RequestBody Integer id) {
        personalService.deleteRecord(id);
        return new OpResult(true);
    }

}