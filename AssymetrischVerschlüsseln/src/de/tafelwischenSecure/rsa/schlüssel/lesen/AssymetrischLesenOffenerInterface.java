package de.tafelwischenSecure.rsa.schlüssel.lesen;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.LesenInterface;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public interface AssymetrischLesenOffenerInterface extends LesenInterface {
	
	public AssymetrischOffen getSchlüssel();
	
	public String getSchlüsselAlsString();
	
	public String getName();
	
}
