package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.lesen;

import java.net.SocketException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.LesenInterface;

public interface SocketLesenInterface extends LesenInterface {
	
	public void setTimeout(int timeout) throws SocketException;
	
	public int getPort();
	
}
