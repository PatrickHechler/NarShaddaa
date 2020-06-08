package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketBiDirektional implements SocketBiDirektionalInterface {
	
	private final String server;
	private final int port;
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;
	private long verschickteGröße;
	
	public SocketBiDirektional(String server, int port) throws UnknownHostException, IOException {
		this.server = server;
		this.port = port;
		socket = new Socket(server, port);
		setTimeout(10000);
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
	}
	
	@Override
	public long getGröße() {
		return verschickteGröße;
	}
	
	@Override
	public void schreibeByte(int schreibeByte) throws IOException {
		output.writeByte(schreibeByte);
		output.flush();
		verschickteGröße ++ ;
	}
	
	@Override
	public String sendWithResponse(String message) throws IOException {
		if (message == null) {
			throw new NullPointerException("sendWithResponse called with null message is not allowed");
		}
		output.writeUTF(message);
		output.flush();
		String rückgabe = input.readUTF();
		return rückgabe;
	}
	
	@Override
	public void schreibeBytes(byte[] bytes) throws IOException {
		output.write(bytes);
		verschickteGröße += bytes.length;
	}
	
	@Override
	public int getPort() {
		return port;
	}
	
	@Override
	public String getServer() {
		return server;
	}
	
	@Override
	public void setTimeout(int timeoutMillisekunden) throws SocketException {
		if (timeoutMillisekunden > 0) {
			socket.setSoTimeout(timeoutMillisekunden);
		} else {
			socket.setSoTimeout(0);
		}
	}
	
	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

