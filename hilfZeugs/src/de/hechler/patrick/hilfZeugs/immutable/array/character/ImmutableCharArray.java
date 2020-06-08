package de.hechler.patrick.hilfZeugs.immutable.array.character;

import de.hechler.patrick.hilfZeugs.immutable.array.ImmutableArray;

public class ImmutableCharArray extends ImmutableArray {
	
	public ImmutableCharArray(char[] inhalt) {
		super(null);
		this.inhalt = new Character[inhalt.length];
		int runde;
		for (runde = 0; runde < inhalt.length; runde ++ ) {
			this.inhalt[runde] = inhalt[runde];
		}
	}
	
}