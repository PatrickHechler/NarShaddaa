package de.tafelwischenSecure.rsa.schl�ssel.eigener;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Dieser Schl�ssel kann die Dateien, welche mit dem offenem Schl�ssel verschl�sselt wurden entschl�sseln.
 */
public interface AssymetrischEigenerInterface {
	
	public int entschl�sselnInt(byte[] entschl�sseln);
	
	public long entschl�sselnLong(byte[] entschl�sseln);
	
	public byte[] entschl�sseln(byte[] entschl�sseln);
	
	public byte[] entschl�sseln(String entschl�sseln);
	
	public byte[] entschl�sseln(String entschl�sseln, String charset) throws UnsupportedEncodingException;
	
	byte[] entschl�sseln(String entschl�sseln, Charset charset);
	
	public String getName();
	
	public void setName(String name);
	
	@Override
	public String toString();
	
	@Override
	boolean equals(Object obj);
	
}
