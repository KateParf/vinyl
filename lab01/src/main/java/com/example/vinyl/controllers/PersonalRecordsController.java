package com.example.vinyl.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.vinyl.dto.EditPersonalRecordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.SearchService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.RecordBrief;
import com.example.vinyl.model.User;

@RestController
@RequestMapping("/api/userrecords")
public class PersonalRecordsController {

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
    public List<RecordBrief> userAll() {
        var user = userService.getSessionUser();
        List<PersonalRecord> res = personalService.getAllRecords(user);
        List<Record> records = new ArrayList<Record>();
        for (int i = 0; i < res.size(); i++) {
            var rec = res.get(i).getRecord();
            rec.setId(res.get(i).getId()); // переназначаем в юзерский бриф ид из юзер рекорда
            records.add(rec);
        }
        var briefs = searchService.RecordsToBriefs(records); 
        return briefs;
    }

    // Получаем все ид глобальных пластинок у юзера
    @GetMapping("/list/ids")
    public ResponseEntity<List<Integer>> getUserRecordIds() {
        var user = userService.getSessionUser();
        List<Integer> recordIds = personalService.getRecordIdsInUserCollection(user);
        return ResponseEntity.ok(recordIds);
    }

    // Получаем конкретную пластинку юзера
    @GetMapping("/get/{id}")
    public ResponseEntity<PersonalRecord> getRecord(@PathVariable Integer id) {
        PersonalRecord record = personalService.getRecord(id);
        if (record == null) {
            throw new ResourceNotFoundException("Personal record", "id", id);
        }
        return ResponseEntity.ok(record);
    }

    // Добавляем пластинку в коллекцию из общего каталога
    @GetMapping("/add/{id}")
    public ResponseEntity<PersonalRecord> addExistingRecord(@PathVariable Integer id) {
        var user = userService.getSessionUser();
        PersonalRecord record = personalService.addExistRecord(id, user);
        if (record == null) {
            throw new ResourceNotFoundException("Record", "id", id);
        }
        return ResponseEntity.ok(record);
    }

    // из полученных RecordBrief пользователь выбирает одну и мы добавляем ее в бд
    @PostMapping("/addbrief")
    public ResponseEntity<PersonalRecord> addByRecordBrief(@RequestBody RecordBrief recordBrief) {
        Record record = searchService.addFullBriefs(recordBrief);
        Integer id = record.getId();
        return this.addExistingRecord(id);
    }

    // Редактируем персональную информацию о пластинке
    @PostMapping("/edit")
    public ResponseEntity<PersonalRecord> editRecord(@RequestBody EditPersonalRecordDto editPersonalRecordDto) {
        var user = userService.getSessionUser();
        PersonalRecord updatedRecord = personalService.updateRecord(editPersonalRecordDto, user);
        return ResponseEntity.ok(updatedRecord);
    }

    // Удаляем пластинку из каталога юзера
    @GetMapping("/delete/{id}")
    public void deleteRecord(@PathVariable Integer id) {
        var user = userService.getSessionUser();
        personalService.deleteRecord(id, user);
    }

}