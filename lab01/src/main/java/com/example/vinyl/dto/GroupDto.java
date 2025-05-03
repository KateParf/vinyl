package com.example.vinyl.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.vinyl.model.Performer;

import lombok.Data;

@Data
public class GroupDto {
    private Integer id;
    private String name;
    private String picture;
    private List<Performer> performers;

    public GroupDto(Integer id, String name, String picture, List<Performer> performers) {
        this.id = id;
        this.name = name;
        this.picture = picture;

        List<Performer> newperformers = new ArrayList<Performer>();
        for (int i = 0; i < performers.size(); i++) {
            Performer performer = new Performer();
            performer.setId(performers.get(i).getId());
            performer.setName(performers.get(i).getName());
            performer.setPicture(performers.get(i).getPicture());
            newperformers.add(performer);
        }
        this.performers = newperformers;
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

    public List<Performer> getPerformers() {
        return this.performers.isEmpty()?new ArrayList<Performer>():this.performers;
    }
}
