package de.tafelwischenSecure.rsa.statischAlt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.KeyPair;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.lesen.DateiLesen;
import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.schreiben.DateiSchreiben;
import de.hechler.patrick.hilfZeugs.byteweise�bertragen.verschl�sselt.schreiben.Verschl�sseltSchreiben;

class AssymetrischVerschl�sselnTest {
	
	Verschl�sseltSchreiben schreiber;
	DateiLesen leser;
	byte[] seed;
	KeyPair schl�ssel;
	
	@BeforeEach
	void setUp() throws Exception {
		leser = new DateiLesen("test/orig.txt");
		schreiber = new Verschl�sseltSchreiben(new DateiSchreiben("test/ver.txt", true));
	}
	
	@AfterEach
	void tearDown() throws Exception {
		leser.close();
		schreiber.close();
	}
	
	@Test
	void test() throws Exception {
		
		long runde;
		
		for (runde = 0; runde < leser.getGr��e(); runde ++ ) {
			schreiber.schreibeByte(leser.getN�chstesByte());
		}
		
		seed = longZuByteArr(schreiber.getVerschl�sselnungsZahl());
		
		schl�ssel = AssymetrischVerschl�sseln.generator(null);
		
		byte[] seedVerschl�sselt = AssymetrischVerschl�sseln.verschl�sseln(schl�ssel.getPublic(), seed.clone());
		
		byte[] seedEntschl�sselt = AssymetrischVerschl�sseln.entschl�sseln(schl�ssel.getPrivate(), seedVerschl�sselt);
		
		for (runde = 0; runde < seedEntschl�sselt.length; runde ++ ) {
			assertEquals(seed[(int) runde], seedEntschl�sselt[(int) runde]);
		}
		
		leser.close();
		schreiber.close();
		
		leser = new DateiLesen("test/ver.txt");
		schreiber = new Verschl�sseltSchreiben(new DateiSchreiben("test/ent.txt", true), byteArrZuLong(seedEntschl�sselt));
		
		for (runde = 0; runde < leser.getGr��e(); runde ++ ) {
			schreiber.schreibeByte(leser.getN�chstesByte());
		}
		
	}
	
	static byte[] longZuByteArr(long umwandeln) {
		byte[] r�ckgabe = new byte[8];
		int runde;
		
		for (runde = 0; runde < r�ckgabe.length; runde ++ ) {
			r�ckgabe[runde] = (byte) ( (umwandeln >> (56 - runde * 8)) & 0xFF);
		}
		
		return r�ckgabe;
	}
	
	static long byteArrZuLong(byte[] umwandeln) {
		long r�ckgabe = 0;
		
		for (int runde = 0; runde < umwandeln.length; runde ++ ) {
			r�ckgabe = (r�ckgabe << 8) + (((long) umwandeln[runde]) & 0xFF);
		}
		
		return r�ckgabe;
	}
	
}
