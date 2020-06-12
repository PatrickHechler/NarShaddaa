package de.tafelwischenSecure.rsa.schlüssel.schreiben;

import de.hechler.patrick.hilfZeugs.byteweiseübertragen.Standard.schreiben.SchreibenInterface;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public class AssymetrischSchreibenOffen implements AssymetrischSchreibenOffenInterface {
	
	AssymetrischOffen schlüssel;
	SchreibenInterface schreiber;
	
	@Override
	public long getGrüße() {
		return schreiber.getGrüße();
	}
	
	@Override
	public void schreibeByte(int schreibeByte) throws Exception {
		schreibeByte = schlüssel.verschlüsseln(schreibeByte);
		schreiber.schreibeByte(schreibeByte);
	}
	
	@Override
	public void close() {
		schreiber.close();
	}
	
	@Override
	public AssymetrischOffen getSchlüssel() {
		return schlüssel;
	}
	
	@Override
	public String getSchlüsselAlsString() {
		return schlüssel.toString();
	}
	
}
