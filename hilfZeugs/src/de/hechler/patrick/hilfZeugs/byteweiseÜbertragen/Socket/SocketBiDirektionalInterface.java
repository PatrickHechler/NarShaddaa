package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.senden.SockettSendenInterface;

public interface SocketBiDirektionalInterface extends SockettSendenInterface {
	
	/**
	 * sends the the message to the server, returns the answer of the server
	 * 
	 * @param message
	 * 
	 * @return
	 *         the answer of the server
	 * 
	 * @throws IOException
	 *             error in the server Kommunikation
	 */
	public String sendWithResponse(String message) throws IOException;
	
}
