package com.example.vinyl.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.SearchService;
import com.example.vinyl.dto.GroupDto;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;
import com.example.vinyl.model.RecordBrief;
import com.example.vinyl.model.Record;

@RestController
@RequestMapping("/api/groups")
public class GroupsController {

    private final GroupService service;
    private final SearchService searchService;

    GroupsController(GroupService service, SearchService searchService) {
        this.service = service;        
        this.searchService = searchService;
    }

    // Получение списка групп для которых у нас есть пластинки
    @GetMapping("/list")
    public List<GroupDto> all() {
        List<Group> groups = service.getAll();
        List<GroupDto> dtos = new ArrayList<GroupDto>();
        for (int i = 0; i < groups.size(); i++) {
            GroupDto dto = new GroupDto(groups.get(i).getId(), groups.get(i).getName(), groups.get(i).getPicture(), groups.get(i).getPerformers());
            dtos.add(dto);
        }
        return dtos;
    }

    // Получение конкретной группы
    // если для такого ид нет записи то вернем 404
    @GetMapping("/get/{id}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Integer id) {
        Group group = service.getById(id);
        if (group == null) {
            throw new ResourceNotFoundException("Group", "id", id);
        }
        GroupDto dto = new GroupDto(id, group.getName(), group.getPicture(), group.getPerformers());
        return ResponseEntity.ok(dto);
    }

    // Получаем пластинки конкретной группы
    @GetMapping("/records/{groupId}")
    public ResponseEntity<List<RecordBrief>> getGroupRecords(@PathVariable Integer groupId) {
        List<Record> records = service.getRecordsByGroupId(groupId);
        var res = searchService.RecordsToBriefs(records);
        return ResponseEntity.ok(res);
    }

}