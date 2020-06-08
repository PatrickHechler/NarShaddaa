package de.tafelwischenSecure.rsa.schl�ssel.schreiben;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.schreiben.SchreibenInterface;
import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigener;

public interface AssymetrischSchreibenEigenerInterface extends SchreibenInterface {
	
	public AssymetrischEigener getSchl�ssel();
	
	public String getSchl�sselAlsString();
	
}
