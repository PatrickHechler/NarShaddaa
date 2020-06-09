package de.tafelwischenSecure;

import java.io.IOException;

import de.tafelwischenSecure.komm.Kommunikation;
import de.tafelwischenSecure.komm.KommunikationInterface;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;
import de.tafelwischenSecure.secure.ClientSidedSecurityManager;
import de.tafelwischenSecure.secure.ClientSidedSecurityManagerInterface;
import de.tafelwischenSecure.secure.VerschlüsselteServerNachricht;

public class Schnittstelle {
	
	private static ClientSidedSecurityManagerInterface secure = new ClientSidedSecurityManager();
	private static KommunikationInterface komm;
	
	public static void configServer(String name, int port) {
		komm = new Kommunikation(name, port);
	}
	
	public static void setDefaultConfig() {
		configServer(Constants.DEAFULT_HOST, Constants.DEAFULT_PORT);
	}
	
	public static void setKommunikation(KommunikationInterface newKomm) {
		komm = newKomm;
	}
	
	public static void setSecurityManager(ClientSidedSecurityManagerInterface newSecure) {
		secure = newSecure;
	}
	
	public static void setDefaultSecurityManager() {
		setSecurityManager(new ClientSidedSecurityManager());
	}
	
	private static void checkServerConfig() throws IOException {
		if (komm == null) {
			throw new IOException("No Server configured");
		}
	}
	
	/**
	 * sends a String to the Server.
	 * 
	 * @throws IOException
	 *             if there are problems with the server connection.
	 * 			
	 * @param senden
	 */
	public static String senden(String senden) throws IOException {
		checkServerConfig();
		return komm.sendMessage(senden);
	}
	
	public static String verschlüsseltSenden(String verschlüsselnUndSenden) throws IOException {
		checkServerConfig();
		checkSecurityManager();
		String serverKeyString = senden(Constants.GET_PUBLIC_KEY_FROM_SERVER);
		AssymetrischOffen serverKey;
		VerschlüsselteServerNachricht message;
		String empfangen;
		serverKey = new AssymetrischOffen(serverKeyString);
		message = secure.encrypt(verschlüsselnUndSenden, serverKey);
		empfangen = senden(
				Constants.ENCRYPTED + Constants.COMMAND_SPLITTER + message.getEncryptedSeed() + Constants.COMMAND_SPLITTER + message.getEncryptedMessage());
		return secure.decrypt(message.getSeed(), empfangen);
	}
	
	private static void checkSecurityManager() {
		if (secure == null) {
			throw new RuntimeException("Es gibt keinen Security Manager");
		}
	}
	
	public static int getServerVersion() throws IOException {
		return Integer.parseInt(senden(Constants.GET_SERVER_VERSION));
	}
	
	public static boolean isNewAcceptableName(String potentialNewName) throws IOException {
		if ( !Rules.isAcceptableName(potentialNewName)) {
			return false;
		}
		String empf = senden(Constants.USER_EXISTS + potentialNewName);
		return (Constants.FALSE.equals(empf));
	}
	
	public static void checkServerConnection() {
		try {
			String empf = senden(Constants.IS_ALIVE);
			if (Constants.TRUE.equals(empf)) {
				return;
			}
			if (Constants.FALSE.equals(empf)) {
				throw new RuntimeException("The server is not there");
			}
			throw new RuntimeException("The server answered wrong");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
