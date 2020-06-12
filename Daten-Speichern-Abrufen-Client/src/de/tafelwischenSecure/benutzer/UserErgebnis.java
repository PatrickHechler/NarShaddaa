package de.tafelwischenSecure.benutzer;

import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;

public class UserErgebnis {
	private AssymetrischEigener userKey;
	private long userPassword;
	
	public UserErgebnis(AssymetrischEigener userKey, long userPassword) {
		this.userKey = userKey;
		this.userPassword = userPassword;
	}
	
	public AssymetrischEigener getUserKey() {
		return userKey;
	}
	
	public long getUserPassword() {
		return userPassword;
	}
	
	public String getUsername() {
		return userKey.getName();
	}
	
}
