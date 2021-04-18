package tech.myic.ranking.table.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import tech.myic.ranking.table.calculator.util.PrinterUtil;

/**
 * @author jules
 */
public class Main
{
    public static void main(String[] args)
            throws IOException
    {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            PrinterUtil.printRankings(r);
        }
    }

}
