package com.example.vinyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "performer")
public class Performer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PerformerEnum sign;

    private String picFilename;

    // Getters and Setters
}
