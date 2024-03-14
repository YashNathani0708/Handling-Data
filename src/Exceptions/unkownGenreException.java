package Exceptions;

public class unkownGenreException extends Exception {

		public unkownGenreException() {
			super("Genre error");
		}

		public unkownGenreException(String error) {
			super("\nUnkown Genre - "+error);
		}

		public String getMessage() {
			return super.getMessage();
		}
	}