package com.florek.NBA_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.florek.NBA_backend.model.basketball.Match;
import com.florek.NBA_backend.repository.MatchRepository;
import com.florek.NBA_backend.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private int dataPages = 0;
    private int currentSeasonDataPages = 23;
    private final int datePeriod = 7;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void addMatches() throws IOException {
        countDataPages();
        ObjectMapper objectMapper = new ObjectMapper();
        for(int pageNumber=1; pageNumber<=dataPages; pageNumber++){
            String jsonMatchArray = prepareMatchArray(pageNumber, false);
            List<Match> matchList = objectMapper.readValue(jsonMatchArray, new TypeReference<>(){});
            matchList.forEach(matchRepository::save);
        }
        Utilities.writeToPropertiesFile("matchPages", dataPages);
    }

    public void updateMatches() throws IOException {
        countDataPages();
        ObjectMapper objectMapper = new ObjectMapper();
        Properties props = Utilities.loadPropertiesFile();
        for(int pageNumber=1; pageNumber<=currentSeasonDataPages; pageNumber++){
            String jsonMatchArray = prepareMatchArray(pageNumber, true);
            List<Match> matchList = objectMapper.readValue(jsonMatchArray, new TypeReference<List<Match>>(){});
            for (Match match : matchList) {
                matchRepository.updateMatch(
                        match.getMatch_date(),
                        match.getHomeTeamScore(),
                        match.getVisitorTeamScore(),
                        match.getPeriod(),
                        match.getStatus(),
                        match.getTime(),
                        match.isPostseason(),
                        match.getHomeTeam().getId(),
                        match.getVisitorTeam().getId(),
                        match.getSeason()
                );
            }
        }
        Utilities.writeToPropertiesFile("matchPages", dataPages);
    }

    public List<Match> getMatchesByDate(String date) throws ParseException {
        return matchRepository.getMatchesByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
    }

    public List<Match> getMatchesByDatePeriod(String startDate, String endDate) throws ParseException {
        return matchRepository.getMatchesByDatePeriod(new SimpleDateFormat("yyyy-MM-dd").parse(startDate), new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
    }

    public List<Match> getTeamMatchesByDatePeriod(String startDate, String endDate, int[] teamIDs) throws ParseException {
        if(teamIDs.length == 0){
            return matchRepository.getMatchesByDatePeriod(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
                    new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        }else{
            return matchRepository.getMatchesByDatePeriod(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
                    new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
                    .stream().filter(match -> {
                        return checkIfTeamPlayed(match.getHomeTeam().getId(), teamIDs) || checkIfTeamPlayed(match.getVisitorTeam().getId(), teamIDs);
                    }).collect(Collectors.toList());
        }

    }

    private boolean checkIfTeamPlayed(int teamId, int[] possibleIds){
        int counter = 0;
        for (int possibleId : possibleIds) {
            if (possibleId == teamId) {
                counter++;
            }
        }
        return counter > 0;
    }

    public List<Match> getTeamMatches(int teamID, int numberOfMatches, int season, String seasonType, String matchType){
        if(seasonType.equals("all") && matchType.equals("all")){
            return  getAllMatches(teamID, numberOfMatches, season);
        }
        if(seasonType.equals("post") && matchType.equals("all")){
            return  getAllPostORRegularSeasonMatches(teamID, numberOfMatches, season, true);
        }
        if(seasonType.equals("regular") && matchType.equals("all")){
            return  getAllPostORRegularSeasonMatches(teamID, numberOfMatches, season, false);
        }
        if(seasonType.equals("post") && matchType.equals("away")){
            return getAwayMatches(teamID, numberOfMatches, season, true, matchType);
        }
        if(seasonType.equals("post") && matchType.equals("home")){
            return getHomeMatches(teamID, numberOfMatches, season, true, matchType);
        }
        if(seasonType.equals("regular") && matchType.equals("away")){
            return getAwayMatches(teamID, numberOfMatches, season, false, matchType);
        }
        if(seasonType.equals("regular") && matchType.equals("home")){
            return getHomeMatches(teamID, numberOfMatches, season, false, matchType);
        }
        return null;
    }

    public List<Match> getAllMatches(int teamID, int numberOfMatches, int season){
        return matchRepository.getAllMatches(teamID, numberOfMatches, season);
    }

    public List<Match> getAllPostORRegularSeasonMatches(int teamID, int numberOfMatches, int season, boolean seasonType){
        return matchRepository.getAllPostOrRegularSeasonMatches(teamID, numberOfMatches, season, seasonType);
    }

    public List<Match> getHomeMatches(int teamID, int numberOfMatches, int season, boolean seasonType, String matchType){
        return matchRepository.getHomeMatches(teamID, numberOfMatches, season, seasonType, matchType);
    }

    public List<Match> getAwayMatches(int teamID, int numberOfMatches, int season, boolean seasonType, String matchType){
        return matchRepository.getAwayMatches(teamID, numberOfMatches, season, seasonType, matchType);
    }




    private void countDataPages() throws IOException {
        JSONObject matchesData = Utilities.getEndpointResponseJSON(Utilities.GAMES_ENDPOINT);
        JSONObject metaData = matchesData.getJSONObject("meta");
        dataPages = metaData.getInt("total_pages");
    }


    private String prepareMatchArray(int pageNumber, boolean update) throws IOException {
        String endpoint;
        JSONObject gamesData;
        JSONArray gamesArray;
        if(update){
            endpoint = Utilities.prepareEndpointWithSeasonParameter(Utilities.GAMES_ENDPOINT, Integer.toString(2020), Integer.toString(pageNumber));
        }else{
            endpoint = Utilities.prepareEndpointWithPageParameter(Utilities.GAMES_ENDPOINT, Integer.toString(pageNumber));

        }
        gamesData = Utilities.getEndpointResponseJSON(endpoint);
        gamesArray = gamesData.getJSONArray("data");
        return gamesArray.toString();
    }
}
