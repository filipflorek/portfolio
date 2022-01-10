package com.florek.NBA_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.florek.NBA_backend.model.basketball.Match;
import com.florek.NBA_backend.model.basketball.Performance;
import com.florek.NBA_backend.model.basketball.SeasonPlayer;
import com.florek.NBA_backend.repository.PerformanceRepository;
import com.florek.NBA_backend.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private int dataPages = 0;

    @Autowired
    public PerformanceService(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }


    public void addPerformances() throws IOException {
        countDataPages();
        ObjectMapper objectMapper = new ObjectMapper();
        for(int pageNumber=0; pageNumber<=dataPages; pageNumber++){
            String jsonPerformanceArray = prepareMatchArray(pageNumber);
            List<Performance> performanceList = objectMapper.readValue(jsonPerformanceArray, new TypeReference<List<Performance>>() {
            });
            performanceList.forEach(performanceRepository::save);
        }
        Utilities.writeToPropertiesFile("performancePages", dataPages);
    }

    public void updatePerformances() throws IOException {
        countDataPages();
        ObjectMapper objectMapper = new ObjectMapper();
        Properties props = Utilities.loadPropertiesFile();
        for(int pageNumber=Integer.parseInt(props.getProperty("performancePages")); pageNumber<=dataPages; pageNumber++){
            String jsonPerformanceArray = prepareMatchArray(pageNumber);
            List<Performance> performanceList = objectMapper.readValue(jsonPerformanceArray, new TypeReference<>() {
            });
            performanceList.forEach(performanceRepository::save);
        }
        Utilities.writeToPropertiesFile("performancePages", dataPages);
    }

    public List<Performance> getPlayerPerformancesFromSeason(int playerID, int matchesAmount, int season){
        return performanceRepository.getPlayerPerformancesFromSeason(playerID, matchesAmount, season);
    }


    public List<List<Performance>> comparePlayers(int[] playerIDs, int matchesAmount, int season){
        ArrayList<List<Performance>> comparedPlayers = new ArrayList<>();
        for (int i=0; i< playerIDs.length; i++){
            comparedPlayers.add(performanceRepository.getPlayerPerformancesFromSeason(playerIDs[i], matchesAmount, season));
        }
        return comparedPlayers;
    }

    private void countDataPages() throws IOException {
        JSONObject matchesData = Utilities.getEndpointResponseJSON(Utilities.SINGLE_GAME_STATS_ENDPOINT);
        JSONObject metaData = matchesData.getJSONObject("meta");
        dataPages = metaData.getInt("total_pages");
    }


    private String prepareMatchArray(int pageNumber) throws IOException {
        String endpoint = Utilities.prepareEndpointWithPageParameter(Utilities.SINGLE_GAME_STATS_ENDPOINT, Integer.toString(pageNumber));
        JSONObject gamesData = Utilities.getEndpointResponseJSON(endpoint);
        JSONArray gamesArray = gamesData.getJSONArray("data");
        for (int i = 0; i < gamesArray.length(); i++) {
            if(gamesArray.getJSONObject(i).get("min") == null){
                gamesArray.getJSONObject(i).remove("min");
                gamesArray.getJSONObject(i).put("min", "0:00");
            }
        }
        return gamesArray.toString();
    }
}
