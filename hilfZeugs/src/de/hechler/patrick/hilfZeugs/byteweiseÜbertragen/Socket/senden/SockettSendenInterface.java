package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.senden;

import java.net.SocketException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben.SchreibenInterface;

public interface SockettSendenInterface extends SchreibenInterface {
	
	/**
	 * Setzt ein Timeout in Millisekunden. Ein Wert unter 1 deaktiviert das Timeout.
	 * 
	 * @param timeoutMillisekunden
	 * 
	 * @throws SocketException
	 */
	public void setTimeout(int timeoutMillisekunden) throws SocketException;
	
	public String getServer();
	
	public int getPort();
	
}
