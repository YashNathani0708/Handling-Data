package Exceptions;

public class missingFieldException extends Exception {

	public missingFieldException() {
		super("Missing Field error");
	}

	public missingFieldException(String error) {
		super(error);
	}

	public String getMessage() {
		return super.getMessage();
	}
}