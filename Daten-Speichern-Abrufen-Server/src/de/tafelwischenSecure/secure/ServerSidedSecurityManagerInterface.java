package de.tafelwischenSecure.secure;

import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;

public interface ServerSidedSecurityManagerInterface {
	
	public DecryptedMessage decrypt(AssymetrischEigener serverKey, String encryptedMessage, String encryptedSymetricKeySeed);
	
	public String encrypt(long Seed, String message);
	
}
