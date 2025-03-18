package com.example.vinyl.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "cover")
public class Cover implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String picture;

    
    public Cover(String picture) {
        this.picture = picture;
    }

    public Cover() {
    }

    //--

    public Integer getId() {
        return this.id;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
