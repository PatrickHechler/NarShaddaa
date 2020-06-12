package de.tafelwischenSecure.rsa.schlüssel.schreiben;

import de.hechler.patrick.hilfZeugs.byteweiseübertragen.Standard.schreiben.SchreibenInterface;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;

public class AssymetrischSchreibenEigener implements AssymetrischSchreibenEigenerInterface {
	
	SchreibenInterface schreiber;
	AssymetrischEigener schlüssel;
	
	@Override
	public long getGrüße() {
		return schreiber.getGrüße();
	}

	@Override
	public void schreibeByte(int schreibeByte) throws Exception {
//		schreibeByte = schlüssel.entschlüsseln(schreibeByte);
//		schreiber.schreibeByte(schreibeByte);
	}

	@Override
	public void close() {
		schreiber.close();
	}

	@Override
	public AssymetrischEigener getSchlüssel() {
		return schlüssel;
	}

	@Override
	public String getSchlüsselAlsString() {
		return schlüssel.toString();
	}
	
}
