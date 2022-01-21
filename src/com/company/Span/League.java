package com.company.Span;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import static java.util.stream.Collectors.*;

import static com.company.Span.Rules.*;

public class League {
    private static League league;

    public static void main(String[] args) {
        Scanner readinput = new Scanner(System.in);
        System.out.println("Enter FileName");
        String fileName = readinput.nextLine();

        league = new League();
        HashMap<String, Integer> teamsSorted = league.processFile(fileName);

        int i = 1;
        for(String t : teamsSorted.keySet())
        {
            System.out.println(i +". "+t +", "+ teamsSorted.get(t) + " pts");
            i++;
        }
    }

    private HashMap<String, Integer> processFile(String fileName) {
        List<Match> matches = league.readGamesFile(fileName);
        HashMap<String, Integer> teams = league.leaguePoints(matches);
        HashMap<String, Integer> teamsSorted = teams.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));
        return teamsSorted;
    }

    public  HashMap<String,Integer> leaguePoints(List<Match> matches) {
        HashMap<String,Integer> game = new HashMap<>();
        Team firstTeam;
        Team secondTeam;

        for(Match m : matches){

            if(m.firstTeamScore > m.secondTeamScore){
                if(game.containsKey(m.firstTeamName) ){
                    processPoints(game,m.firstTeamName,applyRules( win));
                }else {
                    firstTeam = new Team(m.firstTeamName, applyRules( win));
                    game.put(firstTeam.teamName,firstTeam.teamPoints);
                }
                if(game.containsKey(m.secondTeamName)){
                    processPoints(game,m.secondTeamName,applyRules( loose));
                }
                else{
                    secondTeam = new Team(m.secondTeamName,applyRules( loose));
                    game.put(secondTeam.teamName, secondTeam.teamPoints);
                }
            }else
            if(m.firstTeamScore < m.secondTeamScore){
                if(game.containsKey(m.firstTeamName) ){
                    processPoints(game,m.firstTeamName,applyRules( loose));
                }else{
                    firstTeam = new Team(m.firstTeamName,applyRules( loose));
                    game.put(firstTeam.teamName,firstTeam.teamPoints);
                }
                if(game.containsKey(m.secondTeamName)){
                    processPoints(game,m.secondTeamName,applyRules( win));
                }
                else{
                    secondTeam = new Team(m.secondTeamName, applyRules( win));
                    game.put(secondTeam.teamName, secondTeam.teamPoints);
                }

            }else
            if(m.firstTeamScore == m.secondTeamScore){
                if(game.containsKey(m.firstTeamName) ){
                    processPoints(game,m.firstTeamName,applyRules( draw));
                }else{
                    firstTeam = new Team(m.firstTeamName,applyRules(draw));
                    game.put(firstTeam.teamName,firstTeam.teamPoints);
                }
                if(game.containsKey(m.secondTeamName)){
                    processPoints(game,m.secondTeamName,applyRules( draw));
                }
                else{
                    secondTeam = new Team(m.secondTeamName, applyRules(draw));
                    game.put(secondTeam.teamName, secondTeam.teamPoints);
                }
            }
        }
        return game;
    }

   private void processPoints( HashMap<String,Integer> game, String name,int point){
       if(game.containsKey(name)){
           int value = game.get(name);
           value += point;
           game.put(name,value);
       }
   }

    private int applyRules( Rules r){
        int point = 0;
        switch(r) {
            case win:
                point = 3;
                break;
            case draw:
                point = 1;
            break;
            case loose:
                point = 0;
                break;
            default:
        }
        return point;
    }

    public List<Match> readGamesFile(String filePath){

        List<Match> match = new ArrayList<>();
        try {
            File file = new File(filePath);
            if(!file.isFile()){
                System.out.println("Filename not found");
            }else {
                System.out.println("File  found");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();

                    String[] splitTeams = data.split(", ");
                    if(splitTeams.length == 2){
                        match.add(processData(splitTeams[0],splitTeams[1]));
                    }else {
                        System.out.println("File only needs to have 2 teams per line");
                        match = null;
                        break;
                    }
                }
                scanner.close();
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
        return match;
    }

    public Match processData(String firstTeam, String secondTeam){
        try{
            char index = ' ';
            String firstTeamScore = firstTeam.substring(firstTeam.lastIndexOf(index));
            String firstTamName = firstTeam.substring(0,firstTeam.lastIndexOf(index));

            String secondTeamScore = secondTeam.substring(secondTeam.lastIndexOf(index));
            String secondTeamName = secondTeam.substring(0,secondTeam.lastIndexOf(index));

            return new Match(firstTamName, Integer.parseInt(firstTeamScore.trim()),
                    secondTeamName, Integer.parseInt(secondTeamScore.trim()));
        }catch (NumberFormatException e){
            System.out.println("Team name and score should be separated by space," +
                    "\n Teams should be separated by commas. Only 2 teams per line" +
                    "\n teamAce 1, teamJoker 1");

        }catch (Exception e){
            System.out.println("File is not in order"+e.getMessage());

        }
        return null;
    }
}
