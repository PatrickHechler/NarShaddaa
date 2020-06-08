package de.tafelwischenSecure.exceptions;

public abstract class MessageException extends TafelwischenException {
	
	private static final long serialVersionUID = -6364091298307403406L;
	
	public MessageException(String message) {
		super(message);
	}
	
}
