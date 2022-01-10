package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.basketball.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query(value = "SELECT * FROM Match WHERE CAST(match_date AS date) = ?1 ", nativeQuery = true)
    List<Match> getMatchesByDate(Date matchDate);

    @Query(value = "SELECT * FROM Match WHERE CAST(match_date AS date) >= ?1 AND CAST(match_date AS date) <= ?2 ORDER BY match_date", nativeQuery = true)
    List<Match> getMatchesByDatePeriod(Date startDate, Date endDate);

    @Query(value = "SELECT * FROM Match WHERE (season = ?3 AND id_home_team = ?1) OR (season = ?3 AND id_visitor_team = ?1) ORDER BY match_date DESC LIMIT ?2", nativeQuery = true)
    List<Match> getAllMatches(int teamID, int numberOfMatches, int season);

    @Query(value = "SELECT * FROM Match WHERE (season = ?3 AND postseason = ?4 AND id_home_team = ?1) OR (season = ?3 AND postseason = ?4 AND id_visitor_team = ?1) ORDER BY match_date DESC LIMIT ?2", nativeQuery = true)
    List<Match> getAllPostOrRegularSeasonMatches(int teamID, int numberOfMatches, int season, boolean seasonType);

    @Query(value = "SELECT * FROM Match WHERE (season = ?3 AND postseason = ?4 AND id_home_team = ?1) ORDER BY match_date DESC LIMIT ?2", nativeQuery = true)
    List<Match> getHomeMatches(int teamID, int numberOfMatches, int season, boolean seasonType, String matchType);

    @Query(value = "SELECT * FROM Match WHERE (season = ?3 AND postseason = ?4 AND id_visitor_team = ?1) ORDER BY match_date DESC LIMIT ?2", nativeQuery = true)
    List<Match> getAwayMatches(int teamID, int numberOfMatches, int season, boolean seasonType, String matchType);


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO match (match_date, home_team_score, visitor_team_score, period, status, time, postseason, id_home_team, id_visitor_team, season)" +
            "VALUES (:match_date, :home_team_score, :visitor_team_score, :period, :status, :time, :postseason, :id_home_team, :id_visitor_team, :season)" +
            "ON CONFLICT ON CONSTRAINT date_home_visitor_unique DO UPDATE SET home_team_score = :home_team_score, visitor_team_score = :visitor_team_score, status = :status", nativeQuery = true)
    void updateMatch(@Param("match_date") Date match_date,
                     @Param("home_team_score") int homeTeamScore,
                     @Param("visitor_team_score") int visitorTeamScore,
                     @Param("period") int period,
                     @Param("status") String status,
                     @Param("time") String time,
                     @Param("postseason") boolean postseason,
                     @Param("id_home_team") int homeTeamID,
                     @Param("id_visitor_team") int visitorTeamID,
                     @Param("season") int season);
}

