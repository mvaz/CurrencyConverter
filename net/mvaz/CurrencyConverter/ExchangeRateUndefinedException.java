package net.mvaz.CurrencyConverter;

/**
 * custom Exception to signalize whenever that an exchange rate is not represented.
 *  
 * @author Miguel Vaz
 */
public class ExchangeRateUndefinedException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExchangeRateUndefinedException() {
	}

	public ExchangeRateUndefinedException(String message) {
		super(message);
	}

	public ExchangeRateUndefinedException(Throwable cause) {
		super(cause);
	}

	public ExchangeRateUndefinedException(String message, Throwable cause) {
		super(message, cause);
	}

}
