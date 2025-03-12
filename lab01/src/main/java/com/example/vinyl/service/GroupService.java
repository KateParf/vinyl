package com.example.vinyl.service;

import com.example.vinyl.exceptions.PerformerNotFoundException;
import com.example.vinyl.model.Group;
import com.example.vinyl.repository.GroupRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // Получить все группы
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    // Получить группу по Id
    public Group getById(Integer id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new PerformerNotFoundException(id));
    }

    //!! todo
    public Group getByName(String string) {
        return groupRepository.findAll().getFirst();
    }

    public Group add(Group group) {
        return groupRepository.save(group);
    }

}
