package de.tafelwischenSecure.exceptions;

public class InvalidAccessException extends TafelwischenException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1417810395784479416L;
	private final String forbiddenThing;
	private final String user;
	
	public InvalidAccessException(String user, String forbiddenThing) {
		super(user + " has not the permission for the " + forbiddenThing);
		this.user = user;
		this.forbiddenThing = forbiddenThing;
	}
	
	@Override
	public String getStandardNachricht() {
		return "You hve not the permission to do this.";
	}
	
	public String getForbiddenThing() {
		return forbiddenThing;
	}
	
	public String getUser() {
		return user;
	}
	
}
