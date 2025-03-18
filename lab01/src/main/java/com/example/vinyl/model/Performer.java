package com.example.vinyl.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "performer")
public class Performer implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String picture;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

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
        return this.group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
