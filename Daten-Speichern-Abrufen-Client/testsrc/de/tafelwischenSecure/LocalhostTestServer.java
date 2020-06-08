package de.tafelwischenSecure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalhostTestServer implements Runnable {
	
	private int count;
	private int maxCount;
	private int port;
	private String nextAnswer;
	
	
	public LocalhostTestServer(int port, String answer, int countRequests) {
		this.port = port;
		this.maxCount = countRequests;
		this.count = 0;
		this.nextAnswer = answer;
	}
	
	public void setAnswer(String answer) {
		this.nextAnswer = answer;
	}
	
	public void start() {
		count = -2;
		new Thread(this).start();
		while (count == -2) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	
	public void stop() {
		maxCount = -3;
		while (count != -1) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	
	@Override
	public void run() {
		count = 0;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			ss.setSoTimeout(1000);

			while (count < maxCount) {
				Socket s = ss.accept();// establishes connection
				
				s.setSoTimeout(1000);
				
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			
				count += 1;
				String str = dis.readUTF();
				dout.writeUTF(nextAnswer.replace("*", str));
				dout.flush();
				
				try {
					dis.close();
					dout.close();
					s.close();
				}
				catch (Exception e) {}
			}
			
			
			
		} catch (Exception e) {
		}
		try {
			ss.close();
		} catch (Exception e) {}
		count = -1;
	}
	
}
