package de.tafelwischenSecure.rsa.schl�ssel.lesen;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.lesen.LesenInterface;
import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigener;

public class AssymetrischLesenEigener implements AssymetrischLesenEigenerInterface {
	
	private AssymetrischEigener schl�ssel;
	private LesenInterface leser;
	
	public AssymetrischLesenEigener(AssymetrischEigener schl�ssel, LesenInterface leser) {
		this.schl�ssel = schl�ssel;
		this.leser = leser;
	}
	
	@Override
	public long getAnzahlBisherigerBytes() {
		return leser.getAnzahlBisherigerBytes();
	}
	
	@Override
	public Integer getN�chstesByte() throws Exception {
		Integer zwischen = leser.getN�chstesByte();
		if (zwischen == null) {
			return zwischen;
		}
		return schl�ssel.entschl�sseln(zwischen);
	}
	
	@Override
	public AssymetrischEigener getSchl�ssel() {
		return schl�ssel;
	}
	
	@Override
	public String getSchl�sselAlsString() {
		return schl�ssel.toString();
	}
	
	@Override
	public void close() {
		leser.close();
	}
	
	@Override
	public byte[] getBytes() throws Exception {
		byte[] bytes = leser.getBytes();
		return schl�ssel.entschl�sseln(bytes);
	}
	
	@Override
	public byte[] getBytes(int maxBytes) throws Exception {
		byte[] bytes = leser.getBytes(maxBytes);
		return schl�ssel.entschl�sseln(bytes);
	}
	
	public String getName() {
		return schl�ssel.getName();
	}
	
}
