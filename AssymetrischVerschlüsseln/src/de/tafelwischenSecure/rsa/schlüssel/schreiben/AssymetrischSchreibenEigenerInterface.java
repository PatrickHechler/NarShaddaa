package de.tafelwischenSecure.rsa.schlüssel.schreiben;

import de.hechler.patrick.hilfZeugs.byteweiseübertragen.Standard.schreiben.SchreibenInterface;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;

public interface AssymetrischSchreibenEigenerInterface extends SchreibenInterface {
	
	public AssymetrischEigener getSchlüssel();
	
	public String getSchlüsselAlsString();
	
}
