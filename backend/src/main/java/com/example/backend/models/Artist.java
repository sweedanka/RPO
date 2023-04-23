package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "artists")
@Access(AccessType.FIELD)
public class Artist {

    public Artist() { }
    public Artist(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @Column(name = "century", nullable = false)
    public String century;

    @ManyToOne()
    @JoinColumn(name = "countryid")
    public Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "artist")
    public List<Artist> artists = new ArrayList<Artist>();
}