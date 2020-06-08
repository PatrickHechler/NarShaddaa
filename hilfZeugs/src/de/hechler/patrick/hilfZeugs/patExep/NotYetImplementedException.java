package de.hechler.patrick.hilfZeugs.patExep;

public class NotYetImplementedException extends PatrRuntimeException {
	
	private static final long serialVersionUID = -1178070165312850430L;
	
	private static final String MESSAGE_START = "The ";
	private static final String IS_NO_CLASS = "method";
	private static final String IS_CLASS = "class";
	private static final String MESSAGE_ENDE = " is not implemented";
	
	public NotYetImplementedException(String name, boolean isClass) {
		super(MESSAGE_START + ( (isClass) ? IS_CLASS : IS_NO_CLASS) + name + MESSAGE_ENDE);
	}
	
	private static final String DEAFULT_MESSAGE = "This class or method is not implemented";
	
	@Override
	public String getStandardNachricht() {
		return DEAFULT_MESSAGE;
	}
	
}
