package com.florek.NBA_backend.controller;

import com.florek.NBA_backend.model.basketball.Match;
import com.florek.NBA_backend.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RequestMapping("nba/matches")
@RestController
public class MatchController {

    private final MatchService matchService;


    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public void addMatches() throws IOException {
        matchService.addMatches();
    }

    @PostMapping(path = "/update")
    public void updateMatches() throws IOException {
        matchService.updateMatches();
    }

    @GetMapping(path = "/date")
    public List<Match> getMatchesByDate(@RequestParam(name = "date") String date) throws ParseException {
        return  matchService.getMatchesByDate(date);
    }

    @GetMapping(path = "/between")
    public List<Match> getMatchesByDatePeriod(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate) throws ParseException {
        return  matchService.getMatchesByDatePeriod(startDate, endDate);
    }

    @GetMapping(path = "/between/teams")
    public List<Match> getTeamMatchesByDatePeriod(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, @RequestParam(name = "teamIDs") int[] teamIDs) throws ParseException {
        return  matchService.getTeamMatchesByDatePeriod(startDate, endDate, teamIDs);
    }

    @GetMapping(path = "/team")
    public List<Match> getFilteredTeamMatches(@RequestParam(name = "team_id") int teamID,
                                              @RequestParam(name = "season_type") String seasonType,
                                              @RequestParam(name = "match_type") String matchType,
                                              @RequestParam(name = "season") int season,
                                              @RequestParam(name = "number_of_matches") int numberOfMatches){
        return matchService.getTeamMatches(teamID, numberOfMatches, season, seasonType, matchType);
    }
}
