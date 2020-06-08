package de.tafelwischenSecure.rsa.statischAlt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

class AssymetrischVerschl�sselnTestTest {
	
	@Test
	void test() {
		long zahl = new Random().nextLong();
		byte[] zwischen;
		long ergebnis;
		
		
		zwischen = AssymetrischVerschl�sselnTest.longZuByteArr(zahl);
		
		ergebnis = AssymetrischVerschl�sselnTest.byteArrZuLong(zwischen);
		
		System.out.println("Orig:		" + zahl);
		System.out.println("Ergebnis:	" + ergebnis);
		assertEquals(zahl , ergebnis);
		
	}
	
}
