package de.tafelwischenSecure.exceptions;

public class InvalidUsernameExeption extends UserException {
	
	private static final long serialVersionUID = 597990179515851305L;
	
	public InvalidUsernameExeption(String username) {
		super("", username, " is an invalid username");
	}
	
	@Override
	public String getStandardNachricht() {
		return "This is an invalid username";
	}
	
}
