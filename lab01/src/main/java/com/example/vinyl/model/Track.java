package com.example.vinyl.model;

import java.io.Serializable;

import org.hibernate.annotations.Cascade;

import jakarta.persistence.*;

@Entity
@Table(name = "tracks")
public class Track implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToOne()
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name="record_id")
    protected Record record;

    //---

    public Track(String name) {
        this.name = name;
    }

    public Track() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
