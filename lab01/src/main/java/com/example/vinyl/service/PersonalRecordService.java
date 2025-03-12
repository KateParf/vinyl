package com.example.vinyl.service;

import com.example.vinyl.exceptions.RecordNotFoundException;
import com.example.vinyl.model.ConditionEnum;
import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.repository.PersonalRecordRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalRecordService {
    private final PersonalRecordRepository recordRepository;

    @Autowired
    public PersonalRecordService(PersonalRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    // Получить все пластинки
    public List<PersonalRecord> getAllRecords() {
        return recordRepository.findAll();
    }

    // Получить пластинку по ID
    public PersonalRecord getRecord(Integer id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Add existing single item
    //??
    public PersonalRecord addExistRecord(Integer id) {
        PersonalRecord existingRecord = this.getRecord(id);
        return recordRepository.save(existingRecord);
    }

    // Edit single item
    //??
    public PersonalRecord updateRecord(PersonalRecord editRecord) {
        return recordRepository.findById(editRecord.getId())
                .map(record -> {
                    record.setCondition(ConditionEnum.valueOf(editRecord.getCondition()));
                    record.setComment(editRecord.getComment());
                    return recordRepository.save(record);
                })
                .orElseGet(() -> {
                    return recordRepository.save(editRecord);
                });
    }

    // Delete single item
    public void deleteRecord(Integer id) {
        recordRepository.deleteById(id);
    }

}
