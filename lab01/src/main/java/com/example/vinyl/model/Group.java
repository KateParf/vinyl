package com.example.vinyl.model;

import java.io.Serializable;

import org.springframework.lang.Nullable;

import jakarta.persistence.*;

@Entity
@Table(name = "groups")
public class Group implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Nullable
    private String picture;
    

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
