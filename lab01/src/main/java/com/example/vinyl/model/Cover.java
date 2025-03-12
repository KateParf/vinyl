package com.example.vinyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cover")
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String picture;

    public Cover(String picture) {
        this.picture = picture;
    }
}
