package com.florek.NBA_backend.model.basketball;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "season_player")
public class SeasonPlayer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_player")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    protected Player player;

    protected int season;
    protected double ast;
    protected double blk;
    protected double dreb;
    protected double oreb;
    protected double reb;
    protected double pts;
    protected double fg3pct;
    protected double fg3a;
    protected double fg3m;
    protected double fgpct;
    protected double fga;
    protected double fgm;
    protected double stl;
    protected double turnover;
    protected double fta;
    protected double ftm;
    protected double ftpct;
    protected double pf;
    protected String min;

    @Column(name = "games_played")
    protected int gamesPlayed;

    public SeasonPlayer(@JsonProperty("id") int id,
                        @JsonProperty("player_id")Player player,
                        @JsonProperty("season")int season,
                        @JsonProperty("ast")double ast,
                        @JsonProperty("blk")double blk,
                        @JsonProperty("dreb")double dreb,
                        @JsonProperty("oreb")double oreb,
                        @JsonProperty("reb")double reb,
                        @JsonProperty("pts")double pts,
                        @JsonProperty("fg3_pct")double fg3pct,
                        @JsonProperty("fg3a")double fg3a,
                        @JsonProperty("fg3m")double fg3m,
                        @JsonProperty("fg_pct")double fgpct,
                        @JsonProperty("fga")double fga,
                        @JsonProperty("fgm")double fgm,
                        @JsonProperty("stl")double stl,
                        @JsonProperty("turnover")double turnover,
                        @JsonProperty("fta")double fta,
                        @JsonProperty("ftm")double ftm,
                        @JsonProperty("ft_pct")double ftpct,
                        @JsonProperty("pf")double pf,
                        @JsonProperty("min")String min,
                        @JsonProperty("games_played") int gamesPlayed) {
        this.id = id;
        this.player = player;
        this.season = season;
        this.ast = ast;
        this.blk = blk;
        this.dreb = dreb;
        this.oreb = oreb;
        this.reb = reb;
        this.pts = pts;
        this.fg3pct = fg3pct;
        this.fg3a = fg3a;
        this.fg3m = fg3m;
        this.fgpct = fgpct;
        this.fga = fga;
        this.fgm = fgm;
        this.stl = stl;
        this.turnover = turnover;
        this.fta = fta;
        this.ftm = ftm;
        this.ftpct = ftpct;
        this.pf = pf;
        this.min = min;
        this.gamesPlayed = gamesPlayed;
    }

    public SeasonPlayer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public double getAst() {
        return ast;
    }

    public void setAst(double ast) {
        this.ast = ast;
    }

    public double getBlk() {
        return blk;
    }

    public void setBlk(double blk) {
        this.blk = blk;
    }

    public double getDreb() {
        return dreb;
    }

    public void setDreb(double dreb) {
        this.dreb = dreb;
    }

    public double getOreb() {
        return oreb;
    }

    public void setOreb(double oreb) {
        this.oreb = oreb;
    }

    public double getReb() {
        return reb;
    }

    public void setReb(double reb) {
        this.reb = reb;
    }

    public double getPts() {
        return pts;
    }

    public void setPts(double pts) {
        this.pts = pts;
    }

    public double getFg3pct() {
        return fg3pct;
    }

    public void setFg3pct(double fg3pct) {
        this.fg3pct = fg3pct;
    }

    public double getFg3a() {
        return fg3a;
    }

    public void setFg3a(double fg3a) {
        this.fg3a = fg3a;
    }

    public double getFg3m() {
        return fg3m;
    }

    public void setFg3m(double fg3m) {
        this.fg3m = fg3m;
    }

    public double getFgpct() {
        return fgpct;
    }

    public void setFgpct(double fgpct) {
        this.fgpct = fgpct;
    }

    public double getFga() {
        return fga;
    }

    public void setFga(double fga) {
        this.fga = fga;
    }

    public double getFgm() {
        return fgm;
    }

    public void setFgm(double fgm) {
        this.fgm = fgm;
    }

    public double getStl() {
        return stl;
    }

    public void setStl(double stl) {
        this.stl = stl;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public double getFta() {
        return fta;
    }

    public void setFta(double fta) {
        this.fta = fta;
    }

    public double getFtm() {
        return ftm;
    }

    public void setFtm(double ftm) {
        this.ftm = ftm;
    }

    public double getFtpct() {
        return ftpct;
    }

    public void setFtpct(double ftpct) {
        this.ftpct = ftpct;
    }

    public double getPf() {
        return pf;
    }

    public void setPf(double pf) {
        this.pf = pf;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
