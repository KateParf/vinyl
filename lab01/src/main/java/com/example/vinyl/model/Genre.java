package com.example.vinyl.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "genre")
public class Genre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Genre: " + this.id + " | " + this.name;
    }

}

