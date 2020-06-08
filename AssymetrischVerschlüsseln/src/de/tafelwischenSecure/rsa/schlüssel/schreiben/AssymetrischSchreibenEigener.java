package de.tafelwischenSecure.rsa.schl�ssel.schreiben;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.schreiben.SchreibenInterface;
import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigener;

public class AssymetrischSchreibenEigener implements AssymetrischSchreibenEigenerInterface {
	
	SchreibenInterface schreiber;
	AssymetrischEigener schl�ssel;
	
	@Override
	public long getGr��e() {
		return schreiber.getGr��e();
	}

	@Override
	public void schreibeByte(int schreibeByte) throws Exception {
//		schreibeByte = schl�ssel.entschl�sseln(schreibeByte);
//		schreiber.schreibeByte(schreibeByte);
	}

	@Override
	public void close() {
		schreiber.close();
	}

	@Override
	public AssymetrischEigener getSchl�ssel() {
		return schl�ssel;
	}

	@Override
	public String getSchl�sselAlsString() {
		return schl�ssel.toString();
	}
	
}
