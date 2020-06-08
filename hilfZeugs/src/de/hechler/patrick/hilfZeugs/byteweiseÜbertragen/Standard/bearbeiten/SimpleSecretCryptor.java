package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.bearbeiten;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.LesenInterface;

/**
 * wrapper um ein LesenInterface Objekt,
 * 
 * @author Patrick
 *
 */
public class SimpleSecretCryptor extends IdentityCryptor {
	
	
	public SimpleSecretCryptor(LesenInterface delegate) {
		super(delegate);
	}
	
	public Integer getNächstesByte() throws IOException {
		Integer result = delegate.getNächstesByte();
		if (result == null) {
			return null;
		}
		result = 255 - result;
		return result;
	}
	
}
