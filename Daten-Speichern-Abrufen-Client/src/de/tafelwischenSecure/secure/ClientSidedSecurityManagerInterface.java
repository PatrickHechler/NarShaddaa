package de.tafelwischenSecure.secure;

import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public interface ClientSidedSecurityManagerInterface {
	
	/**
	 * 
	 * @param verschlüsseln
	 * @param offnerKey
	 * 
	 * @return
	 */
	public VerschlüsselteServerNachricht encrypt(String verschlüsseln, AssymetrischOffen offnerKey);
	
	public String decrypt(long seed, String encryptedMessage);
	
}
