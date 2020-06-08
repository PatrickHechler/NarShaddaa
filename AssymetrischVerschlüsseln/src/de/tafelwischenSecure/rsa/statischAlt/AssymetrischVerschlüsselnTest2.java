package de.tafelwischenSecure.rsa.statischAlt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AssymetrischVerschl�sselnTest2 {
	
	@Test
	void testByteToHex() {
		int zahl;
		String l�sung;
		
		zahl = 0;
		l�sung = "00";
		System.out.println((byte) zahl);
		assertEquals(l�sung, AssymetrischVerschl�sseln.byteToHex((byte) zahl));
		
		zahl = 255;
		l�sung = "FF";
		System.out.println((byte) zahl);
		assertEquals(l�sung, AssymetrischVerschl�sseln.byteToHex((byte) zahl));
		
		zahl = 16;
		l�sung = "10";
		System.out.println((byte) zahl);
		assertEquals(l�sung, AssymetrischVerschl�sseln.byteToHex((byte) zahl));
		
		zahl = 15;
		l�sung = "0F";
		System.out.println((byte) zahl);
		assertEquals(l�sung, AssymetrischVerschl�sseln.byteToHex((byte) zahl));
		
		zahl = 167;
		l�sung = "A7";
		System.out.println((byte) zahl);
		assertEquals(l�sung, AssymetrischVerschl�sseln.byteToHex((byte) zahl));
		
	}
	
	@Test
	void testKleineZahlZuHex() {
		int runde;
		
		for (runde = 0; runde < 10; runde ++ ) {
			assertEquals(runde + "", AssymetrischVerschl�sseln.kleineZahlZuHex(runde));
		}
		assertEquals("A", AssymetrischVerschl�sseln.kleineZahlZuHex(10));
		assertEquals("B", AssymetrischVerschl�sseln.kleineZahlZuHex(11));
		assertEquals("C", AssymetrischVerschl�sseln.kleineZahlZuHex(12));
		assertEquals("D", AssymetrischVerschl�sseln.kleineZahlZuHex(13));
		assertEquals("E", AssymetrischVerschl�sseln.kleineZahlZuHex(14));
		assertEquals("F", AssymetrischVerschl�sseln.kleineZahlZuHex(15));
		
		try {
			AssymetrischVerschl�sseln.kleineZahlZuHex( -1);
			fail("Kleine Zahl darf -1 nicht annehmen");
		} catch (RuntimeException ignore) {
		}
		try {
			AssymetrischVerschl�sseln.kleineZahlZuHex( -5);
			fail("Kleine Zahl darf -5 nicht annehmen");
		} catch (RuntimeException ignore) {
		}
		try {
			AssymetrischVerschl�sseln.kleineZahlZuHex(16);
			fail("Kleine Zahl darf 16 nicht annehmen");
		} catch (RuntimeException ignore) {
		}
		try {
			AssymetrischVerschl�sseln.kleineZahlZuHex(5378);
			fail("Kleine Zahl darf so hohe nicht annehmen");
		} catch (RuntimeException ignore) {
		}
		
	}
	
}
