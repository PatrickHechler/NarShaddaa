package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben;

import java.io.IOException;

public interface SchreibenInterface {
	
	public long getGröße();
	
	/**
	 * @throws Exception
	 */
	public void schreibeByte(int schreibeByte) throws IOException;
	
	/**
	 * Schließt die Datei.
	 */
	public void close();
	
	public void schreibeBytes(byte[] bytes) throws IOException;
	
}
