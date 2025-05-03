package com.example.vinyl.dto;

import com.example.vinyl.model.Group;
import lombok.Data;

@Data
public class PerformerDto {
    private Integer id;
    private String name;
    private String picture;
    private Group group;

    public PerformerDto(Integer id, String name, String picture, Group group) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.group = new Group();
        this.group.setId(group.getId());
        this.group.setName(group.getName());
        this.group.setPicture(group.getPicture());
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPicture() {
        return this.picture;
    }

    public Group getGroup() {
        return this.group==null?new Group():this.group;
    }
}
