package de.tafelwischenSecure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;

import de.hechler.patrick.hilfZeugs.GlobalScanner;
import de.tafelwischenSecure.exception.CorruptServerDataException;
import de.tafelwischenSecure.serverObjects.ServerObjectInterface;
import de.tafelwischenSecure.serverObjects.message.ServerMessage;
import de.tafelwischenSecure.serverObjects.user.ServerUser;

public class TafelwischenSecureServer {
	
	public static final int VERSION = 3;
	private String saveFolder;
	private int port;
	private Server server;
	private static boolean debuglog;
	
	public TafelwischenSecureServer() {
	}
	
	public static void main(String[] args) throws IOException {
		TafelwischenSecureServer programm;
		programm = new TafelwischenSecureServer();
		try {
			programm.configure(args);
			programm.startUp();
			help();
			String filename;
			GlobalScanner eingabe = GlobalScanner.getInstance();
			while (true) {
				String adminCmd = eingabe.next();
				switch (adminCmd) {
				case Constants.HELP:
					help();
					break;
				
				case Constants.ADM_EXIT:
					programm.stop();
					return;
				
				case Constants.ADM_SAVE:
					programm.save();
					break;
				
				case Constants.ADM_RESET:
					programm.reset();
					break;
				
				case Constants.ADM_RESETMESSAGES:
					programm.resetmessages();
					break;
				
				case Constants.ADM_BACKUP:
					filename = eingabe.next();
					programm.backup(filename);
					break;
				
				case Constants.ADM_RESTORE:
					filename = eingabe.next();
					programm.restore(filename);
					break;
				
				case Constants.ADM_LISTUSERS:
					programm.listusers();
					break;
				
				case Constants.ADM_LISTMESSAGES:
					programm.listmessages();
					break;
				
				
				default:
					help();
					break;
				}
				
				
			}
			
		} catch (CorruptServerDataException csde) {
			csde.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		programm.stop();
	}
	
	private void listusers() {
		Collection <? extends ServerObjectInterface> users = ServerUser.getUsers();
		show(users);
	}
	
	private void show(Collection <? extends ServerObjectInterface> serverObjects) {
		for (ServerObjectInterface serverObject : serverObjects) {
			serverObject.print();
		}
	}
	
	private void listmessages() {
		Collection <? extends ServerObjectInterface> mesages = ServerMessage.getMessages();
		show(mesages);
	}
	
	private void restore(String fileName) throws CorruptServerDataException, IOException {
		String fullName = "server-" + fileName + ".data";
		if ( !Files.exists(Paths.get(saveFolder, fullName))) {
			System.err.println("Das backup " + fileName + " konnte nicht gefunden werden.");
			return;
		}
		server.load(fullName);
		System.out.println("restored the backup: " + fileName);
	}
	
	private void backup(String fileName) throws IOException {
		server.save("server-" + fileName + ".data");
		System.out.println("backup " + fileName + " created");
	}
	
	private void reset() throws IOException {
		server.reset();
		System.out.println("All data have been ternminated");
	}
	
	private void resetmessages() throws IOException {
		server.resetmessages();
		System.out.println("All messages have been removed");
	}
	
	private void save() throws IOException {
		server.save();
		System.out.println("save finished");
	}
	
	private static void help() {
		System.out.println("ADMINCOMMANDS");
		System.out.println("  enter '" + Constants.HELP + "' to show this text");
		System.out.println("  enter '" + Constants.ADM_EXIT + "' to stop the server");
		System.out.println("  enter '" + Constants.ADM_SAVE + "' to save the current server data");
		System.out.println("  enter '" + Constants.ADM_BACKUP + " <name>' to save the current server data in the backupFile <name>");
		System.out.println("  enter '" + Constants.ADM_RESTORE + " <name>' to load the current state from the backupFile <name>");
		System.out.println("  enter '" + Constants.ADM_RESET + "' rests the server to the deafult stand.");
		System.out.println("  enter '" + Constants.ADM_RESETMESSAGES + "' removes all messages.");
		System.out.println("  enter '" + Constants.ADM_LISTUSERS + "' outputs the list of user data.");
		System.out.println("  enter '" + Constants.ADM_LISTMESSAGES + "' outputs the list of messages.");
	}
	
	private void startUp() throws IOException, CorruptServerDataException {
		System.out.println("Starte: " + new Date());
		server = new Server(port, saveFolder);
		server.load();
		server.startBackground();
	}
	
	private void stop() throws IOException {
		System.out.println("Stoppe: " + new Date());
		server.stop();
	}
	
	private void configure(String[] args) throws IOException {
		saveFolder = "./data";
		port = Constants.DEAFULT_PORT;
		debuglog = true;
		System.out.println("TafelwischenSecureServer V" + VERSION);
		System.out.println("Config:");
		System.out.println("   port        = " + port);
		System.out.println("   save Folder = " + new File(saveFolder).getAbsolutePath());
		System.out.println("   debuglog    = " + debuglog);
	}
	
	public static void logDebug(String message) {
		if (debuglog) {
			if (message.length() <= 172) {
				System.out.println("[DBG] " + message);
			} else {
				System.out.println("[DBG] " + message.substring(0, 169) + "..."); /* Sollte das sein: … */
			}
		}
	}
	
	public static boolean isDebuglog() {
		return debuglog;
	}
	
	public static void setDebuglog(boolean debuglog) {
		TafelwischenSecureServer.debuglog = debuglog;
	}
	
}
