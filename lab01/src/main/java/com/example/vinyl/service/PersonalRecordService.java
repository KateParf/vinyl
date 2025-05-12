package com.example.vinyl.service;

import com.example.vinyl.dto.EditPersonalRecordDto;
import com.example.vinyl.dto.PersonalListDto;
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.repository.PersonalRecordRepository;
import com.example.vinyl.repository.RecordRepository;
import com.example.vinyl.repository.UserRepository;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PersonalRecordService {
    private final PersonalRecordRepository persRecordRepository;
    private final RecordService recordService;
    private final RecordRepository recordRepository;


    @Autowired
    public PersonalRecordService(PersonalRecordRepository recordRepository, RecordService recordService,
                                 UserService userService, UserRepository userRepository, RecordRepository recordRepository1) {
        this.persRecordRepository = recordRepository;
        this.recordService = recordService;

        this.recordRepository = recordRepository1;
    }

    // Получить все пластинки
    public List<PersonalRecord> getAllRecords(User user) {
        return persRecordRepository.findAllByUser(user);
    }

    public List<Integer> getRecordIdsInUserCollection(User user) {
        return persRecordRepository.findRecordIdsByUserId(user.getId());
    }

    // Получить пластинку по ID
    public PersonalRecord getRecord(Integer id) {
        return persRecordRepository.findById(id).orElse(null);
    }

    // Добавляем пластинку из общего каталога ?????
    // при добавлении коменты и состояние пустые а потом мы их отдельно редактируем
    public PersonalRecord addExistRecord(Integer MainRecordId, User user) {
        Record existingRecord = recordService.getRecord(MainRecordId);
        if (existingRecord == null) {
            return null;
        }

        PersonalRecord newRecord = new PersonalRecord();
        newRecord.setRecord(existingRecord);
        newRecord.setComment(null);
        newRecord.setCondition(null);
        newRecord.setUser(user);
        return persRecordRepository.save(newRecord);
    }

    // изменяем информацию о пластинке пользователя (комент и состояние)
    public PersonalRecord updateRecord(EditPersonalRecordDto editRecord, User user) {
        Record record = recordRepository.findById(editRecord.getId()).orElseThrow(() -> new RuntimeException("Record not found!"));

        return persRecordRepository.findByUserAndRecord(user, record)
                .map(persRecord -> {
                    persRecord.setCondition(editRecord.getCondition());
                    persRecord.setComment(editRecord.getComment());
                    return persRecordRepository.save(persRecord);
                })
                .orElseGet(() -> {
                    return persRecordRepository.save(new PersonalRecord());
                });
    }

    // удаляем пластинку из коллекции
    public void deleteRecord(Integer id, User user) {
        if (persRecordRepository.existsById(id))
            persRecordRepository.deleteById(id);
    }

    public void clear(User user) {
        persRecordRepository.deleteAll();
        //!!! TODO
    }

}
