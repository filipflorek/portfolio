package com.florek.NBA_backend.model.people;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.florek.NBA_backend.model.basketball.Team;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "favourite_team")
public class FavouriteTeam{

    @Id
    @GeneratedValue
    protected int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_team")
    protected Team team;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    protected Person person;

    public FavouriteTeam(
            @JsonProperty("id") int id,
            @JsonProperty("id_team") Team team,
            @JsonProperty("id_user") Person person) {
        this.id = id;
        this.team = team;
        this.person = person;
    }

    public FavouriteTeam(
            @JsonProperty("id_team") Team team,
            @JsonProperty("id_user") Person person) {
        this.team = team;
        this.person = person;
    }

    public FavouriteTeam() {
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
