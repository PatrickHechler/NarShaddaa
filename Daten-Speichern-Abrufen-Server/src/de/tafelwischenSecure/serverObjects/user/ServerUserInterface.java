package de.tafelwischenSecure.serverObjects.user;

import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;
import de.tafelwischenSecure.serverObjects.ServerObjectInterface;

public interface ServerUserInterface extends ServerObjectInterface {
	
	public String getName();
	
	public String getPwHash();
	
	public boolean isPwHash(String hash);
	
	public AssymetrischOffen getOffenenKey();
	
	public String getEncryptedEigenenKey();
	
}
