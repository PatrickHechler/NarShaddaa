package de.tafelwischenSecure.rsa.schlüssel;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

class AssymetrischKeyEqualsTest {
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void test() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		AssymetrischPaar paarA1;
		AssymetrischPaar paarA2;
		AssymetrischPaar paarB1;
		AssymetrischPaar paarB2;
		AssymetrischEigener eigeA1;
		AssymetrischEigener eigeA2;
		AssymetrischEigener eigeB1;
		AssymetrischEigener eigeB2;
		AssymetrischOffen offeA1;
		AssymetrischOffen offeA2;
		AssymetrischOffen offeB1;
		AssymetrischOffen offeB2;
		
		paarA1 = new AssymetrischPaar();
		paarA2 = new AssymetrischPaar(paarA1.toString());
		assertEquals(paarA1, paarA2);
		
		paarB1 = new AssymetrischPaar();
		paarB2 = paarB1;
		assertEquals(paarB1, paarB2);
		
		assertNotEquals(paarA1, paarB1);
		assertNotEquals(paarA1, paarB2);
		assertNotEquals(paarA2, paarB1);
		assertNotEquals(paarA2, paarB2);
		
//		TODO machen
		
	}
	
}
