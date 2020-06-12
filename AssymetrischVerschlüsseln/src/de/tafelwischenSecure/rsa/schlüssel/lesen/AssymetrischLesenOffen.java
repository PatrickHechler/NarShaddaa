package de.tafelwischenSecure.rsa.schlüssel.lesen;

import de.hechler.patrick.hilfZeugs.byteweiseübertragen.Standard.lesen.LesenInterface;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public class AssymetrischLesenOffen implements AssymetrischLesenOffenerInterface {
	
	private AssymetrischOffen schlüssel;
	private LesenInterface leser;
	
	public AssymetrischLesenOffen(AssymetrischOffen schlüssel, LesenInterface leser) {
		this.schlüssel = schlüssel;
		this.leser = leser;
	}
	
	@Override
	public long getAnzahlBisherigerBytes() {
		return leser.getAnzahlBisherigerBytes();
	}
	
	@Override
	public Integer getNüchstesByte() throws Exception {
		Integer zwischen = leser.getNüchstesByte();
		if (zwischen == null) {
			return null;
		}
		return schlüssel.verschlüsseln(zwischen);
	}
	
	@Override
	public void close() {
		leser.close();
	}
	
	@Override
	public AssymetrischOffen getSchlüssel() {
		return schlüssel;
	}
	
	@Override
	public String getSchlüsselAlsString() {
		return schlüssel.toString();
	}
	
	@Override
	public byte[] getBytes() throws Exception {
		byte[] bytes = leser.getBytes();
		return schlüssel.verschlüsseln(bytes);
	}
	
	@Override
	public byte[] getBytes(int maxBytes) throws Exception {
		byte[] bytes = leser.getBytes(maxBytes);
		return schlüssel.verschlüsseln(bytes);
	}
	
	@Override
	public String getName() {
		return schlüssel.getName();
	}
	
}
