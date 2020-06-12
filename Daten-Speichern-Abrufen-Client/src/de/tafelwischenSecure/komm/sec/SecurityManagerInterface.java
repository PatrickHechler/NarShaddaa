package de.tafelwischenSecure.komm.sec;

import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public interface SecurityManagerInterface {
	
	/**
	 * 
	 * @param verschlüsseln
	 * @param serverKey
	 * 
	 * @return
	 */
	public VerschlüsselteServerNachricht encrypt(String verschlüsseln, AssymetrischOffen serverKey);
	
	public String decrypt(long seed, String encryptedMessage);
	
}
