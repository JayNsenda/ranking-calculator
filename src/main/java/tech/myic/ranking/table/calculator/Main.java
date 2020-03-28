package tech.myic.ranking.table.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jules
 */
public class Main {

    private static final Map<String, TeamDetails> details = new TreeMap<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String line;

            while ((line = r.readLine()) != null && !line.isEmpty()) {
                String[] teamsDetails = line.split(",\\s+");

                String firstTeamDetails = null;
                try {
                    firstTeamDetails = getIndividualTeamDetails(teamsDetails, 0);
                } catch (RankingCalculatorException ex) {
                    throw new RuntimeException("Unable to get first team details", ex);
                }

                if (firstTeamDetails == null) {
                    System.err.println("Unable to get first team details");
                    return;
                }

                String secondTeamDetails = null;
                try {
                    secondTeamDetails = getIndividualTeamDetails(teamsDetails, 1);
                } catch (RankingCalculatorException ex) {
                    throw new RuntimeException("Unable to get second team details", ex);
                }

                if (secondTeamDetails == null) {
                    System.err.println("No second team details");
                    return;
                }

                setResultsInMap(firstTeamDetails, secondTeamDetails);

            }

            Map<String, Integer> m = new HashMap<>();

            for (Map.Entry<String, TeamDetails> entry : details.entrySet()) {
                TeamDetails t = entry.getValue();
//                int point = t.getPoint();
                m.put(t.getTeamName(), t.getPoint());
//                System.out.println(t.getTeamName() + ", " + point + (point <= 1 ? " pt" : " pts"));
            }

            LinkedList<Map.Entry<String, Integer>> l = new LinkedList<>(m.entrySet());
            Collections.sort(l, (Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> {
                int comparison = 0;
                comparison = o2.getValue().compareTo(o1.getValue());
                if (comparison == 0) {
                    comparison = o1.getKey().compareTo(o2.getKey());
                }
                return comparison;
            });

            for (Map.Entry<String, Integer> entry : l) {
                System.out.println(entry.getKey() + ", " + entry.getValue() + (entry.getValue() <= 1 ? " pt" : " pts"));
            }
        }
    }

    private static void setResultsInMap(String firstTeamDetails, String secondTeamDetails) {

        String firstTeamName = getTeamNameByTeamDetails(firstTeamDetails);
        int firstTeamScore = getScoreByTeamDetails(firstTeamDetails);

        String secondTeamName = getTeamNameByTeamDetails(secondTeamDetails);
        int secondTeamScore = getScoreByTeamDetails(secondTeamDetails);

        Map<Integer, Integer> mapPoint = getPointByScore(firstTeamScore, secondTeamScore);

        if (details.isEmpty() || details == null) {
            details.put(firstTeamName, new TeamDetails(firstTeamName, firstTeamScore, mapPoint.get(1)));
            details.put(secondTeamName, new TeamDetails(secondTeamName, secondTeamScore, mapPoint.get(2)));
        } else {

            if (details.containsKey(firstTeamName)) {
                TeamDetails t = details.get(firstTeamName);
                t.setPoint(t.getPoint() + mapPoint.get(1));
                t.setScore(firstTeamScore);
            } else {
                details.put(firstTeamName, new TeamDetails(firstTeamName, firstTeamScore, mapPoint.get(1)));
            }

            if (details.containsKey(secondTeamName)) {
                TeamDetails t = details.get(secondTeamName);
                t.setPoint(t.getPoint() + mapPoint.get(2));
                t.setScore(secondTeamScore);
            } else {
                details.put(secondTeamName, new TeamDetails(secondTeamName, secondTeamScore, mapPoint.get(2)));
            }
        }
    }

    private static Map<Integer, Integer> getPointByScore(int firstTeamScore, int secondTeamScore) {
        Map<Integer, Integer> map = new HashMap<>();

        if (firstTeamScore > secondTeamScore) {
            map.put(1, 3);
            map.put(2, 0);
        } else if (firstTeamScore == secondTeamScore) {
            map.put(1, 1);
            map.put(2, 1);
        } else if (firstTeamScore < secondTeamScore) {
            map.put(1, 0);
            map.put(2, 3);
        }

        return map;
    }

    private static String getTeamNameByTeamDetails(String teamDetails) {
        return teamDetails.substring(0, teamDetails.length() - 1).trim();
    }

    private static int getScoreByTeamDetails(String teamDetails) {
        return Integer.parseInt(teamDetails.substring(teamDetails.length() - 1, teamDetails.length()));
    }

    private static String getIndividualTeamDetails(String[] teamsDetails, int idx) throws RankingCalculatorException {
        if (teamsDetails.length < 2) {
            throw new RankingCalculatorException("Invalid details of teams entered. There should be two teams");
        }

        return teamsDetails[idx];
    }
}
