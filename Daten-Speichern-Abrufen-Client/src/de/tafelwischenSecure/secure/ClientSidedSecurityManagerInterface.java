package de.tafelwischenSecure.secure;

import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public interface ClientSidedSecurityManagerInterface {
	
	/**
	 * 
	 * @param verschl�sseln
	 * @param offnerKey
	 * 
	 * @return
	 */
	public Verschl�sselteServerNachricht encrypt(String verschl�sseln, AssymetrischOffen offnerKey);
	
	public String decrypt(long seed, String encryptedMessage);
	
}
