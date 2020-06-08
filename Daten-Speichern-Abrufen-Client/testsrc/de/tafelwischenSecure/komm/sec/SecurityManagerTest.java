package de.tafelwischenSecure.komm.sec;

import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.komm.sec.ClientSidedSecurityManager;
import de.tafelwischenSecure.komm.sec.Verschl�sselteServerNachricht;
import de.tafelwischenSecure.rsa.schl�ssel.AssymetrischPaar;
import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

class SecurityManagerTest {
	
	private static final String PLAINTEXT = "Dieser Text mit Umlauten ������� soll\r\n  verschl�sselt werden!\r\n\r\n";
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
		Verschl�sselteServerNachricht encServerMsg = sm.encrypt(PLAINTEXT, publicKey);
		assertNotNull(encServerMsg);
		byte[] encryptedSeedBytes = ZahlenUmwandeln.hexZuByteArray(encServerMsg.getEncryptedSeed());
		byte[] decryptedSeedBytes = privateKey.entschl�sseln(encryptedSeedBytes);
		long decryptedSeed = ZahlenUmwandeln.zuLong(decryptedSeedBytes);
		assertEquals(encServerMsg.getSeed(), decryptedSeed);
	}
	
	@Test
	void testDecrypt() throws IllegalBlockSizeException, BadPaddingException {
		Verschl�sselteServerNachricht encServerMsg = sm.encrypt(PLAINTEXT, publicKey);
		assertNotNull(encServerMsg);
		String decryptedMessage = sm.decrypt(encServerMsg.getSeed(), encServerMsg.getEncryptedMessage());
		assertEquals(PLAINTEXT, decryptedMessage);
	}
	
}
