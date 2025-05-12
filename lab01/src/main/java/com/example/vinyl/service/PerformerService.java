package com.example.vinyl.service;

import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;
import com.example.vinyl.model.Record;
import com.example.vinyl.repository.PerformerRepository;
import com.example.vinyl.repository.RecordRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PerformerService {
    private final PerformerRepository performerRepository;
    private final RecordRepository recordRepository;

    @Autowired
    public PerformerService(PerformerRepository performerRepository, RecordRepository recordRepository) {
        this.performerRepository = performerRepository;
        this.recordRepository = recordRepository;
    }

    // Получить всех исполнителей
    public List<Performer> getAll() {
        return performerRepository.findAll();
    }

    // Получить исполнителя по ID
    // если не найден то вернуть null
    public Performer getById(Integer id) {
        return performerRepository.findById(id)
            .orElse(null);
    }

    // Получить исполнителя по имени
    // если не найден то вернуть null
    public Performer getByName(String name) {
        return performerRepository.findByName(name)
            .orElse(null);
    }

    // добавляет нового исполнителя
    // При вставке дубликата - возвращается DataIntegrityViolationException
    public Performer add(Performer performer) throws DataIntegrityViolationException {
        return performerRepository.save(performer);
    }

    // ищет все пластинки исполнителя
    public List<Record> getRecordsByPerformerId(Integer performerId) {
        return recordRepository.findByPerformerId(performerId);
    }

    public void clear() {
        performerRepository.deleteAll();
    }
}
