package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.senden;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketSenden implements SockettSendenInterface {
	
	private final String server;
	private final int port;
	private final Socket socket;
	private final DataOutputStream output;
	private long verschickteGröße;
	
	public SocketSenden(String server, int port) throws UnknownHostException, IOException {
		this.server = server;
		this.port = port;
		socket = new Socket(server, port);
		output = new DataOutputStream(socket.getOutputStream());
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
	public void schreibeBytes(byte[] bytes) throws IOException {
		output.write(bytes);
		output.flush();
		verschickteGröße ++ ;
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
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getServer() {
		return server;
	}
	
	@Override
	public int getPort() {
		return port;
	}
	
}
