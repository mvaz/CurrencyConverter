/**
 * 
 */
package net.mvaz.CurrencyConverter;

import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author miguel
 *
 */
public class ConvertCurrency {

    private static String PROMPT = " >> ";
    private static final String COMMAND_OK = "OK";
    private static final String COMMAND_NOT_FOUND = "incorrect syntax or command not found";

    private static final String doubleRegex = "\\d+(?:[.,]\\d+)?";
    private static final String setCurrencyRegex = "\\s*(.*?)\\s*->\\s*(.*?)\\s*\\((" + doubleRegex + ")\\)";
    private static final String convertCurrencyRegex = "\\s*([\\w+\\s+]+?)\\s*,\\s*([\\w\\s]+?)\\s*\\((" + doubleRegex + ")\\)";

    /**
     * @param args
     */
    public static void main(String[] args) {

        // the film star
        CurrencyConverter converter = new CurrencyConverter();

        // console for command interpretation
        Console objConsole = System.console();
        if (objConsole == null) {
            System.out.println("exiting (null) no console available");
            System.exit(1);
        }
        
        // 
        while (true) {
            // get command
            String command = objConsole.readLine( PROMPT );
            
            // exit if it is EOF
            if ( command == null )
                System.exit(0);
            
            String response = processCommand( converter, command );
            System.out.println(response);
        }
            
    }
    
    public static String processCommand( CurrencyConverter converter, String command)
    {

        String output = null;
        // get matchers
        Matcher setCurrencyMatcher = Pattern.compile(setCurrencyRegex).matcher(command);
        Matcher convertCurrencyMatcher = Pattern.compile(convertCurrencyRegex).matcher(command);

        if ( setCurrencyMatcher.matches()  )
        {
            int i = 1;
            String baseCurrency = setCurrencyMatcher.group(i);
            String goalCurrency = setCurrencyMatcher.group(++i);
            double rate = Double.parseDouble( setCurrencyMatcher.group(++i) );
            
            converter.setExchangeRate(baseCurrency, goalCurrency, rate);
            output = COMMAND_OK;
            
        } else if (convertCurrencyMatcher.matches()) {
            
            int i = 1;
            String baseCurrency = convertCurrencyMatcher.group(i);
            String goalCurrency = convertCurrencyMatcher.group(++i);
            double amount = Double.parseDouble( convertCurrencyMatcher.group(++i) );
            
            double result;
            try {
                result = converter.convertCurrency(baseCurrency, goalCurrency, amount);
                output = String.valueOf(result);

            } catch (ExchangeRateUndefinedException e) {
                e.printStackTrace();
            }
            
        } else {
            output = COMMAND_NOT_FOUND;
        }

        return output;
        
    }

}
