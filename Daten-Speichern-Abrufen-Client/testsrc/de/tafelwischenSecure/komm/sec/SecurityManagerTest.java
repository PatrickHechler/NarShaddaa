package de.tafelwischenSecure.komm.sec;

import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.komm.sec.ClientSidedSecurityManager;
import de.tafelwischenSecure.komm.sec.VerschlüsselteServerNachricht;
import de.tafelwischenSecure.rsa.schlüssel.AssymetrischPaar;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

class SecurityManagerTest {
	
	private static final String PLAINTEXT = "Dieser Text mit Umlauten äöüßÄÖÜ soll\r\n  verschlüsselt werden!\r\n\r\n";
	private ClientSidedSecurityManager sm;
	private static AssymetrischPaar keyPair; 
	private static AssymetrischEigener privateKey;
	private static AssymetrischOffen publicKey;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		keyPair = new AssymetrischPaar();
		privateKey = keyPair.getEigenen();
		publicKey = keyPair.getOffen();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		sm = new ClientSidedSecurityManager();
	}
	
	@Test
	void testSeedEncryption() throws IllegalBlockSizeException, BadPaddingException {
		VerschlüsselteServerNachricht encServerMsg = sm.encrypt(PLAINTEXT, publicKey);
		assertNotNull(encServerMsg);
		byte[] encryptedSeedBytes = ZahlenUmwandeln.hexZuByteArray(encServerMsg.getEncryptedSeed());
		byte[] decryptedSeedBytes = privateKey.entschlüsseln(encryptedSeedBytes);
		long decryptedSeed = ZahlenUmwandeln.zuLong(decryptedSeedBytes);
		assertEquals(encServerMsg.getSeed(), decryptedSeed);
	}
	
	@Test
	void testDecrypt() throws IllegalBlockSizeException, BadPaddingException {
		VerschlüsselteServerNachricht encServerMsg = sm.encrypt(PLAINTEXT, publicKey);
		assertNotNull(encServerMsg);
		String decryptedMessage = sm.decrypt(encServerMsg.getSeed(), encServerMsg.getEncryptedMessage());
		assertEquals(PLAINTEXT, decryptedMessage);
	}
	
}
