package Exceptions;

public class BadYearException extends Exception {

	public BadYearException() {
		super("Bad year");
	}

	public BadYearException(String error) {
		super("\nInvalid year - "+error);
	}

	public String getMessage() {
		return super.getMessage();
	}
}