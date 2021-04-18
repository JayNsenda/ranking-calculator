package tech.myic.ranking.table.calculator.util;

import tech.myic.ranking.table.calculator.exception.CustomException;

public class TeamDetailsUtil
{
    private TeamDetailsUtil()
    {
    }

    public static String getTeamNameByTeamDetails(String teamDetails)
    {
        return teamDetails.substring(0, teamDetails.length() - 1).trim();
    }

    public static int getScoreByTeamDetails(String teamDetails)
    {
        return Integer.parseInt(teamDetails.substring(teamDetails.length() - 1));
    }

    public static String getIndividualTeamDetails(String[] teamsDetails, int idx)
            throws CustomException
    {
        if (teamsDetails.length < 2){
            throw new CustomException("Invalid details of teams entered. There should be two teams");
        }

        return teamsDetails[idx];
    }
}
