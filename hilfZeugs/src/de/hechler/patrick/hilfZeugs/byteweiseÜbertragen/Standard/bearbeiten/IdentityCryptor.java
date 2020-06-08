package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.bearbeiten;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.LesenInterface;

/**
 * wrapper um ein LesenInterface Objekt,
 * es wird nichts verändert.
 */
public class IdentityCryptor implements LesenInterface {
	
	protected LesenInterface delegate;
	
	public IdentityCryptor(LesenInterface delegate) {
		this.delegate = delegate;
	}
	
	
	public long getAnzahlBisherigerBytes() {
		return delegate.getAnzahlBisherigerBytes();
	}
	
	public Integer getNächstesByte() throws IOException {
		return delegate.getNächstesByte();
	}
	
	public void close() {
		delegate.close();
	}
	
	
	@Override
	public byte[] getBytes() throws IOException {
		return delegate.getBytes();
	}
	
	
	@Override
	public byte[] getBytes(int maxBytes) throws IOException {
		return delegate.getBytes(maxBytes);
	}
	
	
}
