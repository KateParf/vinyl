package com.example.vinyl.model;

import java.io.Serializable;

import org.hibernate.annotations.Cascade;

import jakarta.persistence.*;

@Entity
@Table(name = "cover")
public class Cover implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String picture;

    @ManyToOne()
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name="record_id")
    protected Record record;

    //--

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

    public void setRecord(Record record) {
        this.record = record;
    }

}
