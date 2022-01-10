package com.florek.NBA_backend.controller;


import com.florek.NBA_backend.model.basketball.Team;
import com.florek.NBA_backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.List;

@RequestMapping("nba/teams")
@RestController
public class TeamController {

    private final TeamService teamService;


    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public void addTeams() throws IOException {
        teamService.addTeams();
    }

    @GetMapping
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }
}
