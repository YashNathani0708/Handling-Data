package Exceptions;

public class BadIsbn13Exception extends Exception {

	public BadIsbn13Exception() {
		super("Bad ISBN 13");
	}

	public BadIsbn13Exception(String error) {
		super("\nInvalid ISBN 13 Value - "+error);
	}

	public String getMessage() {
		return super.getMessage();
	}
}