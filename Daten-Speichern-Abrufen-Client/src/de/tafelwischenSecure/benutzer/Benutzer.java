package de.tafelwischenSecure.benutzer;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.GlobalScanner;
import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.bearbeiten.Byteweise�bertragen;
import de.hechler.patrick.hilfZeugs.byteweise�bertragen.Standard.lesen.ArrayLesen;
import de.hechler.patrick.hilfZeugs.byteweise�bertragen.verschl�sselt.lesen.Verschl�sseltLesen;
import de.hechler.patrick.hilfZeugs.umwandeln.StringByteConvert;
import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.Rules;
import de.tafelwischenSecure.Schnittstelle;
import de.tafelwischenSecure.exceptions.InvalidUsernameExeption;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.exceptions.UserDoesNotExistsExeption;
import de.tafelwischenSecure.exceptions.WrongPasswortException;
import de.tafelwischenSecure.message.Message;
import de.tafelwischenSecure.message.MessageInterface;
import de.tafelwischenSecure.message.ShortMessage;
import de.tafelwischenSecure.message.ShortMessageInterface;
import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public class Benutzer {
	
	public static boolean sendMessage(String username, String pwHash, String destinyUsername, String title, String inhalt)
			throws IOException, UserDoesNotExistsExeption {
		String empfangen;
		empfangen = Schnittstelle.senden(Constants.USER_EXISTS + username);
		if (Constants.FALSE.equals(empfangen)) {
			throw new UserDoesNotExistsExeption(username);
		}
		empfangen = Schnittstelle.senden(Constants.USER_EXISTS + destinyUsername);
		if (Constants.FALSE.equals(empfangen)) {
			throw new UserDoesNotExistsExeption(username);
		}
		String senden = Constants.SEND_MESSAGE + username + Constants.COMMAND_SPLITTER + pwHash + Constants.COMMAND_SPLITTER + destinyUsername
				+ Constants.COMMAND_SPLITTER + title + Constants.COMMAND_SPLITTER + inhalt;
		
//		TODO verschl�sseln
		
		empfangen = Schnittstelle.verschl�sseltSenden(senden);
		if (Constants.TRUE.equals(empfangen)) {
			return true;
		}
		return false;
	}
	
	public static ShortMessageInterface[] hasNewMessages(String username, String pwHash) throws IOException, UserDoesNotExistsExeption, WrongPasswortException {
		String empfangen;
		empfangen = Schnittstelle.senden(Constants.USER_EXISTS + username);
		if (Constants.FALSE.equals(empfangen)) {
			throw new UserDoesNotExistsExeption(username);
		}
		empfangen = Schnittstelle.verschl�sseltSenden(Constants.HAS_HEW_MESSAGES + username + Constants.COMMAND_SPLITTER + pwHash);
		if (Constants.WRONG_PW.equals(empfangen)) {
			throw new WrongPasswortException();
		}
		if (Constants.FALSE.equals(empfangen)) {
			return new ShortMessageInterface[0];
		}
		String[] shrtMsgs = empfangen.split(Constants.COMMAND_SPLITTER);
		ShortMessageInterface[] r�ckgabe = new ShortMessageInterface[Integer.parseInt(shrtMsgs[0])];
		int runde;
//		messageID + messageTitle + sendFrom + sendTime 
		for (runde = 0; runde < r�ckgabe.length; runde ++ ) {
			long msgId;
			try {
				msgId = Long.parseLong(shrtMsgs[1 + (runde * 4)]);
			} catch (NumberFormatException e) {
				msgId = -1;
			}
			r�ckgabe[runde] = new ShortMessage(shrtMsgs[3 + (runde * 4)], shrtMsgs[4 + (runde * 4)], shrtMsgs[2 + (runde * 4)], msgId);
		}
		return r�ckgabe;
	}
	
	/* The Time, when the server got the message
	 * + The username of the user, who send the message
	 * + The title of the message
	 * + text of the message */
	public static MessageInterface getMessage(String username, String pwHash, long id) throws UserDoesNotExistsExeption, IOException {
		String empfangen;
		empfangen = Schnittstelle.senden(Constants.USER_EXISTS + username);
		if (Constants.FALSE.equals(empfangen)) {
			throw new UserDoesNotExistsExeption(username);
		}
		empfangen = Schnittstelle.senden(Constants.GET_MESSAGE + username + Constants.COMMAND_SPLITTER + id + Constants.COMMAND_SPLITTER + pwHash);
		if (Constants.FALSE.equals(empfangen)) {
			return null;
		}
		String[] msg = empfangen.split(Constants.COMMAND_SPLITTER);
		return new Message(msg[1], msg[0], msg[2], msg[3]);
	}
	
	public static AssymetrischOffen getOffenFromUser(String username) throws IOException, UserDoesNotExistsExeption {
		AssymetrischOffen r�ckgabe;
		String empf = Schnittstelle.senden(Constants.GET_PUBLIC_KEY_FROM_USER + username);
		if (Constants.FALSE.equals(empf)) {
			throw new UserDoesNotExistsExeption(username);
		}
		r�ckgabe = new AssymetrischOffen(empf);
		return r�ckgabe;
	}
	
	/**
	 * with .getName() on the Key, you get the name of the user
	 * 
	 * @return
	 *         the key with the name set to the username.
	 * 
	 * @throws InvalidUsernameExeption
	 * 
	 * @throws IOException
	 * @throws UserDoesNotExistsExeption
	 * @throws WrongPasswortException
	 */
	public static AssymetrischEigener anmelden(String username, long passwort)
			throws InvalidUsernameExeption, IOException, UserDoesNotExistsExeption, WrongPasswortException {
		Schnittstelle.checkServerConnection();
		if ( !Rules.isAcceptableName(username)) {
			throw new InvalidUsernameExeption(username);
		}
		
		String empfangen;
		empfangen = Schnittstelle.senden(Constants.USER_EXISTS + username);
		if (Constants.FALSE.equals(empfangen)) {
			throw new UserDoesNotExistsExeption(username);
		}
		if ( !Constants.TRUE.equals(empfangen)) {
			if (Constants.UNKNOWN_COMMAND.equals(empfangen)) {
				throw new RuntimeException("Der Server sollte das Commando '" + Constants.USER_EXISTS + username + "' kennen." + System.lineSeparator()
						+ "Allerdings wurde das Kommando f�r einen unbekannten befehl zur�ckgeschickt (" + empfangen + ").");
			}
			throw new RuntimeException("By cecking the existence of the user " + username + " the server answered wrongly.");
		}
		
		empfangen = Schnittstelle.verschl�sseltSenden(Constants.REGISTER_USER + username + Constants.COMMAND_SPLITTER + Rules.generatePwHash(passwort));
		if (Constants.FALSE.equals(empfangen) || Constants.WRONG_PW.equals(empfangen)) {
			throw new WrongPasswortException();
		}
		byte[] bytes = ZahlenUmwandeln.hexZuByteArray(empfangen);
		
		Verschl�sseltLesen leser = new Verschl�sseltLesen(new ArrayLesen(bytes), passwort);
		String eigenKeyStr = StringByteConvert.bytesZuString(leser.getBytes());
		AssymetrischEigener privateUserKey = new AssymetrischEigener(eigenKeyStr);
		return privateUserKey;
	}
	
	public static UserErgebnis anmelden(String username) throws InvalidUsernameExeption, IOException, UserDoesNotExistsExeption, WrongPasswortException {
		int runde;
		for (runde = 0; runde < 3; runde ++ ) {
			long passwort = pwLongEingabe();
			try {
				return new UserErgebnis(anmelden(username, passwort), passwort);
			} catch (WrongPasswortException e) {
				System.out.println("Das war das falsche Passwort.");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
				}
			}
		}
		throw new WrongPasswortException();
	}
	
	private static long pwLongEingabe() {
		GlobalScanner benEingabe = GlobalScanner.getInstance();
		do {
			System.out.println("Gebe nun das Passwort ein:");
			String eingabe = benEingabe.next();
			try {
				return Long.parseLong(eingabe);
			} catch (NumberFormatException e) {
				System.out.println(eingabe + " ist kein g�ltiges Passwort");
				continue;
			}
		} while (true);
	}
	
	/**
	 * with .getName() on the Key, you get the name of the user
	 * 
	 * @return
	 *         the key with the name set to the username.
	 * 
	 * @throws IOException
	 */
	public static UserErgebnis erstellen() throws IOException {
		String username;
		GlobalScanner benEingabe = GlobalScanner.getInstance();
		
		while (true) {
			System.out.println("Benutzer erstellen:");
			System.out.println("Gebe den Benutzernamen ein.");
			System.out.println("(a-z, A-Z und '_')");
			username = benEingabe.next();
			if ( !Rules.isAcceptableName(username)) {
				System.out.println(username + " ist kein akzeptabler Benutzername.");
				continue;
			}
			if ( !Schnittstelle.isNewAcceptableName(username)) {
				System.out.println(username + " existiert bereits.");
				continue;
			}
			try {
				return erstellen(username);
			} catch (UserAlreadyExistsException uaee) {
				System.out.println("Den Benutzernamen " + uaee.getUsername() + " gibt es bereits. Suche dir einen anderen aus.");
			} catch (InvalidUsernameExeption iue) {
				System.out.println(iue.getUsername() + " ist ein ung�ltiger Benutzername.");
			}
		}
	}
	
	/**
	 * senden: <br>
	 * username + sending <br>
	 * empfangen: <br>
	 * hexByteArray, wich should be encrypted with the key.
	 * 
	 * @return the key with the name set to the username.
	 * 
	 * @throws IOException
	 * @throws UserAlreadyExistsException
	 * @throws InvalidUsernameExeption
	 */
	public static UserErgebnis erstellen(String username) throws IOException, UserAlreadyExistsException, InvalidUsernameExeption {
//		Grundlegende Pr�fungen.
		{
			Schnittstelle.checkServerConnection();
			if ( !Rules.isAcceptableName(username)) {
				throw new InvalidUsernameExeption(username);
			}
			if ( !Schnittstelle.isNewAcceptableName(username)) {
				throw new UserAlreadyExistsException(username);
			}
		}
		String empfangen = Schnittstelle.verschl�sseltSenden(Constants.SEND_NEW_USER_TO_SERVER + username);
		if (Constants.FALSE.equals(empfangen)) {
			try {
//				Warten, um dem Server Zeit zu geben, damit dieser seine Daten aktualisieren kann.
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
//			pr�fen, ob der Server online ist
			String serverExi = Schnittstelle.senden(Constants.IS_ALIVE);
			if (Constants.FALSE.contentEquals(serverExi)) {
				throw new RuntimeException("There is no server");
			}
//			Erneut pr�fen, ob der Benutzername bereits vergeben ist und einen Dementsprechenden Fehler werfen.
			String userExi = Schnittstelle.senden(Constants.USER_EXISTS + username);
			if (Constants.TRUE.equals(userExi)) {
				throw new UserAlreadyExistsException(username);
			}
			throw new RuntimeException("I could not create a user with the name: " + userExi + ", but the server says, that the user does not exists.");
		}
		String[] userThings = empfangen.split(Constants.COMMAND_SPLITTER);
		long password = Long.parseLong(userThings[0]);
		System.out.println("Dein Passwort ist: '" + password + "'.");
		System.out.println("Das ist sehr wichtig! Also merk es dir sehr gut!");
		AssymetrischEigener userKey;
		
		String encPrivKey = userThings[1];
		String privKeyString = Byteweise�bertragen.Verschl�sselt.entschl�sselnString(ZahlenUmwandeln.hexZuByteArray(encPrivKey), password);
		userKey = new AssymetrischEigener(privKeyString, username);
		return new UserErgebnis(userKey, password);
	}
	
}
