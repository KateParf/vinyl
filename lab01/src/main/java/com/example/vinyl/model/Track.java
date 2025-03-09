package com.example.vinyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tracks")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    private String name;

}
