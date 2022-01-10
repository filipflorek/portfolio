package com.florek.NBA_backend.utils;

import com.florek.NBA_backend.service.MatchService;
import com.florek.NBA_backend.service.PerformanceService;
import com.florek.NBA_backend.service.SeasonPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    MatchService matchService;
    SeasonPlayerService seasonPlayerService;
    PerformanceService performanceService;

    @Autowired
    public ScheduledTask(MatchService matchService, SeasonPlayerService seasonPlayerService, PerformanceService performanceService) {
        this.matchService = matchService;
        this.seasonPlayerService = seasonPlayerService;
        this.performanceService = performanceService;
    }


    @Scheduled(cron="30 29 11 * * *")
    public void updateMatches() throws IOException {
        System.out.println("Rozpoczęto aktualizację meczy: " + formatter.format(new Date()));
        matchService.updateMatches();
        System.out.println("Zakończono aktualizację meczy: " + formatter.format(new Date()));
    }

    @Scheduled(cron="30 34 11 * * *")
    public void updatePerformances() throws IOException {
        System.out.println("Rozpoczęto aktualizację występów: " + formatter.format(new Date()));
        performanceService.updatePerformances();
        System.out.println("Zakończono aktualizację występów: " + formatter.format(new Date()));
    }

    @Scheduled(cron="0 42 16 * * *")
    public void updateSeasonStats() throws IOException {
        System.out.println("Rozpoczęto aktualizację średnich sezonowych: " + formatter.format(new Date()));
        seasonPlayerService.updateSeasonPlayers();
        System.out.println("Zakończono aktualizację średnich sezonowych: " + formatter.format(new Date()));
    }
}
