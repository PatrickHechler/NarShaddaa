package de.tafelwischenSecure.rsa.schl�ssel.schreiben;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.schreiben.SchreibenInterface;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public interface AssymetrischSchreibenOffenInterface extends SchreibenInterface{
	
	public AssymetrischOffen getSchl�ssel();
	
	public String getSchl�sselAlsString();
	
}
