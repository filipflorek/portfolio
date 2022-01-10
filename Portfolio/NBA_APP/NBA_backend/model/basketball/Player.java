package com.florek.NBA_backend.model.basketball;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table (name = "Player")
public class Player {

    @Id
    protected int id;

    @Column(nullable = false, name = "first_name")
    protected String firstName;

    @Column(nullable = false, name = "last_name")
    protected String lastName;

    @Column(nullable = false)
    protected String position;

    @Column(name = "height_feet")
    protected int heightFeet;

    @Column(name = "height_inches")
    protected int heightInches;

    @Column(name = "height_pounds")
    protected int weightPounds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_team")
    protected Team team;

    public Player(@JsonProperty("id") int id,
                  @JsonProperty("first_name") String firstName,
                  @JsonProperty("last_name") String lastName,
                  @JsonProperty("position") String position,
                  @JsonProperty("height_feet") int heightFeet,
                  @JsonProperty("height_inches") int heightInches,
                  @JsonProperty("weight_pounds") int weightPounds,
                  @JsonProperty("team") Team team) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.heightFeet = heightFeet;
        this.heightInches = heightInches;
        this.weightPounds = weightPounds;
        this.team = team;
    }



    public Player() {
    }

    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(int heightFeet) {
        this.heightFeet = heightFeet;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(int heightInches) {
        this.heightInches = heightInches;
    }

    public int getWeightPounds() {
        return weightPounds;
    }

    public void setWeightPounds(int weightPounds) {
        this.weightPounds = weightPounds;
    }
}
