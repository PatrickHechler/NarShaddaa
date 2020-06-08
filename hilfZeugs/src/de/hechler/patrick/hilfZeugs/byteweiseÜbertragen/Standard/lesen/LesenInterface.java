package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen;

import java.io.IOException;

public interface LesenInterface {
	
	/**
	 * Gibt die Anzahl der bisheriger gelesenen Bytes zurück.
	 */
	public long getAnzahlBisherigerBytes();
	
	/**
	 * Gibt das nächste Byte zurück. <br>
	 * null, wenn es kein nächstes Byte gibt.
	 * 
	 * @throws Exception
	 */
	public Integer getNächstesByte() throws IOException;
	
	public byte[] getBytes() throws IOException;
	
	public byte[] getBytes(int maxBytes) throws IOException;
	
	/**
	 * Schließt die Datei.
	 */
	public void close();
	
}
