package de.tafelwischenSecure.rsa.schl�ssel.schreiben;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.schreiben.SchreibenInterface;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public class AssymetrischSchreibenOffen implements AssymetrischSchreibenOffenInterface {
	
	AssymetrischOffen schl�ssel;
	SchreibenInterface schreiber;
	
	@Override
	public long getGr��e() {
		return schreiber.getGr��e();
	}
	
	@Override
	public void schreibeByte(int schreibeByte) throws Exception {
		schreibeByte = schl�ssel.verschl�sseln(schreibeByte);
		schreiber.schreibeByte(schreibeByte);
	}
	
	@Override
	public void close() {
		schreiber.close();
	}
	
	@Override
	public AssymetrischOffen getSchl�ssel() {
		return schl�ssel;
	}
	
	@Override
	public String getSchl�sselAlsString() {
		return schl�ssel.toString();
	}
	
}
