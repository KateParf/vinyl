package com.example.vinyl.model;

import jakarta.persistence.*;


@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer year;
    private String publisher;
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "genre", nullable = false)
    private Genre genre;

    // Getters and Setters
}

