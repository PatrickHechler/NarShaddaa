package de.hechler.patrick.hilfZeugs.immutable.array;

public class ImmutableArray implements ImmutableArrayInterface {
	
	protected Object[] inhalt;
	
	public ImmutableArray(Object[] inhalt) {
		this.inhalt = inhalt;
	}
	
	@Override
	public Object get(int index) {
		return inhalt[index];
	}
	
	@Override
	public int size() {
		return inhalt.length;
	}
	
}
