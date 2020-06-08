package de.tafelwischenSecure.exceptions;

public class WrongPasswortException extends TafelwischenException {
	
	private static final String MESSAGE = "Das war ein falsches Passwort.";
	private static final long serialVersionUID = 9121985400332277451L;
	
	public WrongPasswortException() {
		super(MESSAGE);
	}
	
	@Override
	public String getStandardNachricht() {
		return MESSAGE;
	}
	
}
