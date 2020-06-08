package de.hechler.patrick.hilfZeugs.dateiOderOrdner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DateiOderOrdnerTest {
	
	@Test
	void test() {
		DateiOderOrdner.destroy("test/");
		assertFalse(DateiOderOrdner.exists("test/"));
		assertTrue(DateiOderOrdner.create("test/"));
		assertTrue(DateiOderOrdner.exists("test/"));
		
		DateiOderOrdner.destroy("test/");
		assertFalse(DateiOderOrdner.exists("test/"));
		assertTrue(DateiOderOrdner.create("test/"));
		assertTrue(DateiOderOrdner.exists("test/"));
	}
	
}
