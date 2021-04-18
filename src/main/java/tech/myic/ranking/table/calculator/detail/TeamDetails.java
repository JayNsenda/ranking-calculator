package tech.myic.ranking.table.calculator.detail;

/**
 *
 * @author jules
 */
public class TeamDetails {

    private final String teamName;
    private int score;
    private int point;

    public TeamDetails(String teamName, int score, int point) {
        this.teamName = teamName;
        this.score = score;
        this.point = point;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int addPoint() {
        return getPoint() + point;
    }

}
