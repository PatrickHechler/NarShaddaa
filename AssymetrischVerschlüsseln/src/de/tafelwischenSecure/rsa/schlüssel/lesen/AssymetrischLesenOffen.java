package de.tafelwischenSecure.rsa.schl�ssel.lesen;

import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.lesen.LesenInterface;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public class AssymetrischLesenOffen implements AssymetrischLesenOffenerInterface {
	
	private AssymetrischOffen schl�ssel;
	private LesenInterface leser;
	
	public AssymetrischLesenOffen(AssymetrischOffen schl�ssel, LesenInterface leser) {
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
			return null;
		}
		return schl�ssel.verschl�sseln(zwischen);
	}
	
	@Override
	public void close() {
		leser.close();
	}
	
	@Override
	public AssymetrischOffen getSchl�ssel() {
		return schl�ssel;
	}
	
	@Override
	public String getSchl�sselAlsString() {
		return schl�ssel.toString();
	}
	
	@Override
	public byte[] getBytes() throws Exception {
		byte[] bytes = leser.getBytes();
		return schl�ssel.verschl�sseln(bytes);
	}
	
	@Override
	public byte[] getBytes(int maxBytes) throws Exception {
		byte[] bytes = leser.getBytes(maxBytes);
		return schl�ssel.verschl�sseln(bytes);
	}
	
	@Override
	public String getName() {
		return schl�ssel.getName();
	}
	
}
