package com.company.Span;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LeagueTests {
    @Test
    public void testReadGamesFile(){
        String filePath = "Games.txt";
        League league = new League();
        List<Match> games = league.readGamesFile(filePath);
        Assert.assertEquals(5, games.size());
    }

    @Test
    public void testProcessData() {
        String firstGameTeam1 = "Lions 3";
        String firstGameTeam2 = "Snakes 3";
        String secondGameTeam1 = "Lions 4";
        String secondGameTeam2 = "Grouches 0";

        List<Match> match = new ArrayList<>();
        League league = new League();
        match.add(league.processData(firstGameTeam1, firstGameTeam2));
        match.add(league.processData(secondGameTeam1, secondGameTeam2));
        Assert.assertEquals(2, match.size());
    }

    @Test
    public void testLeaguePoints(){
        String firstGameTeam1 = "Lions 3";
        String firstGameTeam2 = "Snakes 3";
        String secondGameTeam1 = "Lions 4";
        String secondGameTeam2 = "Grouches 0";
        List<Match> match = new ArrayList<>();
        League league = new League();
        HashMap<String,Integer> game;
        match.add(league.processData(firstGameTeam1, firstGameTeam2));
        match.add(league.processData(secondGameTeam1, secondGameTeam2));
        game = league.leaguePoints(match);

        Assert.assertTrue(game.containsKey("Lions"));
        Assert.assertTrue(game.containsKey("Snakes"));
        Assert.assertTrue(game.containsKey("Grouches"));
        Assert.assertEquals(3, game.size());
        Assert.assertEquals(4, (int) game.get("Lions"));

    }
}
