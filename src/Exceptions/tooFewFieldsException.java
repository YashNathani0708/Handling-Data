package Exceptions;

public class tooFewFieldsException extends Exception {

		public tooFewFieldsException() {
			super("Field errors");
		}

		public tooFewFieldsException(String error) {
			super("\nToo Few Field - "+error);
		}

		public String getMessage() {
			return super.getMessage();
		}
	}