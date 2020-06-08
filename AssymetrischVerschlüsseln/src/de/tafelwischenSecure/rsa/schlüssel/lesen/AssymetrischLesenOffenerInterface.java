package de.tafelwischenSecure.rsa.schl�ssel.lesen;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.lesen.LesenInterface;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public interface AssymetrischLesenOffenerInterface extends LesenInterface {
	
	public AssymetrischOffen getSchl�ssel();
	
	public String getSchl�sselAlsString();
	
	public String getName();
	
}
