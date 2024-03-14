package Exceptions;

public class BadIsbn10Exception extends Exception {

	public BadIsbn10Exception() {
		super("Bad ISBN 10");
	}

	public BadIsbn10Exception(String error) {
		super("\nInvalid ISBN 10 Value - "+error);
	}

	public String getMessage() {
		return super.getMessage();
	}
}