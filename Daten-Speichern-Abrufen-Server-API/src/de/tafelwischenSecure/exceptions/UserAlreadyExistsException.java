package de.tafelwischenSecure.exceptions;

public class UserAlreadyExistsException extends UserException {
	
	/** the SVUID */
	private static final long serialVersionUID = -3703163497877040701L;
	
	public UserAlreadyExistsException(String username) {
		super("user ", username, " exists already");
	}
	
	@Override
	public String getStandardNachricht() {
		return "Ein Benutzer mit diesem Namen existiert bereits und kann deshalb nicht neu engelegt werden.";
	}
	
}
