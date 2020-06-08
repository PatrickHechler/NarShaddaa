package de.tafelwischenSecure.secure;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.ArrayLesen;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.lesen.VerschlüsseltLesen;
import de.hechler.patrick.hilfZeugs.umwandeln.StringByteConvert;
import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;

public class ServerSidedSecurityManager implements ServerSidedSecurityManagerInterface {
	
	public ServerSidedSecurityManager() {
	}
	
	@Override
	public DecryptedMessage decrypt(AssymetrischEigener serverKey, String encryptedMessage, String encryptedSymetricKeySeed) {
		byte[] encryptedSeedBytes = ZahlenUmwandeln.hexZuByteArray(encryptedSymetricKeySeed);
		long seed = serverKey.entschlüsselnLong(encryptedSeedBytes);
		
		byte[] encryptedMessageBytes = ZahlenUmwandeln.hexZuByteArray(encryptedMessage);
		VerschlüsseltLesen leser = new VerschlüsseltLesen(new ArrayLesen(encryptedMessageBytes), seed);
		byte[] bytesMessage;
		try {
			bytesMessage = leser.getBytes();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		DecryptedMessage decMessage = new DecryptedMessage(StringByteConvert.bytesZuString(bytesMessage), seed);
		return decMessage;
	}
	
	@Override
	public String encrypt(long seed, String message) {
		byte[] messageBytes = StringByteConvert.stringZuBytes(message);
		VerschlüsseltLesen leser = new VerschlüsseltLesen(new ArrayLesen(messageBytes), seed);
		byte[] encryptedMessageBytes;
		try {
			encryptedMessageBytes = leser.getBytes();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return ZahlenUmwandeln.zuHex(encryptedMessageBytes);
	}
	
}
