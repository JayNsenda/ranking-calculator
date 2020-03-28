package tech.myic.ranking.table.calculator;

/**
 *
 * @author jules
 */
public class RankingCalculatorException extends Exception {

    private static final long serialVersionUID = 1L;

    public RankingCalculatorException() {
    }

    public RankingCalculatorException(String string) {
        super(string);
    }

    public RankingCalculatorException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public RankingCalculatorException(Throwable thrwbl) {
        super(thrwbl);
    }

}
