package com.example.vinyl.controllers;

import java.util.List;

import com.example.vinyl.dto.EditPersonalRecordDto;
import com.example.vinyl.dto.PersonalListDto;
import org.springframework.http.ResponseEntity;
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
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.RecordBrief;
import com.example.vinyl.model.User;

@RestController
@RequestMapping("/userrecords")
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
    public List<PersonalRecord> userAll() {
        return personalService.getAllRecords();
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
    public ResponseEntity<String> existRecord(@PathVariable Integer id) {
        PersonalRecord record = personalService.addExistRecord(id);
        if (record == null) {
            throw new ResourceNotFoundException("Record", "id", id);
        }
        return ResponseEntity.ok("Пластинка успешно добавлена!");
    }

    // из полученных RecordBrief пользователь выбирает одну и мы добавляем ее в бд
    @PostMapping("/addbrief")
    public ResponseEntity<String> addByRecordBrief(@RequestBody RecordBrief recordBrief) {
        Record record = searchService.addFullBriefs(recordBrief);
        Integer id = record.getId();
        return this.existRecord(id);
    }

    // Редактируем персональную информацию о пластинке
    @PostMapping("/edit")
    public ResponseEntity<PersonalRecord> editRecord(@RequestBody EditPersonalRecordDto editPersonalRecordDto) {
        PersonalRecord updatedRecord = personalService.updateRecord(editPersonalRecordDto);
        return ResponseEntity.ok(updatedRecord);
    }

    // Удаляем пластинку из каталога юзера
    @GetMapping("/delete/{id}")
    public void deleteRecord(@PathVariable Integer id) {
        personalService.deleteRecord(id);
    }

}