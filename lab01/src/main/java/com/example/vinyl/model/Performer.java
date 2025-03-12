package com.example.vinyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "performer")
public class Performer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String picture;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}
