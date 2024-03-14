package Exceptions;

	public class tooManyFieldsException extends Exception {

		public tooManyFieldsException() {
			super("Field errors");
		}

		public tooManyFieldsException(String error) {
			super("\nToo Many Field - "+error);
		}

		public String getMessage() {
			return super.getMessage();
		}
	}