package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.basketball.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {


    @Query(value = "SELECT * FROM Player WHERE id_team = ?1", nativeQuery = true)
    List<Player> getPlayersFromTeam(int teamID);

    @Query(value = "SELECT * FROM Player WHERE position ILIKE ?1", nativeQuery = true)
    List<Player> getPlayersFromPosition(String position);

    @Query(value = "SELECT * FROM Player WHERE last_name ILIKE ?1", nativeQuery = true)
    List<Player> getPlayersByLastName(String lastName);
}
