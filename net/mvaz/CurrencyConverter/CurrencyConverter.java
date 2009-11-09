/**
 * 
 */
package net.mvaz.CurrencyConverter;

import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.*;

/**
 * TODO Consider in the future the use of java.util.Currency, which implements ISO-4217
 * 
 * 
 * @author Miguel Vaz
 *
 */

public class CurrencyConverter {

	// directed, because the exchange rate is direction specific
	// non-weighted, because the exchange rates are the edges themselves
	private SimpleDirectedGraph<String,Double> currencyGraph;
	
	/**
	 * Simple constructor. Initializes nothing.
	 */
	public CurrencyConverter() {
		currencyGraph = new SimpleDirectedGraph<String, Double>(Double.class);
	}
	
	/**
	 * 
	 * (not thread-safe)
	 * @param origin
	 * @param goal
	 * @param rate
	 * @return
	 */
	public boolean setExchangeRate(String origin, String goal, double rate)
	{
		// add the vertices (currencies) if they do not exist
		currencyGraph.addVertex(origin);
		currencyGraph.addVertex(goal);
		
		// check whether the edge already exists
		// if so, remove it, in order to add it
		if (currencyGraph.containsEdge(origin, goal)) {
			currencyGraph.removeEdge(origin, goal);
			currencyGraph.removeEdge(goal, origin);
		}
		// add the edge
		boolean	addDirectCurrency = currencyGraph.addEdge(origin, goal, rate);
		// and the direct inverse edge, with the inverse weight
		boolean	addReverseCurrency = currencyGraph.addEdge(goal, origin, 1.0 / rate);
		
		return addDirectCurrency && addReverseCurrency;
	}
	
	/**
	 * Converts a given amount of an origin currency to a goal currency.
	 *  (not thread-safe)
	 *  
	 * @param origin the string identifier of the currency to be exchanged 
	 * @param goal the string identifier of the target currency
	 * @param amount the amount of the origin currency to be exchanged
	 * @return the amount of converted currency
	 * @throws ExchangeRateUndefinedException
	 */
	public double convertCurrency( String origin, String goal, double amount ) throws ExchangeRateUndefinedException
	{
		// find the shortest path between the two currencies
		List<Double> l = DijkstraShortestPath.findPathBetween(currencyGraph, origin, goal);
		
		// when there is no path between the 2 nodes / vertices / currencies
		// DijkstraShortestPath returns null
		if ( l == null)
			throw new ExchangeRateUndefinedException();
		
		// navigate the edges and iteratively compute the exchange rate
		double rate = 1.0;
		for (Double edge : l) {
			rate = rate * edge.doubleValue();
		}
		
		// compute and return the currency value
		return amount * rate;
	}

	/**
	 * Checks whether the currency is already represented.
	 * 
	 * @param currency
	 * @return
	 */
	public boolean containsCurrency( String currency )
	{
		return currencyGraph.containsVertex(currency);
	}
	
	public String toString()
	{
		return currencyGraph.toString();
	}
}
