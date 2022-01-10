package com.florek.NBA_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.florek.NBA_backend.model.basketball.Player;
import com.florek.NBA_backend.model.basketball.Team;
import com.florek.NBA_backend.repository.PlayerRepository;
import com.florek.NBA_backend.utils.Utilities;
import com.sun.xml.bind.v2.model.core.ID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private int dataPages = 0;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void addPlayers() throws IOException {
        countDataPages();
        ObjectMapper objectMapper = new ObjectMapper();
        for(int pageNumber=1; pageNumber<=dataPages; pageNumber++){
            String jsonPlayerArray = preparePlayerArray(pageNumber);
            List<Player> playerList = objectMapper.readValue(jsonPlayerArray, new TypeReference<>(){});
            playerList.forEach(playerRepository::save);
        }
    }


    public List<Player> getPlayersFromTeam(int teamID){
        return playerRepository.getPlayersFromTeam(teamID);
    }

    public List<Player> getPlayersFromPosition(String position){
        return playerRepository.getPlayersFromPosition(Utilities.prepareSearchString(position));
    }

    public List<Player> getPlayersByLastName(String lastName){
        return playerRepository.getPlayersByLastName(Utilities.prepareSearchString(lastName));
    }

    public Optional<Player> getPlayerByID(int playerID){
        return Optional.empty();
    }

    public void deletePlayer(int playerID){
        if(playerRepository.findById(playerID).isPresent()){
            playerRepository.deleteById(playerID);
        }
    }

    private void countDataPages() throws IOException {
        JSONObject playersData = Utilities.getEndpointResponseJSON(Utilities.PLAYERS_ENDPOINT);
        JSONObject metaData = playersData.getJSONObject("meta");
        dataPages = metaData.getInt("total_pages");
    }


    private String preparePlayerArray(int pageNumber) throws IOException {
        String endpoint = Utilities.prepareEndpointWithPageParameter(Utilities.PLAYERS_ENDPOINT, Integer.toString(pageNumber));
        JSONObject playersData = Utilities.getEndpointResponseJSON(endpoint);
        JSONArray playersArray = playersData.getJSONArray("data");
        return playersArray.toString();
    }
}
