package de.tafelwischenSecure.komm;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.SocketBiDirektional;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.SocketBiDirektionalInterface;

public class Kommunikation implements KommunikationInterface {
	
	private String serverName;
	private int serverPort;
	
	public Kommunikation(String serverName, int serverPort) {
		this.serverName = serverName;
		this.serverPort = serverPort;
	}
	
	@Override
	public String sendMessage(String message) throws IOException {
		SocketBiDirektionalInterface socket = new SocketBiDirektional(serverName, serverPort);
		String rückgabe = socket.sendWithResponse(message);
		socket.close();
		return rückgabe;
	}
	
}
