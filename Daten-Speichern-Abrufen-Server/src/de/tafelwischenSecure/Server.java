package de.tafelwischenSecure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import de.tafelwischenSecure.commandExecution.ServerCommandExecuter;
import de.tafelwischenSecure.commandExecution.ServerCommandExecuterInterface;
import de.tafelwischenSecure.exception.CorruptServerDataException;
import de.tafelwischenSecure.rsa.schlüssel.AssymetrischPaar;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;
import de.tafelwischenSecure.secure.ServerSidedSecurityManager;
import de.tafelwischenSecure.secure.ServerSidedSecurityManagerInterface;
import de.tafelwischenSecure.serverObjects.message.ServerMessage;
import de.tafelwischenSecure.serverObjects.user.ServerUser;
import de.tafelwischenSecure.worker.ServerWorker;
import de.tafelwischenSecure.worker.ServerWorkerInterface;

public class Server {
	
	private static final String SAVE_START_ANFANG = "SERVER-DATA:V";
	private static final String SAVE_START = SAVE_START_ANFANG + TafelwischenSecureServer.VERSION;
	private static final String SAVE_END = "FINISH";
	private AssymetrischPaar key;
	private ServerCommandExecuterInterface commandExecuter;
	private ServerSidedSecurityManagerInterface secure;
	
	private int port;
	private String saveFolder;
	
	private boolean weiterlaufen;
	private boolean online;
	
	/**
	 * the server must use load() before using
	 * 
	 * @param port
	 * @param saveFolder
	 */
	public Server(int port, String saveFolder) {
		this.key = null;
		this.commandExecuter = new ServerCommandExecuter(this);
		this.secure = new ServerSidedSecurityManager();
		this.port = port;
		this.weiterlaufen = false;
		this.online = false;
		this.saveFolder = saveFolder;
		if (saveFolder == null) {
			key = new AssymetrischPaar();
		}
	}
	
	public boolean isWeiterlaufen() {
		return weiterlaufen;
	}
	
	public AssymetrischOffen getPublicKey() {
		return key.getOffen();
	}
	
	public AssymetrischEigener getPrivateKey() {
		return key.getEigenen();
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public void start() {
		if (key == null) {
			throw new RuntimeException("The server hasn't been loaded");
		}
		weiterlaufen = true;
		online = true;
		haupt();
	}
	
	public void startBackground() {
		new Thread(() -> {
			start();
		}).start();
	}
	
	private void haupt() {
		ServerSocket ss;
		ServerWorkerInterface worker;
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		while (weiterlaufen) {
			try {
				Socket socket = ss.accept();
				if ( !weiterlaufen) {
					break;
				}
				worker = new ServerWorker(socket, secure, this, commandExecuter);
				new Thread(worker).start();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		
		weiterlaufen = false;
		online = false;
		
		try {
			ss.close();
		} catch (Exception e) {
		}
	}
	
	public void stop() throws FileNotFoundException, IOException {
		weiterlaufen = false;
		save();
		sendDummy();
		while (online) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
	
	private void sendDummy() {
		try {
			Socket socket = new Socket("localhost", port);
			socket.setSoTimeout(100);
			OutputStream out = socket.getOutputStream();
			out.write(0);
			out.close();
			socket.close();
		} catch (IOException e) {
		}
	}
	
	public void save(String fileName) throws IOException {
		Files.createDirectories(Paths.get(saveFolder));
		DataOutputStream schreiber = new DataOutputStream(new FileOutputStream(saveFolder + "/" + fileName));
		
		writeUTF(schreiber, SAVE_START);
		writeUTF(schreiber, key.toString());
		
		Collection <ServerUser> users = ServerUser.getUsers();
		writeInt(schreiber, users.size());
		for (ServerUser dieser : users) {
			dieser.writeToDataStream(schreiber);
			TafelwischenSecureServer.logDebug(dieser.toString());
		}
		
		writeLong(schreiber, ServerMessage.getCounter());
		Collection <ServerMessage> messages = ServerMessage.getMessages();
		writeInt(schreiber, messages.size());
		for (ServerMessage diese : messages) {
			diese.writeToDataStream(schreiber);
			TafelwischenSecureServer.logDebug(diese.toString());
		}
		
		writeUTF(schreiber, SAVE_END);
		schreiber.close();
	}
	
	public void save() throws IOException {
		save("/server.data");
	}
	
	private void writeLong(DataOutputStream schreiber, long zahl) throws IOException {
		TafelwischenSecureServer.logDebug("WRITE-LONG = " + zahl);
		schreiber.writeLong(zahl);
	}
	
	private void writeInt(DataOutputStream schreiber, int zahl) throws IOException {
		TafelwischenSecureServer.logDebug("WRITE-INT = " + zahl);
		schreiber.writeInt(zahl);
	}
	
	private void writeUTF(DataOutputStream schreiber, String message) throws IOException {
		TafelwischenSecureServer.logDebug("WRITE-UTF = " + message);
		schreiber.writeUTF(message);
	}
	
	public void load(String fileName) throws CorruptServerDataException, IOException {
		Path dataFile = Paths.get(saveFolder, fileName);
		if ( !Files.exists(dataFile)) {
			key = new AssymetrischPaar();
			ServerUser.removeAllUsers();
			ServerMessage.removeAllMessages();
			save();
			return;
		}
		
		try (DataInputStream leser = new DataInputStream(new FileInputStream(dataFile.toString()))) {
			
			String start = readUTF(leser);
			if (SAVE_START.equals(start) || (SAVE_START_ANFANG + 4).equals(start)) {
				
				String keyString = readUTF(leser);
				this.key = new AssymetrischPaar(keyString);
				
				ServerUser.removeAllUsers();
				int sieze = readInt(leser);
				for (int runde = 0; runde < sieze; runde ++ ) {
					ServerUser newUsewr = ServerUser.createFromDataStream(leser);
					TafelwischenSecureServer.logDebug(newUsewr.toString());
				}
				
				ServerMessage.removeAllMessages();
				long msgCounter = readLong(leser);
				ServerMessage.setCounter(msgCounter);
				sieze = readInt(leser);
				for (int runde = 0; runde < sieze; runde ++ ) {
					ServerMessage newMessage = ServerMessage.createFromDataStream(leser);
					TafelwischenSecureServer.logDebug(newMessage.toString());
				}
				
				String ende = readUTF(leser);
				if ( !SAVE_END.equals(ende)) {
					throw new CorruptServerDataException("magic Finish");
				}
				
			} else {
				throw new CorruptServerDataException("magic start");
			}
		} catch (RuntimeException | IOException e) {
			e.printStackTrace();
			throw new CorruptServerDataException(e.toString());
		}
	}
	
	public void load() throws CorruptServerDataException, IOException {
		load("server.data");
	}
	
	private int readInt(DataInputStream leser) throws IOException {
		int gelesen = leser.readInt();
		TafelwischenSecureServer.logDebug("READ-INT = " + gelesen);
		return gelesen;
	}
	
	private long readLong(DataInputStream leser) throws IOException {
		long gelesen = leser.readLong();
		TafelwischenSecureServer.logDebug("READ-LONG = " + gelesen);
		return gelesen;
	}
	
	private String readUTF(DataInputStream leser) throws IOException {
		String gelesen = leser.readUTF();
		TafelwischenSecureServer.logDebug("READ-UTF = " + gelesen);
		return gelesen;
	}
	
	public void reset() throws IOException {
		key = new AssymetrischPaar();
		ServerUser.removeAllUsers();
		ServerMessage.removeAllMessages();
		save();
	}
	
	public void resetmessages() {
		ServerMessage.removeAllMessages();
	}
	
}
