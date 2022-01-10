package com.florek.NBA_backend.model.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.florek.NBA_backend.utils.Utilities;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected int id;

    @Column(nullable = false)
    protected String login;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false)
    protected String email;

    @Column(nullable = false)
    protected boolean loggedIn;

    @Column(nullable = false)
    protected String role;

    @Column(name = "account_points")
    protected int accountPoints;

    @Column(name = "able_to_comment")
    protected boolean ableToComment;

    public Person(@JsonProperty("id") int id,
                  @JsonProperty("login") String login,
                  @JsonProperty("password") String password,
                  @JsonProperty("email") String email,
                  @JsonProperty("role") String role){
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        accountPoints = 0;
        ableToComment = true;
        loggedIn = false;
    }

    public Person() {

    }

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAccountPoints() {
        return accountPoints;
    }

    public void setAccountPoints(int accountPoints) {
        this.accountPoints = accountPoints;
    }

    public boolean isAbleToComment() {
        return ableToComment;
    }

    public void setAbleToComment(boolean ableToComment) {
        this.ableToComment = ableToComment;
    }

    public boolean isPublicist(){
        return role.equals(Utilities.PUBLICIST);
    }

    public boolean isAdmin(){
        return role == Utilities.ADMINISTRATOR;
    }
}
