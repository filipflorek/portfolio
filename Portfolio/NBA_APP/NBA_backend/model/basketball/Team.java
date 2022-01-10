package com.florek.NBA_backend.model.basketball;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {

    @Id
    protected int id;

    @Column(nullable = false)
    protected String abbreviation;

    @Column(nullable = false)
    protected String city;

    @Column(nullable = false)
    protected String conference;

    @Column(nullable = false)
    protected String division;

    @Column(nullable = false)
    protected String fullName;

    @Column(nullable = false)
    protected String name;

    public Team(@JsonProperty("id") int id,
                @JsonProperty("abbreviation") String abbreviation,
                @JsonProperty("city") String city,
                @JsonProperty("conference") String conference,
                @JsonProperty("division") String division,
                @JsonProperty("full_name") String fullName,
                @JsonProperty("name") String name) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.city = city;
        this.conference = conference;
        this.division = division;
        this.fullName = fullName;
        this.name = name;
    }

    public Team(int id){
        this.id = id;
    }

    public Team(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
