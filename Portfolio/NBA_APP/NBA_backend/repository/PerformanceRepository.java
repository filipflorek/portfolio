package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.basketball.Performance;
import com.florek.NBA_backend.model.basketball.SeasonPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Integer> {

    @Query(value = "SELECT performance.* FROM performance JOIN match ON performance.id_match = match.id WHERE id_player = ?1 AND performance.season = ?3 ORDER BY match.match_date DESC LIMIT ?2", nativeQuery = true)
    List<Performance> getPlayerPerformancesFromSeason(int playerID, int matchesAmount, int season);


}
