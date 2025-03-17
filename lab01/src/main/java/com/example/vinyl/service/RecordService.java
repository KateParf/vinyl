package com.example.vinyl.service;

import com.example.vinyl.exceptions.RecordNotFoundException;
import com.example.vinyl.model.Record;
import com.example.vinyl.repository.RecordRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    // Получить все пластинки
    public List<Record> getFilterRecords(String genre, String performer, String group, Integer decade) {
        // Получаем все пластинки
        List<Record> records = recordRepository.findAll();

        // Применяем фильтры
        if (genre != null) {
            records = records.stream()
                    .filter(record -> genre.equalsIgnoreCase(record.getGenre().getName()))
                    .collect(Collectors.toList());
        }
        if (performer != null) {
            records = records.stream()
                .filter(record -> record.getPerformers().stream()
                        .anyMatch(p -> performer.equalsIgnoreCase(p.getName())))
                .collect(Collectors.toList());
        }
        if (group != null) {
            records = records.stream()
                .filter(record -> record.getGroups().stream()
                        .anyMatch(g -> group.equalsIgnoreCase(g.getName())))
                .collect(Collectors.toList());
        }
        if (decade != null) {
            int startYear = decade; // Например 1960
            int endYear = startYear + 9; // 1960 -> 1969
            records = records.stream()
                    .filter(record -> record.getYear() != null && record.getYear() >= startYear && record.getYear() <= endYear)
                    .collect(Collectors.toList());
        }    

        return records;
    }

    // Получить пластинку по ID
    public Record getRecord(Integer id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Поиск пластинки по штрихкоду
    public Record getByBarcode(String barcode) {
        return recordRepository.findByBarcode(barcode);
    }

    // Поиск пластинки по имени
    public Record searchRecord(String name) {
        return recordRepository.findByName(name);
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

    public void clear() {
        recordRepository.deleteAll();
    }

}
