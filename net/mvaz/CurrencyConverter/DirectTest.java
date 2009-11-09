package net.mvaz.CurrencyConverter;
 
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for the basic functionality of the CurrencyConverter class
 * @author Miguel Vaz
 *
 */
public class DirectTest {

	private final String usd = "USD";
	private final String eur = "EUR";
	private final String jpy = "JPY";
	private final String bla = "BLA";
	private final String gbp = "GBP";

	private static final double EQUALS_DELTA = 0.000000000000000000001;
	
	/**
	 * tests the basic functionality
	 */
	@Test
    public void simpleTest() {
		CurrencyConverter ccv = new CurrencyConverter();

		double amount = 25.0;
		double rate = 1.1;
		ccv.setExchangeRate(usd,eur,1.1);
		
		try {
			double zbr = ccv.convertCurrency(usd,eur,25);
			assertEquals(rate * amount, zbr, EQUALS_DELTA);
			
		} catch (ExchangeRateUndefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * tests the inverse exchange rate
     */
	@Test
    public void reverseTest() {
		CurrencyConverter ccv = new CurrencyConverter();

		double amount = 25.0;
		double rate = 1.1;
		ccv.setExchangeRate(usd,eur,1.1);
		
		try {
			double zbr = ccv.convertCurrency(eur,usd,25);
			assertEquals( (1.0 / rate) * amount, zbr, EQUALS_DELTA);
			
		} catch (ExchangeRateUndefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * tests the choice for the shortest "path", when different ways of
     * calculating the exchange rate are available
     */
	@Test
	public void testDifferentPaths() {
		CurrencyConverter ccv = new CurrencyConverter();
		
		// longest path (with lower sum of edges)
		ccv.setExchangeRate(eur,usd,0.1);
		ccv.setExchangeRate(usd,bla,0.56);
		ccv.setExchangeRate(bla,gbp,0.13);
		ccv.setExchangeRate(gbp,jpy,0.12);
		
		// shortest path (with higher sum of edges)
		ccv.setExchangeRate(eur,jpy,4.033);
		
		try {
			double amount = 1.0;
			double value1 = ccv.convertCurrency(bla,eur,amount);
			double expectedValue1 = amount / 0.56 / 0.1;
			assertEquals(expectedValue1, value1, EQUALS_DELTA);
			
			double value2 = ccv.convertCurrency(eur,jpy,amount);
			double expectedValue2 = amount * 4.033;  
			assertEquals(expectedValue2, value2, EQUALS_DELTA);

		} catch (ExchangeRateUndefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * tests the ability to redefine an exchange rate
	 */
	@Test
	public void testOverrideNodes() {
		CurrencyConverter ccv = new CurrencyConverter();
		
		// build graph
		ccv.setExchangeRate(eur,usd,0.1);
		ccv.setExchangeRate(usd,bla,0.56);
		ccv.setExchangeRate(eur,jpy,4.033);
		ccv.setExchangeRate(bla,gbp,0.13);
		ccv.setExchangeRate(gbp,jpy,0.12);
		ccv.setExchangeRate(gbp,jpy,0.21);

		// convert and control
		try {
			double zbr2 = ccv.convertCurrency(gbp,jpy,1.0);
			assertEquals(.21, zbr2, EQUALS_DELTA);

		} catch (ExchangeRateUndefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}