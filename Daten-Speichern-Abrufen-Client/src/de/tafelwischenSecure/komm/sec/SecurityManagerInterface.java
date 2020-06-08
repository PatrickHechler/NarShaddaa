package de.tafelwischenSecure.komm.sec;

import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public interface SecurityManagerInterface {
	
	/**
	 * 
	 * @param verschl�sseln
	 * @param serverKey
	 * 
	 * @return
	 */
	public Verschl�sselteServerNachricht encrypt(String verschl�sseln, AssymetrischOffen serverKey);
	
	public String decrypt(long seed, String encryptedMessage);
	
}
