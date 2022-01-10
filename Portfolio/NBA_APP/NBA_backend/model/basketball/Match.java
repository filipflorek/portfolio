package com.florek.NBA_backend.model.basketball;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected int id;

    @Column(nullable = false, name = "home_team_score")
    protected int homeTeamScore;

    @Column(nullable = false, name = "visitor_team_score")
    protected int visitorTeamScore;

    @Column(nullable = false)
    protected boolean postseason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_home_team")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    protected Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitor_team")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    protected Team visitorTeam;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date match_date;

    protected int period;

    protected String status;

    protected String time;

    protected int season;

    public Match(@JsonProperty("id") int id,
                 @JsonProperty("home_team_score") int homeTeamScore,
                 @JsonProperty("visitor_team_score") int visitorTeamScore,
                 @JsonProperty("postseason") boolean postseason,
                 @JsonProperty("home_team") Team homeTeam,
                 @JsonProperty("visitor_team") Team visitorTeam,
                 @JsonProperty("date") Date date,
                 @JsonProperty("period") int period,
                 @JsonProperty("status") String status,
                 @JsonProperty("time") String time,
                 @JsonProperty("season") int season){
        this.id = id;
        this.homeTeamScore = homeTeamScore;
        this.visitorTeamScore = visitorTeamScore;
        this.postseason = postseason;
        this.homeTeam = homeTeam;
        this.visitorTeam = visitorTeam;
        this.match_date = date;
        this.period = period;
        this.status = status;
        this.time = time;
        this.season = season;
    }

    public Match(int id) {
        this.id = id;
    }

    public Match() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(int visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }

    public boolean isPostseason() {
        return postseason;
    }

    public void setPostseason(boolean postseason) {
        this.postseason = postseason;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public Date getMatch_date() {
        return match_date;
    }

    public void setMatch_date(Date date) {
        this.match_date = date;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }


}
