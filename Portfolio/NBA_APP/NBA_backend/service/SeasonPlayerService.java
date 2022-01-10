package com.florek.NBA_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.florek.NBA_backend.model.basketball.Player;
import com.florek.NBA_backend.model.basketball.SeasonPlayer;
import com.florek.NBA_backend.repository.SeasonPlayerRepository;
import com.florek.NBA_backend.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeasonPlayerService {

    private final SeasonPlayerRepository seasonPlayerRepository;
    private final int range = 100;
    private final int players = 3400;

    @Autowired
    public SeasonPlayerService(SeasonPlayerRepository seasonPlayerRepository) {
        this.seasonPlayerRepository = seasonPlayerRepository;

    }

    public void addSeasonPlayers() throws IOException {

        int seasons[] = new int[20];
        for (int i=0; i<21; i++){
            seasons[i] = i+2000;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        for (int season : seasons) {
            for (int i=0; i<players; i+=range){
                String jsonPlayerArray = prepareSeasonPlayerArray(season, new int[]{i+1, i + range});
                List<SeasonPlayer> playerList = objectMapper.readValue(jsonPlayerArray, new TypeReference<List<SeasonPlayer>>(){});
                playerList.forEach(seasonPlayerRepository::save);
            }
        }
    }

    public void updateSeasonPlayers() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        for (int i=0; i<players; i+=range){
            String jsonPlayerArray = prepareSeasonPlayerArray(2020, new int[]{i+1, i + range});
            List<SeasonPlayer> playerList = objectMapper.readValue(jsonPlayerArray, new TypeReference<List<SeasonPlayer>>(){});
            for ( SeasonPlayer seasonPlayer : playerList) {
                seasonPlayerRepository.addSeasonPlayer(
                        seasonPlayer.getPlayer().getId(),
                        seasonPlayer.getSeason(),
                        seasonPlayer.getAst(),
                        seasonPlayer.getBlk(),
                        seasonPlayer.getDreb(),
                        seasonPlayer.getOreb(),
                        seasonPlayer.getReb(),
                        seasonPlayer.getPts(),
                        seasonPlayer.getFg3pct(),
                        seasonPlayer.getFg3a(),
                        seasonPlayer.getFg3m(),
                        seasonPlayer.getFgpct(),
                        seasonPlayer.getFga(),
                        seasonPlayer.getFgm(),
                        seasonPlayer.getStl(),
                        seasonPlayer.getTurnover(),
                        seasonPlayer.getFta(),
                        seasonPlayer.getFtm(),
                        seasonPlayer.getFtpct(),
                        seasonPlayer.getPf(),
                        seasonPlayer.getMin(),
                        seasonPlayer.getGamesPlayed()
                );
            }
        }
    }

    public List<SeasonPlayer> getManySeasonsAveragesByID(int playerID, int seasonsAmount){
        return seasonPlayerRepository.getManySeasonAveragesByID(playerID, seasonsAmount);
    }

    public SeasonPlayer getSeasonAveragesByID(int playerID, int season){
        return seasonPlayerRepository.getSeasonAveragesByID(playerID, season);
    }

    public List<List<SeasonPlayer>> comparePlayers(int[] playerIDs, int seasonsAmount){
        ArrayList<List<SeasonPlayer>> comparedPlayers = new ArrayList<>();
        for (int i=0; i< playerIDs.length; i++){
            comparedPlayers.add(seasonPlayerRepository.getManySeasonAveragesByID(playerIDs[i], seasonsAmount));
        }
        return comparedPlayers;
    }




    private String prepareSeasonPlayerArray(int season, int[] range) throws IOException {
        String endpoint = Utilities.prepareSeasonPlayerEndpoint(Utilities.SEASON_STATS_ENDPOINT, season, range);
        JSONObject playersData = Utilities.getEndpointResponseJSON(endpoint);
        JSONArray playersArray = playersData.getJSONArray("data");
        return playersArray.toString();
    }
}
