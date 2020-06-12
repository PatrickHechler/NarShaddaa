package de.tafelwischenSecure.rsa.schlüssel.eigener;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Dieser Schlüssel kann die Dateien, welche mit dem offenem Schlüssel verschlüsselt wurden entschlüsseln.
 */
public interface AssymetrischEigenerInterface {
	
	public int entschlüsselnInt(byte[] entschlüsseln);
	
	public long entschlüsselnLong(byte[] entschlüsseln);
	
	public byte[] entschlüsseln(byte[] entschlüsseln);
	
	public byte[] entschlüsseln(String entschlüsseln);
	
	public byte[] entschlüsseln(String entschlüsseln, String charset) throws UnsupportedEncodingException;
	
	byte[] entschlüsseln(String entschlüsseln, Charset charset);
	
	public String getName();
	
	public void setName(String name);
	
	@Override
	public String toString();
	
	@Override
	boolean equals(Object obj);
	
}
