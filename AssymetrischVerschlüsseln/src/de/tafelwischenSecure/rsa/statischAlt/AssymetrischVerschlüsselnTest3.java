package de.tafelwischenSecure.rsa.statischAlt;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

class AssymetrischVerschlüsselnTest3 {
	
	@Test
	void testGeneratePublicKeyFromString() throws NoSuchAlgorithmException, InvalidKeySpecException {
		PublicKey offen = AssymetrischVerschlüsseln.generator(null).getPublic();
		String schlüssel = AssymetrischVerschlüsseln.toString(offen);
		
		assertEquals(offen, AssymetrischVerschlüsseln.generatePublicKeyFromString(schlüssel));
		
	}
	
	@Test
	void testGeneratePrivateKeyFromString() throws NoSuchAlgorithmException, InvalidKeySpecException {
		PrivateKey zu = AssymetrischVerschlüsseln.generator(null).getPrivate();
		String schlüssel = AssymetrischVerschlüsseln.toString(zu);
		
		assertEquals(zu, AssymetrischVerschlüsseln.generatePrivateKeyFromString(schlüssel));
	}
	
}
