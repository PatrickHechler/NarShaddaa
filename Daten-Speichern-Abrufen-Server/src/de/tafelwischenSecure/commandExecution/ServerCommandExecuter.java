package de.tafelwischenSecure.commandExecution;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import de.hechler.patrick.hilfZeugs.umwandeln.StringByteConvert;
import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.Rules;
import de.tafelwischenSecure.Server;
import de.tafelwischenSecure.TafelwischenSecureServer;
import de.tafelwischenSecure.exceptions.InvalidAccessException;
import de.tafelwischenSecure.exceptions.InvalidPwHashExeption;
import de.tafelwischenSecure.exceptions.InvalidUsernameExeption;
import de.tafelwischenSecure.exceptions.MessageDoesNotExistException;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.exceptions.UserDoesNotExistsExeption;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;
import de.tafelwischenSecure.serverObjects.message.ServerMessage;
import de.tafelwischenSecure.serverObjects.user.ServerUser;
import de.tafelwischenSecure.serverObjects.user.ServerUserInterface;

public class ServerCommandExecuter implements ServerCommandExecuterInterface {
	
	private Server server;
	
	public ServerCommandExecuter(Server server) {
		this.server = server;
	}
	
	@Override
	public String isAlive() {
		if (server.isWeiterlaufen()) {
			return Constants.TRUE;
		}
		return Constants.FALSE;
	}
	
	@Override
	public String getServerVersion() {
		return TafelwischenSecureServer.VERSION + "";
	}
	
	@Override
	public String getPublicKey() {
		return server.getPublicKey().toString();
	}
	
	@Override
	public String getPublicKeyFromUser(String username) {
		ServerUser user;
		try {
			user = ServerUser.getUserByname(username);
		} catch (UserDoesNotExistsExeption e) {
			return Constants.FALSE;
		}
		return user.getOffenenKey().toString();
	}
	
	@Override
	public String existsUser(String userName) {
		if (ServerUser.exists(userName)) {
			return Constants.TRUE;
		}
		return Constants.FALSE;
	}
	
	@Override
	public String createUser(String userName, boolean isEncryptedSending) {
		if (isEncryptedSending == false) {
			return Constants.FALSE;
		}
		
		ServerUserInterface user;
		long passwort = new Random().nextLong();
		
		try {
			user = new ServerUser(passwort, userName);
		} catch (UserAlreadyExistsException | InvalidUsernameExeption e) {
			return Constants.FALSE;
		}
		return passwort + Constants.COMMAND_SPLITTER + user.getEncryptedEigenenKey();
	}
	
	@Override
	public String createUserWithPwAndKey(String username, String pwHash, String encryptedEigener, String offener, boolean isEncryptedSending) {
		if (isEncryptedSending == false) {
			return Constants.FALSE;
		}
		AssymetrischOffen offenerKey;
		try {
			offenerKey = new AssymetrischOffen(offener);
		} catch (Exception e) {
			return Constants.FALSE;
		}
		try {
			new ServerUser(pwHash, username, offenerKey, encryptedEigener);
		} catch (UserAlreadyExistsException | InvalidUsernameExeption | InvalidPwHashExeption e) {
			return Constants.FALSE;
		}
		return Constants.TRUE;
	}
	
	@Override
	public String registerUser(List <String> befehle, boolean wasEncryptedSending) {
		if (wasEncryptedSending == false) {
			return Constants.FALSE;
		}
		
		String username = befehle.get(0);
		String pwHash = befehle.get(1);
		ServerUser user;
		
		try {
			user = ServerUser.getUserByname(username);
		} catch (UserDoesNotExistsExeption e) {
			return Constants.FALSE;
		}
		
		if ( !user.isPwHash(pwHash)) {
			return Constants.WRONG_PW;
		}
		
		return user.getEncryptedEigenenKey();
	}
	
	/* nameOfTheSendingUser + pwHash + usernameOfTheDestinationUser + nameOfTheMessage + message */
	public String sendMessageToUser(String sender, String pwHash, String destiny, String title, String inhalt) {
		if ( !ServerUser.exists(destiny)) {
			return Constants.FALSE;
		}
		try {
			ServerUser user = ServerUser.getUserByname(sender);
			if ( !user.isPwHash(pwHash)) {
				return Constants.WRONG_PW;
			}
		} catch (UserDoesNotExistsExeption e) {
			return Constants.FALSE;
		}
		
		new ServerMessage(title, inhalt, sender, destiny);
		return Constants.TRUE;
	}
	
	@Override
	public String hasNewMessages(String username, String pwHash) {
		try {
			if ( !ServerUser.getUserByname(username).isPwHash(pwHash)) {
				return Constants.WRONG_PW;
			}
		} catch (UserDoesNotExistsExeption e) {
			return Constants.FALSE;
		}
		List <String> liste = ServerMessage.getUncheckedMessages(username);
		StringBuilder messageShortTexts = new StringBuilder();
		liste.forEach((dieser) -> {
			messageShortTexts.append(dieser).append(Constants.COMMAND_SPLITTER);
		});
		return liste.size() + Constants.COMMAND_SPLITTER + messageShortTexts.toString();
	}
	
	@Override
	public String getAllMessages(String username, String pwHash) {
		try {
			if ( !ServerUser.getUserByname(username).isPwHash(pwHash)) {
				return Constants.WRONG_PW;
			}
		} catch (UserDoesNotExistsExeption e) {
			return Constants.FALSE;
		}
		List <String> liste = ServerMessage.getAllMessages(username);
		StringBuilder messageShortTexts = new StringBuilder();
		liste.forEach((dieser) -> {
			messageShortTexts.append(dieser).append(Constants.COMMAND_SPLITTER);
		});
		return liste.size() + Constants.COMMAND_SPLITTER + messageShortTexts.toString();
	}
	
	@Override
	/* username + COMMAND_SPLITTER + messageID + COMMAND_SPLITTER + pwHash
	 * empfangen:
	 * FALSE, if the message does not exist or something other was wrong
	 * otherwise:
	 * the message:
	 * The Time, when the server got the message
	 * The username of the user, who send the message
	 * The title of the message
	 * text of the message */
	public String getMessage(List <String> befehle) {
		ServerUser user;
		ServerMessage message;
		String username = befehle.get(0);
		long msgId;
		try {
			msgId = Long.parseLong(befehle.get(1));
		} catch (NumberFormatException e) {
			return Constants.FALSE;
		}
		String pwHash = befehle.get(2);
		try {
			user = ServerUser.getUserByname(username);
			if ( !user.isPwHash(pwHash)) {
				return Constants.WRONG_PW;
			}
		} catch (UserDoesNotExistsExeption e) {
			return Constants.FALSE;
		}
		try {
			message = ServerMessage.getMessagebyID(msgId, username);
		} catch (MessageDoesNotExistException | InvalidAccessException e) {
			return Constants.FALSE;
		}
		String rückgabe = message.getTime() + Constants.COMMAND_SPLITTER + message.getSendFrom() + Constants.COMMAND_SPLITTER + message.getTitle()
				+ Constants.COMMAND_SPLITTER + message.getInhalt();
		return rückgabe;
	}
	
}
