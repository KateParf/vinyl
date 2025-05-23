package com.example.vinyl.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.vinyl.dto.GroupDto;
import com.example.vinyl.dto.PerformerDto;

@Entity
@Table(name = "record")
public class Record implements Serializable {
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
    private final List<Performer> performers = new ArrayList<>();

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
    private final List<Group> groups = new ArrayList<>();

    //@OneToMany( fetch = FetchType.EAGER , cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "record_id")
    @OneToMany( mappedBy = "record" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Cover> covers = new HashSet<>();

    //@OneToMany( fetch = FetchType.EAGER )
    //@JoinColumn(name = "record_id")
    @OneToMany( mappedBy = "record" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Track> tracks = new ArrayList<>();


    //----

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public Integer getYear() {
        return this.year;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public String getBarcode() {
        return this.barcode;
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
  
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<GroupDto> getGroups() {
        List<GroupDto> dtos = new ArrayList<GroupDto>();
        for (int i = 0; i < this.groups.size(); i++) {
            GroupDto dto = new GroupDto(this.groups.get(i).getId(), this.groups.get(i).getName(), this.groups.get(i).getPicture(), this.groups.get(i).getPerformers());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<PerformerDto> getPerformers() {
        List<PerformerDto> dtos = new ArrayList<PerformerDto>();
        for (int i = 0; i < this.performers.size(); i++) {
            PerformerDto dto = new PerformerDto(this.performers.get(i).getId(), this.performers.get(i).getName(), this.performers.get(i).getPicture(), this.performers.get(i).getGroup());
            dtos.add(dto);
        }
        return dtos;
    }

    public Set<Cover> getCovers() {
        return this.covers;
    }

    public List<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(List<Track> tracks){
        this.tracks = tracks;
        tracks.forEach(track -> track.setRecord(this) );
    }

    //---

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addPerformer(Performer performer) {
        this.performers.add(performer);
    }

    public void addCover(String coverUrl) {
        var cover = new Cover(coverUrl);
        this.covers.add(cover);
        cover.setRecord(this);
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
