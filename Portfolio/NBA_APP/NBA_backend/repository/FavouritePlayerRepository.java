package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.people.FavouritePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavouritePlayerRepository extends JpaRepository<FavouritePlayer, Integer> {

    @Query(value = "SELECT id_player FROM favourite_player WHERE id_user = ?1", nativeQuery = true)
    List<Integer> getFavouritePlayers(int personID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM favourite_player WHERE id_user = ?1 AND id_player = ?2", nativeQuery = true)
    void deleteFavouritePlayer(int personID, int playerID);
}
