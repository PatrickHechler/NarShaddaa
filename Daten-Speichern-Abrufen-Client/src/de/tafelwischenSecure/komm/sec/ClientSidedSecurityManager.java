package de.tafelwischenSecure.komm.sec;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.lesen.ArrayLesen;
import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.schreiben.ArraySchreiben;
import de.hechler.patrick.hilfZeugs.byteweise�bertragen.verschl�sselt.lesen.Verschl�sseltLesen;
import de.hechler.patrick.hilfZeugs.umwandeln.StringByteConvert;
import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public class ClientSidedSecurityManager implements SecurityManagerInterface {
	
	@Override
	public Verschl�sselteServerNachricht encrypt(String verschl�sseln, AssymetrischOffen serverKey) {
		byte[] klartextBytes = StringByteConvert.stringZuBytes(verschl�sseln);
		Verschl�sseltLesen leser = new Verschl�sseltLesen(new ArrayLesen(klartextBytes));
		byte[] verschl�sselteBytes;
		try {
			verschl�sselteBytes = leser.getBytes();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String encryptedKey;
		encryptedKey = ZahlenUmwandeln.zuHex(serverKey.verschl�sseln(leser.getVerschl�sselnungsZahl()));
		return new Verschl�sselteServerNachricht(leser.getVerschl�sselnungsZahl(), encryptedKey, ZahlenUmwandeln.zuHex(verschl�sselteBytes));
	}
	
	@Override
	public String decrypt(long seed, String encryptedMessage) {
		Verschl�sseltLesen leser = new Verschl�sseltLesen(new ArrayLesen(ZahlenUmwandeln.hexZuByteArray(encryptedMessage)), seed);
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
