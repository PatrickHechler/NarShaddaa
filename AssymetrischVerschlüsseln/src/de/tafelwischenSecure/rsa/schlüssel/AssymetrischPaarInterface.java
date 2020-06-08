package de.tafelwischenSecure.rsa.schl�ssel;

import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigenerInterface;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffenInterface;

public interface AssymetrischPaarInterface extends AssymetrischEigenerInterface, AssymetrischOffenInterface {
	
	public AssymetrischEigenerInterface getEigenen();
	
	public AssymetrischOffenInterface getOffen();
	
	public String getEigenenAlsString();
	
	public String getOffenAlsString();
	
	@Override
	public String toString();
	
}
