package com.florek.NBA_backend.model.basketball;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.florek.NBA_backend.utils.PerformanceDeserializer;

import javax.persistence.*;

@Entity
@Table(name = "performance")
@JsonDeserialize(using = PerformanceDeserializer.class)
public class Performance {

    @Id
    protected int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_player")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    protected Player player;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_match")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

    protected Match match;


    protected int ast;
    protected int blk;
    protected int dreb;
    protected int oreb;
    protected int reb;
    protected int pts;
    protected double fg3pct;
    protected int fg3a;
    protected int fg3m;
    protected double fgpct;
    protected int fga;
    protected int fgm;
    protected int stl;
    protected int turnover;
    protected int fta;
    protected int ftm;
    protected double ftpct;
    protected int pf;
    protected String min;
    protected int season;

    public Performance(@JsonProperty("id")int id,
                       @JsonProperty("player")Player player,
                       @JsonProperty("game")Match match,
                       @JsonProperty("ast")int ast,
                       @JsonProperty("blk")int blk,
                       @JsonProperty("dreb")int dreb,
                       @JsonProperty("oreb")int oreb,
                       @JsonProperty("reb")int reb,
                       @JsonProperty("pts")int pts,
                       @JsonProperty("fg3_pct")double fg3pct,
                       @JsonProperty("fg3a")int fg3a,
                       @JsonProperty("fg3m")int fg3m,
                       @JsonProperty("fg_pct")double fgpct,
                       @JsonProperty("fga")int fga,
                       @JsonProperty("fgm")int fgm,
                       @JsonProperty("stl")int stl,
                       @JsonProperty("turnover")int turnover,
                       @JsonProperty("fta")int fta,
                       @JsonProperty("ftm")int ftm,
                       @JsonProperty("ft_pct")double ftpct,
                       @JsonProperty("pf")int pf,
                       @JsonProperty("min")String min,
                       @JsonProperty("season") int season) {
        this.id = id;
        this.player = player;
        this.match = match;
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
        this.season = season;
    }

    public Performance() {
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

    public void setPlayer(Player seasonPlayer) {
        this.player = seasonPlayer;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getAst() {
        return ast;
    }

    public void setAst(int ast) {
        this.ast = ast;
    }

    public int getBlk() {
        return blk;
    }

    public void setBlk(int blk) {
        this.blk = blk;
    }

    public int getDreb() {
        return dreb;
    }

    public void setDreb(int dreb) {
        this.dreb = dreb;
    }

    public int getOreb() {
        return oreb;
    }

    public void setOreb(int oreb) {
        this.oreb = oreb;
    }

    public int getReb() {
        return reb;
    }

    public void setReb(int reb) {
        this.reb = reb;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public double getFg3pct() {
        return fg3pct;
    }

    public void setFg3pct(double fg3pct) {
        this.fg3pct = fg3pct;
    }

    public int getFg3a() {
        return fg3a;
    }

    public void setFg3a(int fg3a) {
        this.fg3a = fg3a;
    }

    public int getFg3m() {
        return fg3m;
    }

    public void setFg3m(int fg3m) {
        this.fg3m = fg3m;
    }

    public double getFgpct() {
        return fgpct;
    }

    public void setFgpct(double fgpct) {
        this.fgpct = fgpct;
    }

    public int getFga() {
        return fga;
    }

    public void setFga(int fga) {
        this.fga = fga;
    }

    public int getFgm() {
        return fgm;
    }

    public void setFgm(int fgm) {
        this.fgm = fgm;
    }

    public int getStl() {
        return stl;
    }

    public void setStl(int stl) {
        this.stl = stl;
    }

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

    public int getFta() {
        return fta;
    }

    public void setFta(int fta) {
        this.fta = fta;
    }

    public int getFtm() {
        return ftm;
    }

    public void setFtm(int ftm) {
        this.ftm = ftm;
    }

    public double getFtpct() {
        return ftpct;
    }

    public void setFtpct(double ftpct) {
        this.ftpct = ftpct;
    }

    public int getPf() {
        return pf;
    }

    public void setPf(int pf) {
        this.pf = pf;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
