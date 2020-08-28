package de.tafelwischenSecure.exceptions;

import de.hechler.patrick.hilfZeugs.objects.patExep.PatrExeption;

public abstract class TafelwischenException extends PatrExeption {
	
	private static final long serialVersionUID = -1867870352113441408L;
	
	public TafelwischenException(String message) {
		super(message);
	}
	
}
