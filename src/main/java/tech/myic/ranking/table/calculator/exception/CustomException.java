package tech.myic.ranking.table.calculator.exception;

/**
 * @author jules
 */
public class CustomException
        extends Exception
{
    private static final long serialVersionUID = 1L;

    public CustomException()
    {
    }

    public CustomException(String string)
    {
        super(string);
    }

    public CustomException(String string, Throwable thrwbl)
    {
        super(string, thrwbl);
    }

    public CustomException(Throwable thrwbl)
    {
        super(thrwbl);
    }

}
