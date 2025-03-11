package com.example.vinyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cover")
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    private byte[] photo;

}
