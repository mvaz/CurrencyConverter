/**
 * 
 */
package net.mvaz.CurrencyConverter;

//import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

public class TestProgram {

	static final String doubleRegex = "\\d+(?:[.,]\\d+)?";
	static final String setCurrencyRegex = "\\s*(.*?)\\s*->\\s*(.*?)\\s*\\((" + doubleRegex + ")\\)";
	static final String convertCurrencyRegex = "\\s*([\\w+\\s+]+?),\\s*([\\w\\s]+?)\\s*\\((" + doubleRegex + ")\\)";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CurrencyConverter converter = new CurrencyConverter();

		
		List<String> commandList = Arrays.asList(
				"Euro -> Schweizer Franken (1.6798)",
				"Euro -> US Dollar (1.6135)",
				"Euro -> Britisch Pfund (0.8031)",
				"US Dollar -> Britisch Pfund (0.9860)",
				"US Dollar, Schweizer Franken (10)");
		
		// parse and execute every command
		for (String command : commandList) {
			
			Matcher setCurrencyMatcher = Pattern.compile(setCurrencyRegex).matcher(command);
			Matcher convertCurrencyMatcher = Pattern.compile(convertCurrencyRegex).matcher(command);
			

			if ( setCurrencyMatcher.matches()  )
			{
				int i = 1;
				String baseCurrency = setCurrencyMatcher.group(i);
				String goalCurrency = setCurrencyMatcher.group(++i);
				double rate = Double.parseDouble( setCurrencyMatcher.group(++i) );
				
				converter.setExchangeRate(baseCurrency, goalCurrency, rate);
				System.out.println( "Setting exchange rate between '" + baseCurrency + "' and '"
						                                              + goalCurrency + "' to "
						                                              + rate );
				
			} else if (convertCurrencyMatcher.matches()) {
				int i = 1;
				String baseCurrency = convertCurrencyMatcher.group(i);
				String goalCurrency = convertCurrencyMatcher.group(++i);
				double amount = Double.parseDouble( convertCurrencyMatcher.group(++i) );
				
				double result;
				try {
					result = converter.convertCurrency(baseCurrency, goalCurrency, amount);
					System.out.println( baseCurrency + " -> " + goalCurrency + " X " + amount + " = " + result);

				} catch (ExchangeRateUndefinedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				System.out.println( "prob" );
			}

		}
				
//		String currentClassPath = System.getProperty("java.class.path");
//		System.out.println( ")" + currentClassPath + "(");
		System.out.println("the end");
		 
		
		
//      Console objConsole = System.console();
//      if (objConsole != null) {
//          // readLine method call.
//          String userName = objConsole.readLine("Enter  username  : ");
//          System.out.println("You have entered : " + userName);
//      } else {
//      	System.out.println("null");
//      }
      	

//		System.out.println(result);

	}

}
