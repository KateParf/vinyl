package com.example.vinyl.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "record")
public class Record {
    public Record() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer year;
    
    private String publisher;
    
    private String barcode;

    @OneToOne
    //@JoinColumn(name = "genre", nullable = false)
    private Genre genre;

    // []performers
    @ManyToMany( fetch = FetchType.EAGER /* cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    } */ )
    @JoinTable(
        name = "performer_record", 
        joinColumns = { @JoinColumn(name = "record_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "performer_id") })
    private Set<Performer> performers = new HashSet<>();

    //  [] groups
    @ManyToMany(
      fetch = FetchType.EAGER /*, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    } */
    )
    @JoinTable(
        name = "performer_record", 
        joinColumns = { @JoinColumn(name = "record_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "group_id") })
    private Set<Group> groups = new HashSet<>();

    /*@OneToMany(mappedBy = "record_id")
    Set<Group> groups = new HashSet<>();*/

    @OneToMany(mappedBy="record_id")
    private Set<Cover> covers = new HashSet<>();

    @OneToMany(mappedBy="record_id")
    private Set<Track> tracks = new HashSet<>();


    //----

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getYear() {
        return this.year;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;    
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Set<Group> getGroups() {
        return this.groups;
    }

    public Set<Cover> getCoverss() {
        return this.covers;
    }

    public Set<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(Set<Track> tracks){
        this.tracks = tracks;
    }

    //---

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addCover(String coverUrl) {
        this.covers.add(new Cover(coverUrl));
    }

}
