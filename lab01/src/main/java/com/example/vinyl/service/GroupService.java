package com.example.vinyl.service;

import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;
import com.example.vinyl.model.Record;
import com.example.vinyl.repository.GroupRepository;
import com.example.vinyl.repository.RecordRepository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final RecordRepository recordRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, RecordRepository recordRepository) {
        this.groupRepository = groupRepository;
        this.recordRepository = recordRepository;
    }

    // Получить все группы
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    // Получить группу по Id
    // если не найдена по ИД то вернуть null
    public Group getById(Integer id) {
        return groupRepository.findById(id)
                .orElse(null);
    }

    // Получить группу по имени
    // если не найдена по ИД то вернуть null
    public Group getByName(String name) {
        return groupRepository.findByName(name)
                .orElse(null);
    }

    // добавляет новую группу
    // При вставке дубликата - возвращается DataIntegrityViolationException
    public Group add(Group group) throws DataIntegrityViolationException {
        return groupRepository.save(group);
    }

    // ищет все пластинки группы
    public List<Record> getRecordsByGroupId(Integer performerId) {
        return recordRepository.findByGroupId(performerId);
    }

    public void clear() {
        groupRepository.deleteAll();
    }

}
