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
	
	public CurrencyConverter() {
		currencyGraph = new SimpleDirectedGraph<String, Double>(Double.class);
	}
	
	// not thread-safe
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
	
	// not thread-safe
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

	public boolean containsCurrency( String currency )
	{
		return currencyGraph.containsVertex(currency);
	}
	
//	protected DirectedWeightedMultigraph<String,Double> getGraph()
//	{
//		return currencyGraph;
//	}
//	
	public String toString()
	{
		return currencyGraph.toString();
	}
}
