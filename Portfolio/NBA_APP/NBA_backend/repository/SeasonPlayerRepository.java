package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.basketball.Player;
import com.florek.NBA_backend.model.basketball.SeasonPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeasonPlayerRepository extends JpaRepository<SeasonPlayer, Integer> {

    @Query(value = "SELECT * FROM season_player WHERE id_player = ?1 ORDER BY season DESC LIMIT ?2", nativeQuery = true)
    List<SeasonPlayer> getManySeasonAveragesByID(int playerID, int seasonsAmount);

    @Query(value = "SELECT * FROM season_player WHERE id_player = ?1 AND season = ?2", nativeQuery = true)
    SeasonPlayer getSeasonAveragesByID(int playerID, int season);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO season_player (id_player, season, ast, blk, dreb, oreb, reb, pts, fg3pct, fg3a, fg3m, fgpct, fga, fgm, stl, turnover, fta, ftm, ftpct, pf, min, games_played) " +
            "VALUES (:id_player, :season, :ast, :blk, :dreb, :oreb, :reb, :pts, :fg3pct, :fg3a, :fg3m, :fgpct, :fga, :fgm, :stl, :turnover, :fta, :ftm, :ftpct, :pf, :min, :games_played)" +
            " ON CONFLICT ON CONSTRAINT player_season_unique DO UPDATE SET ast = :ast, blk = :blk, dreb = :dreb, oreb = :oreb, reb = :reb, pts = :pts, fg3pct = :fg3pct, fg3a = :fg3a, fg3m = :fg3m, fgpct = :fgpct, fga = :fga, fgm = :fgm, stl = :stl, turnover = :turnover, fta = :fta, ftm = :ftm, ftpct = :ftpct, pf = :pf, min = :min, games_played = :games_played", nativeQuery = true)
    void addSeasonPlayer(
            @Param("id_player") int id,
            @Param("season") int season,
            @Param("ast") double ast,
            @Param("blk") double blk,
            @Param("dreb") double dreb,
            @Param("oreb") double oreb,
            @Param("reb") double reb,
            @Param("pts") double pts,
            @Param("fg3pct") double fg3pct,
            @Param("fg3a") double fg3a,
            @Param("fg3m") double fg3m,
            @Param("fgpct") double fgpct,
            @Param("fga") double fga,
            @Param("fgm") double fgm,
            @Param("stl") double stl,
            @Param("turnover") double turnover,
            @Param("fta") double fta,
            @Param("ftm") double ftm,
            @Param("ftpct") double ftpct,
            @Param("pf") double pf,
            @Param("min") String min,
            @Param("games_played") int gamesPlayed);
}
