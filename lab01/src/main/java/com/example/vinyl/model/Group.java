package com.example.vinyl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany( mappedBy = "group" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Performer> performers = new ArrayList<>();
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Performer> getPerformers() {
        return performers;
    }
    

}
