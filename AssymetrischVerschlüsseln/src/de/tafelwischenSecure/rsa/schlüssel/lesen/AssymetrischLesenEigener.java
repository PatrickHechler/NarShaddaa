package de.tafelwischenSecure.rsa.schlüssel.lesen;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.LesenInterface;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;

public class AssymetrischLesenEigener implements AssymetrischLesenEigenerInterface {
	
	private AssymetrischEigener schlüssel;
	private LesenInterface leser;
	
	public AssymetrischLesenEigener(AssymetrischEigener schlüssel, LesenInterface leser) {
		this.schlüssel = schlüssel;
		this.leser = leser;
	}
	
	@Override
	public long getAnzahlBisherigerBytes() {
		return leser.getAnzahlBisherigerBytes();
	}
	
	@Override
	public Integer getNächstesByte() throws Exception {
		Integer zwischen = leser.getNächstesByte();
		if (zwischen == null) {
			return zwischen;
		}
		return schlüssel.entschlüsseln(zwischen);
	}
	
	@Override
	public AssymetrischEigener getSchlüssel() {
		return schlüssel;
	}
	
	@Override
	public String getSchlüsselAlsString() {
		return schlüssel.toString();
	}
	
	@Override
	public void close() {
		leser.close();
	}
	
	@Override
	public byte[] getBytes() throws Exception {
		byte[] bytes = leser.getBytes();
		return schlüssel.entschlüsseln(bytes);
	}
	
	@Override
	public byte[] getBytes(int maxBytes) throws Exception {
		byte[] bytes = leser.getBytes(maxBytes);
		return schlüssel.entschlüsseln(bytes);
	}
	
	public String getName() {
		return schlüssel.getName();
	}
	
}
