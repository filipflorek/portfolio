package com.florek.NBA_backend.model.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.florek.NBA_backend.model.basketball.Player;

import javax.persistence.*;

@Entity
@Table(name = "favourite_player")
public class FavouritePlayer{

    @Id
    @GeneratedValue
    protected int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_player")
    protected Player player;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    protected Person person;

    public FavouritePlayer(
            @JsonProperty("id") int id,
            @JsonProperty("id_player") Player player,
            @JsonProperty("id_user") Person person) {
        this.id = id;
        this.player = player;
        this.person = person;
    }

    public FavouritePlayer(
            @JsonProperty("id_player") Player player,
            @JsonProperty("id_user") Person person) {
        this.player = player;
        this.person = person;
    }

    public FavouritePlayer() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
