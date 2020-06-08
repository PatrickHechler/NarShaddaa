package de.tafelwischenSecure.command;

/**
 * This Command is known by the server
 */
 @Implemented(since = 2)
public @interface Implemented {
	
	public int since();
	
}
