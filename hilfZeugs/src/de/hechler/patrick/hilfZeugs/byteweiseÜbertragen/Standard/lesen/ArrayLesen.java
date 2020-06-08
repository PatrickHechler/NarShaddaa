package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen;

import java.io.IOException;

public class ArrayLesen implements LesenInterface {
	
	private int index;
	private byte[] lesen;
	
	public ArrayLesen(byte[] array) {
		lesen = array;
	}
	
	@Override
	public long getAnzahlBisherigerBytes() {
		return index;
	}
	
	@Override
	public Integer getNächstesByte() {
		try {
			int rückgabe;
			rückgabe = ((int) lesen[index]) & 0xFF;
			index ++ ;
			return rückgabe;
		} catch (ArrayIndexOutOfBoundsException fehler) {
			return null;
		}
	}
	
	@Override
	public void close() {
		lesen = null;
	}
	
	public long getGröße() {
		return lesen.length;
	}
	
	@Override
	public byte[] getBytes() throws IOException {
		if (index >= lesen.length) {
			return new byte[0];
		}
		if (index == 0) {
			index = lesen.length;
			return lesen.clone();
		}
		byte[] rückgabe = new byte[lesen.length - index];
		int runde;
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = lesen[runde + index];
		}
		index = lesen.length;
		return rückgabe;
	}
	
	@Override
	public byte[] getBytes(int maxBytes) throws IOException {
		if (index >= lesen.length) {
			return new byte[0];
		}
		byte[] rückgabe = new byte[lesen.length - index];
		int runde;
		for (runde = 0; runde < rückgabe.length && runde < maxBytes; runde ++ ) {
			rückgabe[runde] = lesen[runde + index];
		}
		index += runde;
		return rückgabe;
	}
	
}
