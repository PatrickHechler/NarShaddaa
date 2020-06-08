package de.tafelwischenSecure.exceptions;

public class UserDoesNotExistsExeption extends UserException {
	
	private static final long serialVersionUID = 2885259182780711609L;
	
	public UserDoesNotExistsExeption(String username) {
		super("The user ", username, " does not exist");
	}
	
	@Override
	public String getStandardNachricht() {
		return "Der Benutzter existiert nicht";
	}
	
}
