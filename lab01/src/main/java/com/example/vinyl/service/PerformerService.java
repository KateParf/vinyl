package com.example.vinyl.service;

import com.example.vinyl.exceptions.PerformerNotFoundException;
import com.example.vinyl.model.Performer;
import com.example.vinyl.repository.PerformerRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Performer getById(Integer id) {
        return performerRepository.findById(id)
                .orElseThrow(() -> new PerformerNotFoundException(id));
    }
}
