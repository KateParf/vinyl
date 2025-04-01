package com.example.vinyl.service;

import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.ConditionEnum;
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.repository.PersonalRecordRepository;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalRecordService {
    private final PersonalRecordRepository persRecordRepository;
    private final RecordService recordService;
    private final UserService userService;

    @Autowired
    public PersonalRecordService(PersonalRecordRepository recordRepository, RecordService recordService,
            UserService userService) {
        this.persRecordRepository = recordRepository;
        this.recordService = recordService;
        this.userService = userService;
    }

    // Получить все пластинки
    public List<PersonalRecord> getAllRecords() {
        return persRecordRepository.findAll();
    }

    // Получить пластинку по ID
    public PersonalRecord getRecord(Integer id) {
        return persRecordRepository.findById(id).orElse(null);
    }

    // Добавляем пластинку из общего каталога ?????
    // при добавлении коменты и состояние пустые а потом мы их отдельно редактируем
    public PersonalRecord addExistRecord(Integer id, User user) {
        Record existingRecord = recordService.getRecord(id);
        if (existingRecord != null){
            PersonalRecord newRecord = new PersonalRecord();
            newRecord.setRecord(existingRecord);
            newRecord.setComment(null);
            newRecord.setCondition(null);
            newRecord.setUser(user);
            return persRecordRepository.save(newRecord);
        }
        return null;
    }

    // Edit single item
    // ??
    public PersonalRecord updateRecord(PersonalRecord editRecord) {
        return persRecordRepository.findById(editRecord.getId())
                .map(record -> {
                    record.setCondition(editRecord.getCondition());
                    record.setComment(editRecord.getComment());
                    return persRecordRepository.save(record);
                })
                .orElseGet(() -> {
                    return persRecordRepository.save(editRecord);
                });
    }

    // Delete single item
    public void deleteRecord(Integer id) {
        if (persRecordRepository.existsById(id))
            persRecordRepository.deleteById(id);
    }

    public void clear(User user) {
        persRecordRepository.deleteAll();
        //!!! TODO
    }

}
