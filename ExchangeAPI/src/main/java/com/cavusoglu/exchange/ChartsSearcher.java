package com.cavusoglu.exchange;

import org.apache.log4j.Logger;

/**
 * @author Izel Cavusoglu This class is used for carrying out secure and concise
 *         searches on given chart.
 */
public class ChartsSearcher {

	private Logger logger = Logger.getLogger(getClass());

	private Charts currencyCharts;

	public ChartsSearcher() {
		// TODO Auto-generated constructor stub
	}

	public ChartsSearcher(Charts chart) {


		currencyCharts = chart;
	}

	private boolean hasCurrencyPair(String currency1, String currency2) {

		/* Checks if given currency parity exist in charts */

		if (currencyCharts.getCurrencyCharts().containsKey(
				concatParity(currency1, currency2))) {
			return true;
		} else {
			return false;
		}

	}

	public double searchCurrencyCharts(String currency1, String currency2)
			throws CurrencyPairDoesntFoundException {
	

		logger.trace("Searching parity from charts. Currency1: " + currency1
				+ "Currency2: " + currency2);
		/*
		 * This method looks for concatenated symbols of two currencies at given
		 * hash map.
		 */

		double parity;
		if (currency1.equals(currency2)) {
			parity = 1.0;
		}

		if (hasCurrencyPair(currency1, currency2)) {
			parity = currencyCharts.getCurrencyCharts().get(concatParity(currency1, currency2));
		} else if (hasCurrencyPair(currency2, currency1)) {
			parity = (1 / currencyCharts.getCurrencyCharts()
					.get(concatParity(currency2, currency1)));
			logger.trace("Parity is registered as "+currency2+"/"+currency1);
		} else {
			logger.error("At hashmap,there is no parity like "+ currency1+"/"+currency2+" or "+ currency2+"/"+currency1);
			throw new CurrencyPairDoesntFoundException();
		}
		logger.info("Found parity for curencies. Parity: " + parity
				+ " Currency1: " + currency1 + " Currency2: " + currency2);
		return parity;
	}

	public String concatParity(String currency1, String currency2) {

		/* Concatenates two different currencies' string symbol. */
		String pair = currency1;
		pair = pair.concat("/").concat(currency2);
		return pair;
	}

}