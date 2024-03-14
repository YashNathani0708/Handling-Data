package Exceptions;

public class BadPriceException extends Exception {

	public BadPriceException() {
		super("Bad Price");
	}

	public BadPriceException(String error) {
		super("\nInvalid Price - "+error);
	}

	public String getMessage() {
		return super.getMessage();
	}
}