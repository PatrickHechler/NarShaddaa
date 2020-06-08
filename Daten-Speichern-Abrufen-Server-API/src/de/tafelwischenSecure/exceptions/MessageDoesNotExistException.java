package de.tafelwischenSecure.exceptions;

public class MessageDoesNotExistException extends MessageException {
	
	private static final long serialVersionUID = 3094906326607308546L;
	
	public MessageDoesNotExistException(long id) {
		super("The message " + id + " does not exist.");
	}
	
	@Override
	public String getStandardNachricht() {
		return "A message does not exist";
	}
	
}
