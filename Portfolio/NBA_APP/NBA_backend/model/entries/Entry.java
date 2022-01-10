package com.florek.NBA_backend.model.entries;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
public abstract class Entry {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected int id;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateOfPublication;

    public Entry(@JsonProperty("id") int id, @JsonProperty("dateOfPublication") Timestamp dateOfPublication){
        this.id = id;
        this.dateOfPublication = dateOfPublication;
    }

    public Entry(int id){
        this.id= id;
    }
    public Entry(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Timestamp dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }
}
