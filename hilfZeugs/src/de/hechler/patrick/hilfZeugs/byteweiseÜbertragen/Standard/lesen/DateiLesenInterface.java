package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen;

public interface DateiLesenInterface extends LesenInterface {
	
	/**
	 * @return Die Größe der Datei in Byte.
	 */
	public long getGröße();
	
	public String getName();
}
