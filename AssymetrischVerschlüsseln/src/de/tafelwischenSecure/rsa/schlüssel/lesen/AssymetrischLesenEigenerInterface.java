package de.tafelwischenSecure.rsa.schl�ssel.lesen;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.lesen.LesenInterface;
import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigener;

public interface AssymetrischLesenEigenerInterface extends LesenInterface {
	
	public AssymetrischEigener getSchl�ssel();
	
	public String getSchl�sselAlsString();
	
	public String getName();
	
}
