package de.tafelwischenSecure.secure;

public class DecryptedMessage {
	
	private final String message;
	private final long seed;
	
	public DecryptedMessage(String message, long seed) {
		this.message = message;
		this.seed = seed;
	}
	
	public String getMessage() {
		return message;
	}
	
	public long getSeed() {
		return seed;
	}
	
}
