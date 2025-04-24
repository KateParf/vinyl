package com.example.vinyl.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;


public class RecordBrief {
    public RecordBrief() {}

    private Integer id;

    private String title;

    private Integer year;

    private String genre;

    private String coverUrl;

    // любая информация - ссылка на источник
    private String sourceUID;

    private String barcode;

    //----

    public Integer getId() {
        return this.id;
    }

    public String getSourceUID() {
        return sourceUID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getGenre() {
        return this.genre;
    }

    public Integer getYear() {
        return this.year;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;    
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    
    public void setSourceUID(String sourceUID) {
        this.sourceUID = sourceUID;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
