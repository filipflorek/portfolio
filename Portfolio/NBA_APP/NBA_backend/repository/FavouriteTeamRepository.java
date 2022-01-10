package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.basketball.Player;
import com.florek.NBA_backend.model.people.FavouriteTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavouriteTeamRepository extends JpaRepository<FavouriteTeam, Integer> {

    @Query(value = "SELECT id_team FROM favourite_team WHERE id_user = ?1", nativeQuery = true)
    List<Integer> getFavouriteTeams(int personID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM favourite_team WHERE id_user = ?1 AND id_team = ?2", nativeQuery = true)
    void deleteFavouriteTeam(int personID, int teamID);
}
