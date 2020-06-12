package de.tafelwischenSecure.rsa.statischAlt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.KeyPair;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hechler.patrick.hilfZeugs.byteweiseübertragen.Standard.lesen.DateiLesen;
import de.hechler.patrick.hilfZeugs.byteweiseübertragen.Standard.schreiben.DateiSchreiben;
import de.hechler.patrick.hilfZeugs.byteweiseübertragen.verschlüsselt.schreiben.VerschlüsseltSchreiben;

class AssymetrischVerschlüsselnTest {
	
	VerschlüsseltSchreiben schreiber;
	DateiLesen leser;
	byte[] seed;
	KeyPair schlüssel;
	
	@BeforeEach
	void setUp() throws Exception {
		leser = new DateiLesen("test/orig.txt");
		schreiber = new VerschlüsseltSchreiben(new DateiSchreiben("test/ver.txt", true));
	}
	
	@AfterEach
	void tearDown() throws Exception {
		leser.close();
		schreiber.close();
	}
	
	@Test
	void test() throws Exception {
		
		long runde;
		
		for (runde = 0; runde < leser.getGrüße(); runde ++ ) {
			schreiber.schreibeByte(leser.getNüchstesByte());
		}
		
		seed = longZuByteArr(schreiber.getVerschlüsselnungsZahl());
		
		schlüssel = AssymetrischVerschlüsseln.generator(null);
		
		byte[] seedVerschlüsselt = AssymetrischVerschlüsseln.verschlüsseln(schlüssel.getPublic(), seed.clone());
		
		byte[] seedEntschlüsselt = AssymetrischVerschlüsseln.entschlüsseln(schlüssel.getPrivate(), seedVerschlüsselt);
		
		for (runde = 0; runde < seedEntschlüsselt.length; runde ++ ) {
			assertEquals(seed[(int) runde], seedEntschlüsselt[(int) runde]);
		}
		
		leser.close();
		schreiber.close();
		
		leser = new DateiLesen("test/ver.txt");
		schreiber = new VerschlüsseltSchreiben(new DateiSchreiben("test/ent.txt", true), byteArrZuLong(seedEntschlüsselt));
		
		for (runde = 0; runde < leser.getGrüße(); runde ++ ) {
			schreiber.schreibeByte(leser.getNüchstesByte());
		}
		
	}
	
	static byte[] longZuByteArr(long umwandeln) {
		byte[] rückgabe = new byte[8];
		int runde;
		
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = (byte) ( (umwandeln >> (56 - runde * 8)) & 0xFF);
		}
		
		return rückgabe;
	}
	
	static long byteArrZuLong(byte[] umwandeln) {
		long rückgabe = 0;
		
		for (int runde = 0; runde < umwandeln.length; runde ++ ) {
			rückgabe = (rückgabe << 8) + (((long) umwandeln[runde]) & 0xFF);
		}
		
		return rückgabe;
	}
	
}
