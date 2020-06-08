package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.verschlüsselungsZahlen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerschlüsselZahlen implements VerschlüsselZahlenInterface {
	
	private long anzahlBisherige;
	private long leseZahl;
	private long multiplikator;
	private long addirenA;
	private long addirenB;
	final private long verschlüsselungsZahl;
	
	public VerschlüsselZahlen() {
		verschlüsselungsZahl = new Random().nextLong();
		VerschlüsselnErstellen();
	}
	
	public VerschlüsselZahlen(long seed) {
		verschlüsselungsZahl = seed;
		VerschlüsselnErstellen();
	}
	
	private void VerschlüsselnErstellen() {
		
		Random zufall = new Random(verschlüsselungsZahl);
		
		leseZahl = zufall.nextLong();
		while (multiplikator == 0) {
			multiplikator = zufall.nextLong();
		}
		addirenA = zufall.nextLong();
		addirenB = zufall.nextLong();
		
	}
	
	@Override
	public long getAnzahlBisherigerBytes() {
		return anzahlBisherige;
	}
	
	@Override
	public byte[] getBytes() {
		return getBytes(1024);
	}
	
	private static byte[] bytesListToArray(List <Byte> bytes) {
		int runde;
		byte[] rückgabe = new byte[bytes.size()];
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = bytes.get(runde);
		}
		return rückgabe;
	}
	
	
	@Override
	public byte[] getBytes(int maxBytes) {
		if (multiplikator == 0) {
			return null;
		}
		List <Byte> bytes = new ArrayList <>();
		int runde;
		for (runde = 0; runde < maxBytes; runde ++ ) {
			Integer zwischen = getNächstesByte();
			if (zwischen == null) {
				break;
			}
			bytes.add((byte) (int) zwischen);
		}
		return bytesListToArray(bytes);
	}
	
	@Override
	public Integer getNächstesByte() {
		
		if (multiplikator == 0) {
			return null;
		}
		
		anzahlBisherige ++ ;
		
		leseZahl += addirenA;
		leseZahl *= multiplikator;
		leseZahl += addirenB;
		
		return (int) leseZahl % (Byte.MAX_VALUE + 1);
	}
	
	@Override
	public void close() {
		leseZahl = 0;
		addirenA = 0;
		addirenB = 0;
		multiplikator = 0;
	}
	
	@Override
	public long getVerschlüsselnungsZahl() {
		return verschlüsselungsZahl;
	}
	
}
