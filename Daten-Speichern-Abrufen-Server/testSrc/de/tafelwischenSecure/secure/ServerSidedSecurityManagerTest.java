package de.tafelwischenSecure.secure;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.ArrayLesen;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.lesen.VerschlüsseltLesen;
import de.hechler.patrick.hilfZeugs.umwandeln.StringByteConvert;
import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.rsa.schlüssel.AssymetrischPaar;

class ServerSidedSecurityManagerTest {
	
	@Test
	void testDecrypt() throws Exception {
		ServerSidedSecurityManagerInterface secure = new ServerSidedSecurityManager();
		String message = "Hallo, dies ist eine Tolle Nachricht, welche blöde Umlaute wie ä, ü, ö, ß und ẞ besitzt.";
		AssymetrischPaar serverKeys = new AssymetrischPaar();
		long seed = 4237732462362264l;
		VerschlüsseltLesen leser = new VerschlüsseltLesen(new ArrayLesen(StringByteConvert.stringZuBytes(message)), seed);
		String encryptedMessage = ZahlenUmwandeln.zuHex(leser.getBytes());
		DecryptedMessage decryptedMessage = secure.decrypt(serverKeys.getEigenen(), encryptedMessage, ZahlenUmwandeln.zuHex(serverKeys.verschlüsseln(seed)));
		assertEquals(message, decryptedMessage.getMessage());
		assertEquals(seed, decryptedMessage.getSeed());
	}
	
	@Test
	void testEncrypt() throws IOException {
		ServerSidedSecurityManagerInterface secure = new ServerSidedSecurityManager();
		String klar = "Hallo, dies ist eine Tolle Nachricht, welche blöde Umlaute wie ä, ü, ö, ß und ẞ besitzt.";
		long seed = 4237732462362264l;
		VerschlüsseltLesen leser = new VerschlüsseltLesen(new ArrayLesen(StringByteConvert.stringZuBytes(klar)), seed);
		String encryptedMessage = ZahlenUmwandeln.zuHex(leser.getBytes());
		String secureMessage = secure.encrypt(leser.getVerschlüsselnungsZahl(), klar);
		assertEquals(encryptedMessage, secureMessage);
	}
	
}
