package de.tafelwischenSecure.exceptions;

public class InvalidPwHashExeption extends UserException {
	
	private static final long serialVersionUID = 4437299868352362316L;
	
	public InvalidPwHashExeption(String username) {
		super("", username, " is an invalid passwortHash.");
	}
	
	@Override
	public String getStandardNachricht() {
		return "This is an invalid PwHash.";
	}
	
}
