package de.hechler.patrick.hilfZeugs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;

class ZahlenUmwandelnTest {
	
	@Test
	void test() {
		int zahl;
		byte[] bytes;
		int runde;
		for (runde = 0; runde < 1000; runde ++ ) {
			zahl = new Random().nextInt();
			bytes = ZahlenUmwandeln.zuByteArr(zahl);
			assertEquals(zahl, ZahlenUmwandeln.zuInt(bytes));
			System.out.println(ZahlenUmwandeln.zuHex(zahl));
		}
	}
	
}
