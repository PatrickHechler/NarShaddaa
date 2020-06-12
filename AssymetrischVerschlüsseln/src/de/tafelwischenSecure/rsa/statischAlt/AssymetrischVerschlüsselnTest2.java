package de.tafelwischenSecure.rsa.statischAlt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AssymetrischVerschlüsselnTest2 {
	
	@Test
	void testByteToHex() {
		int zahl;
		String lüsung;
		
		zahl = 0;
		lüsung = "00";
		System.out.println((byte) zahl);
		assertEquals(lüsung, AssymetrischVerschlüsseln.byteToHex((byte) zahl));
		
		zahl = 255;
		lüsung = "FF";
		System.out.println((byte) zahl);
		assertEquals(lüsung, AssymetrischVerschlüsseln.byteToHex((byte) zahl));
		
		zahl = 16;
		lüsung = "10";
		System.out.println((byte) zahl);
		assertEquals(lüsung, AssymetrischVerschlüsseln.byteToHex((byte) zahl));
		
		zahl = 15;
		lüsung = "0F";
		System.out.println((byte) zahl);
		assertEquals(lüsung, AssymetrischVerschlüsseln.byteToHex((byte) zahl));
		
		zahl = 167;
		lüsung = "A7";
		System.out.println((byte) zahl);
		assertEquals(lüsung, AssymetrischVerschlüsseln.byteToHex((byte) zahl));
		
	}
	
	@Test
	void testKleineZahlZuHex() {
		int runde;
		
		for (runde = 0; runde < 10; runde ++ ) {
			assertEquals(runde + "", AssymetrischVerschlüsseln.kleineZahlZuHex(runde));
		}
		assertEquals("A", AssymetrischVerschlüsseln.kleineZahlZuHex(10));
		assertEquals("B", AssymetrischVerschlüsseln.kleineZahlZuHex(11));
		assertEquals("C", AssymetrischVerschlüsseln.kleineZahlZuHex(12));
		assertEquals("D", AssymetrischVerschlüsseln.kleineZahlZuHex(13));
		assertEquals("E", AssymetrischVerschlüsseln.kleineZahlZuHex(14));
		assertEquals("F", AssymetrischVerschlüsseln.kleineZahlZuHex(15));
		
		try {
			AssymetrischVerschlüsseln.kleineZahlZuHex( -1);
			fail("Kleine Zahl darf -1 nicht annehmen");
		} catch (RuntimeException ignore) {
		}
		try {
			AssymetrischVerschlüsseln.kleineZahlZuHex( -5);
			fail("Kleine Zahl darf -5 nicht annehmen");
		} catch (RuntimeException ignore) {
		}
		try {
			AssymetrischVerschlüsseln.kleineZahlZuHex(16);
			fail("Kleine Zahl darf 16 nicht annehmen");
		} catch (RuntimeException ignore) {
		}
		try {
			AssymetrischVerschlüsseln.kleineZahlZuHex(5378);
			fail("Kleine Zahl darf so hohe nicht annehmen");
		} catch (RuntimeException ignore) {
		}
		
	}
	
}
