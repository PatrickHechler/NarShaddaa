package de.tafelwischenSecure.secure;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.ArrayLesen;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben.ArraySchreiben;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.lesen.VerschlüsseltLesen;
import de.hechler.patrick.hilfZeugs.umwandeln.StringByteConvert;
import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public class ClientSidedSecurityManager implements ClientSidedSecurityManagerInterface {
	
	@Override
	public VerschlüsselteServerNachricht encrypt(String verschlüsseln, AssymetrischOffen offenerKey) {
		byte[] klartextBytes = StringByteConvert.stringZuBytes(verschlüsseln);
		VerschlüsseltLesen leser = new VerschlüsseltLesen(new ArrayLesen(klartextBytes));
		byte[] verschlüsselteBytes;
		try {
			verschlüsselteBytes = leser.getBytes();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String encryptedKey;
		encryptedKey = ZahlenUmwandeln.zuHex(offenerKey.verschlüsseln(leser.getVerschlüsselnungsZahl()));
		return new VerschlüsselteServerNachricht(leser.getVerschlüsselnungsZahl(), encryptedKey, ZahlenUmwandeln.zuHex(verschlüsselteBytes));
	}
	
	@Override
	public String decrypt(long seed, String encryptedMessage) {
		VerschlüsseltLesen leser = new VerschlüsseltLesen(new ArrayLesen(ZahlenUmwandeln.hexZuByteArray(encryptedMessage)), seed);
		ArraySchreiben schreiber = new ArraySchreiben(ZahlenUmwandeln.hexZuByteArray(encryptedMessage).length);
		byte[] bytes;
		try {
			bytes = leser.getBytes();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		schreiber.schreibeBytes(bytes);
		return StringByteConvert.bytesZuString(schreiber.getArray());
	}
	
}
