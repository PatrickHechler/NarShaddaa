package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.lesen;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class SocketLesen implements SocketLesenInterface {
	
	private long anzahlBisherigerBytes;
	private final int port;
	
	private final ServerSocket serverSocket;
	private final Socket socket;
	private final DataInputStream input;
	
	public SocketLesen(int port) throws IOException {
		this.port = port;
		
		serverSocket = new ServerSocket(port);
		socket = serverSocket.accept();
		input = new DataInputStream(socket.getInputStream());
		
	}
	
	@Override
	public long getAnzahlBisherigerBytes() {
		return anzahlBisherigerBytes;
	}
	
	@Override
	public byte[] getBytes() throws IOException {
		byte[] rückgabe = input.readAllBytes();
		anzahlBisherigerBytes += rückgabe.length;
		return rückgabe;
	}
	
	@Override
	public byte[] getBytes(int maxBytes) throws IOException {
		byte[] rückgabe = input.readNBytes(maxBytes);
		anzahlBisherigerBytes += rückgabe.length;
		return rückgabe;
	}
	
	@Override
	public Integer getNächstesByte() {
		Integer rückgabe;
		try {
			rückgabe = (int) input.readByte();
			rückgabe = rückgabe & 0XFF;
		} catch (IOException fahler) {
			rückgabe = null;
		}
		return rückgabe;
	}
	
	@Override
	public void close() {
		try {
			input.close();
			serverSocket.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setTimeout(int timeout) throws SocketException {
		if (timeout < 0) {
			timeout = 0;
		}
		socket.setSoTimeout(timeout);
	}
	
	@Override
	public int getPort() {
		return port;
	}
	
}
