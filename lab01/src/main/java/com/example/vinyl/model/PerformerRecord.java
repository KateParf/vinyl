package com.example.vinyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "performer_record")
public class PerformerRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @ManyToOne
    @JoinColumn(name = "performer_id", nullable = false)
    private Performer performer;

    // Getters and Setters
}
