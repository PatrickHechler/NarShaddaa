package de.tafelwischenSecure.rsa.schlüssel;

import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigenerInterface;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffenInterface;

public interface AssymetrischPaarInterface extends AssymetrischEigenerInterface, AssymetrischOffenInterface {
	
	public AssymetrischEigenerInterface getEigenen();
	
	public AssymetrischOffenInterface getOffen();
	
	public String getEigenenAlsString();
	
	public String getOffenAlsString();
	
	@Override
	public String toString();
	
}
