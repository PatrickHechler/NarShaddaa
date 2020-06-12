package de.tafelwischenSecure.rsa.schlüssel.lesen;

import de.hechler.patrick.hilfZeugs.byteweiseübertragen.Standard.lesen.LesenInterface;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;

public interface AssymetrischLesenEigenerInterface extends LesenInterface {
	
	public AssymetrischEigener getSchlüssel();
	
	public String getSchlüsselAlsString();
	
	public String getName();
	
}
