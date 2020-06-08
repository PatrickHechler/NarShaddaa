package de.tafelwischenSecure.worker;

import java.io.IOException;
import java.net.Socket;

public interface ServerWorkerInterface extends Runnable {
	
	/**
	 * Creates In-/OutputStreams and sets the Timeout
	 * 
	 * @throws IOException
	 */
	public void init(Socket s) throws IOException;
	
	public void setSeed(long seed);
	
	public Long getSeed();
	
	public String readCommand() throws IOException;
	
	public String executeCommand(String command);
	
	public void sendResponse(String antworten) throws IOException;
	
	/**
	 * Schließt alle Sachen, von denen der Server nichts weiß. <br>
	 * Aber auch nur diese, damit der Server nicht gestört wird.
	 */
	public void clearTheNotServerRelevantThings();
	
}
