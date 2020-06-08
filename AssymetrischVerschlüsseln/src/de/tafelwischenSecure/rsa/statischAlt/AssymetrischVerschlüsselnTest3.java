package de.tafelwischenSecure.rsa.statischAlt;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

class AssymetrischVerschl�sselnTest3 {
	
	@Test
	void testGeneratePublicKeyFromString() throws NoSuchAlgorithmException, InvalidKeySpecException {
		PublicKey offen = AssymetrischVerschl�sseln.generator(null).getPublic();
		String schl�ssel = AssymetrischVerschl�sseln.toString(offen);
		
		assertEquals(offen, AssymetrischVerschl�sseln.generatePublicKeyFromString(schl�ssel));
		
	}
	
	@Test
	void testGeneratePrivateKeyFromString() throws NoSuchAlgorithmException, InvalidKeySpecException {
		PrivateKey zu = AssymetrischVerschl�sseln.generator(null).getPrivate();
		String schl�ssel = AssymetrischVerschl�sseln.toString(zu);
		
		assertEquals(zu, AssymetrischVerschl�sseln.generatePrivateKeyFromString(schl�ssel));
	}
	
}
