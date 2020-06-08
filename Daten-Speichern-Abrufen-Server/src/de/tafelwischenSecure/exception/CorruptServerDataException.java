package de.tafelwischenSecure.exception;

import de.tafelwischenSecure.exceptions.TafelwischenException;

public class CorruptServerDataException extends TafelwischenException {
	
	private static final long serialVersionUID = 6064200686171140514L;
	
	public CorruptServerDataException(String wrongThing) {
		super("Error with reading the " + wrongThing);
	}
	
	@Override
	public String getStandardNachricht() {
		return "error loading the saved server data";
	}
	
}
