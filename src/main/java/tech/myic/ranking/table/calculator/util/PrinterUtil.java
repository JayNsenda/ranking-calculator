package tech.myic.ranking.table.calculator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import tech.myic.ranking.table.calculator.detail.TeamDetails;
import tech.myic.ranking.table.calculator.exception.CustomException;

public class PrinterUtil
{
    private static final Map<String, TeamDetails> details = new TreeMap<>();

    private PrinterUtil()
    {
    }

    public static void printRankings(BufferedReader r)
            throws IOException
    {
        String line;

        while ((line = r.readLine()) != null && !line.isEmpty()){
            String[] teamsDetails = line.split(",\\s+");

            String firstTeamDetails;
            try {
                firstTeamDetails = TeamDetailsUtil.getIndividualTeamDetails(teamsDetails, 0);
            }catch (CustomException ex){
                throw new RuntimeException("Unable to get first team details", ex);
            }

            if (firstTeamDetails == null){
                System.err.println("Unable to get first team details");
                return;
            }

            String secondTeamDetails;
            try {
                secondTeamDetails = TeamDetailsUtil.getIndividualTeamDetails(teamsDetails, 1);
            }catch (CustomException ex){
                throw new RuntimeException("Unable to get second team details", ex);
            }

            if (secondTeamDetails == null){
                System.err.println("No second team details");
                return;
            }

            setResultsInMap(firstTeamDetails, secondTeamDetails);
        }

        Map<String, Integer> m = new HashMap<>();

        for (Map.Entry<String, TeamDetails> entry : details.entrySet()){
            TeamDetails t = entry.getValue();
            m.put(t.getTeamName(), t.getPoint());
        }

        LinkedList<Map.Entry<String, Integer>> l = new LinkedList<>(m.entrySet());
        l.sort((Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> {
            int comparison;
            comparison = o2.getValue().compareTo(o1.getValue());
            if (comparison == 0){
                comparison = o1.getKey().compareTo(o2.getKey());
            }
            return comparison;
        });

        int count = 1;
        for (Map.Entry<String, Integer> entry : l){
            System.out.println(count + ". " + entry.getKey() + ", " + entry.getValue() + (entry.getValue() <= 1 ? " pt" : " pts"));
            count += 1;
        }
    }

    private static void setResultsInMap(String firstTeamDetails, String secondTeamDetails)
    {
        String firstTeamName = TeamDetailsUtil.getTeamNameByTeamDetails(firstTeamDetails);
        int firstTeamScore = TeamDetailsUtil.getScoreByTeamDetails(firstTeamDetails);

        String secondTeamName = TeamDetailsUtil.getTeamNameByTeamDetails(secondTeamDetails);
        int secondTeamScore = TeamDetailsUtil.getScoreByTeamDetails(secondTeamDetails);

        Map<Integer, Integer> mapPoint = getPointByScore(firstTeamScore, secondTeamScore);

        if (details.isEmpty()){
            details.put(firstTeamName, new TeamDetails(firstTeamName, firstTeamScore, mapPoint.get(1)));
            details.put(secondTeamName, new TeamDetails(secondTeamName, secondTeamScore, mapPoint.get(2)));
        }else{
            if (details.containsKey(firstTeamName)){
                TeamDetails t = details.get(firstTeamName);
                t.setPoint(t.getPoint() + mapPoint.get(1));
                t.setScore(firstTeamScore);
            }else{
                details.put(firstTeamName, new TeamDetails(firstTeamName, firstTeamScore, mapPoint.get(1)));
            }

            if (details.containsKey(secondTeamName)){
                TeamDetails t = details.get(secondTeamName);
                t.setPoint(t.getPoint() + mapPoint.get(2));
                t.setScore(secondTeamScore);
            }else{
                details.put(secondTeamName, new TeamDetails(secondTeamName, secondTeamScore, mapPoint.get(2)));
            }
        }
    }

    private static Map<Integer, Integer> getPointByScore(int firstTeamScore, int secondTeamScore)
    {
        Map<Integer, Integer> map = new HashMap<>();

        if (firstTeamScore > secondTeamScore){
            map.put(1, 3);
            map.put(2, 0);
        }else if (firstTeamScore == secondTeamScore){
            map.put(1, 1);
            map.put(2, 1);
        }else{
            map.put(1, 0);
            map.put(2, 3);
        }

        return map;
    }

}
