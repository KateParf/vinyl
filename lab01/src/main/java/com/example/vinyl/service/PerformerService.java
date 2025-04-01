package com.example.vinyl.service;

import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;
import com.example.vinyl.repository.PerformerRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PerformerService {
    private final PerformerRepository performerRepository;

    @Autowired
    public PerformerService(PerformerRepository performerRepository) {
        this.performerRepository = performerRepository;
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

    public void clear() {
        performerRepository.deleteAll();
    }
}
