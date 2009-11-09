package net.mvaz.CurrencyConverter;

import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Program to interactively test the CurrencyConverter class, using the system console.
 * 
 * @author Miguel Vaz
 *
 */
public class ConvertCurrency {

    private static final String ERROR_EXCHANGE_RATE_NOT_DEFINED = "ERROR: exchange rate not defined";
    private static final String PROMPT = ">> ";
    private static final String COMMAND_OK = "OK";
    private static final String COMMAND_NOT_FOUND = "incorrect syntax or command not found";

    private static final String doubleRegex = "\\d+(?:[.,]\\d+)?";
    private static final String setCurrencyRegex = "\\s*(.*?)\\s*->\\s*(.*?)\\s*\\(\\s*(" + doubleRegex + ")\\s*\\)\\s*";
    private static final String convertCurrencyRegex = "\\s*([\\w+\\s+]+?)\\s*,\\s*([\\w\\s]+?)\\s*\\(\\s*(" + doubleRegex + ")\\s*\\)\\s*";

    
    CurrencyConverter converter = new CurrencyConverter();
   
    
    /**
     * method for parsing and processing the commands.
     * 
     * @param converter
     * @param command
     * @return
     */
    public String processCommand( String command)
    {

        String output = null;
        // get matchers
        Matcher setCurrencyMatcher = Pattern.compile(setCurrencyRegex).matcher(command);
        Matcher convertCurrencyMatcher = Pattern.compile(convertCurrencyRegex).matcher(command);

        // see which command matches, and act accordingly
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
                output = ERROR_EXCHANGE_RATE_NOT_DEFINED;
            }
            
        } else {
            output = COMMAND_NOT_FOUND;
        }

        return output;
    }

    
    /**
     * @param args
     */
    public static void main(String[] args) {

        // the film star
        ConvertCurrency converter = new ConvertCurrency();

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
            
            String response = converter.processCommand( command );
            System.out.println(response);
        }
            
    }
    
    /**
     * Auxiliary function for parsing the commands.
     * 
     * @param converter the CurrencyConverter object
     * @param command 
     * @return
     */

}
