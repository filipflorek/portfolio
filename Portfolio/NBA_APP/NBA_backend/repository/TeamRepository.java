package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.basketball.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query(value = "SELECT * FROM Team WHERE conference = ?1", nativeQuery = true)
    List<Team> getTeamByConference(String conference);

    @Query(value = "SELECT * FROM Team WHERE division = ?1", nativeQuery = true)
    List<Team> getTeamByDivision(String division);
}
