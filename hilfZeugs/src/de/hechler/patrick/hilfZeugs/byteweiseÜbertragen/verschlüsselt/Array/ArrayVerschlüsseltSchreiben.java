package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.Array;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben.ArraySchreiben;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.schreiben.VerschlüsseltSchreiben;

public class ArrayVerschlüsseltSchreiben extends VerschlüsseltSchreiben {
	
	public ArrayVerschlüsseltSchreiben(ArraySchreiben schreiber) {
		super(schreiber);
	}
	
	public ArrayVerschlüsseltSchreiben(ArraySchreiben schreiber, long seed) {
		super(schreiber, seed);
	}
	
	public byte[] getArray() {
		return ((ArraySchreiben) schreiber).getArray();
	}
	
	
}
