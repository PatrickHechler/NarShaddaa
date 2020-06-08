package de.tafelwischenSecure.exceptions;

public abstract class UserException extends TafelwischenException {
	
	private static final long serialVersionUID = -4028949905976600676L;
	private final String username;
	
	public UserException(String messageBeforName, String username, String messageAfterName) {
		super(messageBeforName + username + messageAfterName);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
}
