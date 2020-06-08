package de.hechler.patrick.hilfZeugs.patExep;

public abstract class PatrExeption extends Exception {
	
	private static final long serialVersionUID = 7564957974927547142L;

	public PatrExeption(String message) {
		super(message);
	}
	
	public abstract String getStandardNachricht();
	
}

