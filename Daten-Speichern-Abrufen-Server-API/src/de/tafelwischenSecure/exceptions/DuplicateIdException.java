package de.tafelwischenSecure.exceptions;

public class DuplicateIdException extends TafelwischenException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4045271964423282899L;
	
	private String doubleThing;
	
	public DuplicateIdException(String doubleThing) {
		super(doubleThing + " exists already");
		this.doubleThing = doubleThing;
	}
	
	@Override
	public String getStandardNachricht() {
		return "The Id exists already";
	}
	
	public String getDoubleThing() {
		return doubleThing;
	}
	
}
