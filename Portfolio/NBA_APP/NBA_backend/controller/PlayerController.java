package com.florek.NBA_backend.controller;

import com.florek.NBA_backend.model.basketball.Performance;
import com.florek.NBA_backend.model.basketball.Player;
import com.florek.NBA_backend.model.basketball.SeasonPlayer;
import com.florek.NBA_backend.service.PerformanceService;
import com.florek.NBA_backend.service.PlayerService;
import com.florek.NBA_backend.service.SeasonPlayerService;
import com.florek.NBA_backend.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("nba/players")
@RestController
public class PlayerController {

    private static final String NO_PLAYERS_FOUND_MESSAGE = "Brak zawodników spełniajacych kryteria wyszukiwania";
    private static final String PLAYERS_FOUND_MESSAGE = "Zawodnicy spełniajacy kryteria wyszukiwania";
    private final PlayerService playerService;
    private final SeasonPlayerService seasonPlayerService;
    private final PerformanceService performanceService;

    @Autowired
    public PlayerController(PlayerService playerService,
                            SeasonPlayerService seasonPlayerService,
                            PerformanceService performanceService) {
        this.playerService = playerService;
        this.seasonPlayerService = seasonPlayerService;
        this.performanceService = performanceService;
    }

    @GetMapping(path = "{id}")
    public RestResponse<Player> getPlayerByID(@PathVariable(name = "id") int playerID){
        Optional<Player> player = playerService.getPlayerByID(playerID);
        return player.map(value -> new RestResponse<>(HttpStatus.OK, PLAYERS_FOUND_MESSAGE, value))
                .orElseGet(() -> new RestResponse<>(HttpStatus.NOT_FOUND, NO_PLAYERS_FOUND_MESSAGE));
    }

    @GetMapping(path = {"/name/{last_name}"})
    public RestResponse<List<Player>> getPlayersByLastName(@PathVariable(name = "last_name") String last_name){
        List<Player> players = playerService.getPlayersByLastName(last_name);
        if(players.size() == 0){
            return new RestResponse<>(HttpStatus.NO_CONTENT, NO_PLAYERS_FOUND_MESSAGE, players);
        }else{
            return new RestResponse<>(HttpStatus.OK, PLAYERS_FOUND_MESSAGE, players);
        }
    }

    @PostMapping
    public void addPlayers() throws IOException {
        playerService.addPlayers();
    }

    @GetMapping(path = "/position/{position}")
    public List<Player> getPlayersFromPosition(@PathVariable(name = "position") String position){
        return playerService.getPlayersFromPosition(position);
    }

    @GetMapping(path = {"/team/{id}"})
    public List<Player> getPlayersFromTeam(@PathVariable(name = "id") int teamID){
        return playerService.getPlayersFromTeam(teamID);
    }



    @DeleteMapping(path = "{id}")
    public void deletePlayer(@PathVariable(name = "id") int playerID){
        playerService.deletePlayer(playerID);
    }

    @PostMapping(path = "/season-stats")
    public void addSeasonPlayers() throws IOException {
        seasonPlayerService.addSeasonPlayers();
    }

    @PostMapping(path = "/season-stats/update")
    public void updateSeasonPlayers() throws IOException {
        seasonPlayerService.updateSeasonPlayers();
    }

    @GetMapping(path = "/season-stats/single")
    public List<SeasonPlayer> getSinglePlayerSeasonAverages(@RequestParam(name = "playerID") int playerID, @RequestParam(name = "seasons")int seasonsAmount){
        return seasonPlayerService.getManySeasonsAveragesByID(playerID, seasonsAmount);
    }

    @GetMapping(path = "/season-stats/compare")
    public List<List<SeasonPlayer>> comparePlayersSeasonAverages(@RequestParam(name = "playerIDs")int[] playerIDs, @RequestParam(name = "seasons")int seasonsAmount){
        return seasonPlayerService.comparePlayers(playerIDs, seasonsAmount);
    }

    @PostMapping(path = "/games-stats")
    public void addPerformances() throws IOException {
        performanceService.addPerformances();
    }

    @GetMapping(path = "/games-stats/single")
    public List<Performance> getPlayerPerformances(@RequestParam(name = "playerID") int playerID, @RequestParam(name = "matches")int matches, @RequestParam(name = "season")int season) {
        return performanceService.getPlayerPerformancesFromSeason(playerID, matches, season);
    }

    @GetMapping(path = "/games-stats/compare")
    public List<List<Performance>> comparePlayersPerformances(@RequestParam(name = "playerIDs")int[] playerIDs, @RequestParam(name = "matches")int matches, @RequestParam(name = "season") int season) {
        return performanceService.comparePlayers(playerIDs, matches, season);
    }

}
