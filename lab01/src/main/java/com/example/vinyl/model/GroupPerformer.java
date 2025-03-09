package com.example.vinyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "group_performer")
public class GroupPerformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "performer_id", nullable = false)
    private Performer performer;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Performer group;

    // Getters and Setters
}
