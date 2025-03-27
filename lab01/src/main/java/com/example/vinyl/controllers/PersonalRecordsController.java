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
import com.example.vinyl.service.SearchService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.exceptions.RecordNotFoundException;
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.RecordBrief;
import com.example.vinyl.model.User;

@RestController
@RequestMapping("/userrecords")
class PersonalRecordsController {

    private final UserService userService;
    private final PersonalRecordService personalService;
    private final SearchService searchService;

    PersonalRecordsController(SearchService searchService, PersonalRecordService personalService, UserService userService) {
        this.searchService = searchService;
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
    OpResult existRecord(@PathVariable Integer id) {
        User user = userService.getSessionUser();
        personalService.addExistRecord(id, user);
        return new OpResult(true);
    }

    // из полученных RecordBrief пользователь выбирает одну и мы добавляем ее в бд
    @PostMapping("/addbrief")
    OpResult addByRecordBrief(@RequestBody RecordBrief recordBrief) {
        Record record = searchService.addFullBriefs(recordBrief);
        Integer id = record.getId();
        return this.existRecord(id);
    }

    // Редактируем персональную информацию о пластинке
    @PostMapping("/edit")
    OpResult editRecord(@RequestBody PersonalRecord editRecord) {
        personalService.updateRecord(editRecord);
        return new OpResult(true);
    }

    // Удаляем пластинку из каталога юзера
    @GetMapping("/delete/{id}")
    OpResult deleteRecord(@PathVariable Integer id) {
        personalService.deleteRecord(id);
        return new OpResult(true);
    }

}