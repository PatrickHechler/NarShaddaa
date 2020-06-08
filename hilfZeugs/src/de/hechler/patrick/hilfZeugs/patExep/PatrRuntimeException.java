package de.hechler.patrick.hilfZeugs.patExep;

@SuppressWarnings("serial")
public abstract class PatrRuntimeException extends RuntimeException {
	
	public PatrRuntimeException(String message) {
		super(message);
	}
	
	public abstract String getStandardNachricht();
	
}
