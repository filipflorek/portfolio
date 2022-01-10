package com.florek.NBA_backend.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.florek.NBA_backend.model.basketball.Team;
import com.florek.NBA_backend.repository.TeamRepository;
import com.florek.NBA_backend.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addTeams() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonTeamArray = prepareTeamArray();
        List<Team> teamList = objectMapper.readValue(jsonTeamArray, new TypeReference<List<Team>>(){});
        teamList.forEach(teamRepository::save);
    }

    public List<Team> getTeamsFromConference(String conference){
        return teamRepository.getTeamByConference(conference);
    }

    public List<Team> getTeamsFromDivision(String division){
        return teamRepository.getTeamByDivision(division);
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamByID(int teamID){
        return teamRepository.findById(teamID);
    }

    private String prepareTeamArray() throws IOException {
        JSONObject teamsData = Utilities.getEndpointResponseJSON(Utilities.TEAMS_ENDPOINT);
        JSONArray teamsArray = teamsData.getJSONArray("data");
        return teamsArray.toString();
    }
}
