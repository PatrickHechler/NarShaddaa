package de.tafelwischenSecure.rsa.schlüssel.schreiben;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben.SchreibenInterface;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public interface AssymetrischSchreibenOffenInterface extends SchreibenInterface{
	
	public AssymetrischOffen getSchlüssel();
	
	public String getSchlüsselAlsString();
	
}
