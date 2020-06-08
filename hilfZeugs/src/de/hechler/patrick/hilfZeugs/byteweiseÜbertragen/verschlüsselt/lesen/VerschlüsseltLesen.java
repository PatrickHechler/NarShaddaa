package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.lesen;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.LesenInterface;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.verschlüsselungsZahlen.VerschlüsselZahlen;

public class VerschlüsseltLesen implements VerschlüsseltLesenInterface {
	
	VerschlüsselZahlen verschlüsselZahlen;
	LesenInterface leser;
	
	public VerschlüsseltLesen(LesenInterface leser) {
		verschlüsselZahlen = new VerschlüsselZahlen();
		this.leser = leser;
	}
	
	public VerschlüsseltLesen(LesenInterface leser, long seed) {
		verschlüsselZahlen = new VerschlüsselZahlen(seed);
		this.leser = leser;
	}
	
	@Override
	public byte[] getBytes() throws IOException {
		byte[] rohBytes = leser.getBytes();
		byte[] rückgabe = verschlüsselZahlen.getBytes(rohBytes.length);
		int runde;
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = (byte) (rückgabe[runde] ^ rohBytes[runde]);
		}
		return rückgabe;
	}
	
	@Override
	public byte[] getBytes(int maxBytes) throws IOException {
		byte[] rohBytes = leser.getBytes(maxBytes);
		byte[] rückgabe = verschlüsselZahlen.getBytes(rohBytes.length);
		int runde;
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = (byte) (rückgabe[runde] ^ rohBytes[runde]);
		}
		return rückgabe;
	}
	
	@Override
	public Integer getNächstesByte() throws IOException {
		Integer nächstesVerschlüsselByte = verschlüsselZahlen.getNächstesByte();
		Integer nächstesDateiByte = leser.getNächstesByte();
		if (nächstesDateiByte == null || nächstesVerschlüsselByte == null) {
			return null;
		}
		
		return nächstesVerschlüsselByte ^ nächstesDateiByte;
	}
	
	@Override
	public long getAnzahlBisherigerBytes() {
		return leser.getAnzahlBisherigerBytes();
	}
	
	@Override
	public long getVerschlüsselnungsZahl() {
		return verschlüsselZahlen.getVerschlüsselnungsZahl();
	}
	
	@Override
	public void close() {
		verschlüsselZahlen.close();
		leser.close();
	}
	
}
