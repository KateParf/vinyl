package com.example.vinyl.service;

import com.example.vinyl.exceptions.RecordNotFoundException;
import com.example.vinyl.model.Record;
import com.example.vinyl.repository.RecordRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    // Получить все пластинки
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    // Получить пластинку по ID
    public Record getRecord(Integer id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Добавить новую пластинку в общий каталог
    public Record addNewRecord(Record record) {
        return recordRepository.save(record);
    }

    // Edit single item
    public Record updateRecord(Record editRecord) {
        return recordRepository.findById(editRecord.getId())
                .map(record -> {
                    record.setName(editRecord.getName());
                    record.setYear(editRecord.getYear());
                    record.setPublisher(editRecord.getPublisher());
                    record.setBarcode(editRecord.getBarcode());
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
