package de.tafelwischenSecure;

import java.awt.Window;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import de.hechler.patrick.hilfZeugs.GlobalScanner;
import de.tafelwischenSecure.benutzer.Benutzer;
import de.tafelwischenSecure.benutzer.UserErgebnis;
import de.tafelwischenSecure.exceptions.InvalidUsernameExeption;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.exceptions.UserDoesNotExistsExeption;
import de.tafelwischenSecure.exceptions.WrongPasswortException;
import de.tafelwischenSecure.message.MessageInterface;
import de.tafelwischenSecure.message.ShortMessageInterface;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;

public class TafelwischenSecureClient {
	
	public static final int VERSION = 0;
	public static final int EXPECTED_SERVER_VERSION = 3;
	public static final int SERVER_VERSION;
	private static long passwort;
	private static String username;
	private static AssymetrischEigener eigenerKey;
	
	static {
		Schnittstelle.setDefaultConfig();
		Schnittstelle.configServer("localhost", Constants.DEAFULT_PORT);
		int zw;
		try {
			zw = Schnittstelle.getServerVersion();
		} catch (IOException e) {
			zw = -1;
		}
		SERVER_VERSION = zw;
	}
	
	public static void main(String[] args) {
		System.out.println("TafelwischenSecure Client V" + VERSION + " S:" + SERVER_VERSION);
		if (SERVER_VERSION != EXPECTED_SERVER_VERSION) {
			System.out.println("Es wurde eine andere Sever-version erwartet, möglicherweise funktionieren einige sachen falsch.");
		}
		GlobalScanner ben = GlobalScanner.getInstance();
		anmeldenOderRegistrieren();
		intro();
		String eingabe;
		while (true) {
			eingabe = ben.next();
			switch (eingabe.toLowerCase()) {
			case Constants.HELP:
				help();
				break;
			case Constants.USR_CHECK:
				check();
				break;
			case Constants.USR_GET_MSG_LO:
				String zw = ben.next();
				try {
					long id = Long.parseLong(zw);
					getMsg(id);
				} catch (NumberFormatException e) {
					System.out.println("Daas ist keine gültige Message-Id!");
				}
				break;
			case Constants.USR_LIST:
				list();
				break;
			case Constants.USR_QUIT:
				System.out.println("Tschüss");
				return;
			case Constants.USR_SEND_MSG_LO:
				sendMsg();
				break;
			default:
				help();
			}
		}
	}
	
	private static void sendMsg() {
		GlobalScanner ben = GlobalScanner.getInstance();
		String empfänger;
		String titel;
		StringBuilder inhalt;
		System.out.println("Gebe nun den Empfänger ein.");
		empfänger = ben.next();
		try {
			if ( !Benutzer.exists(empfänger)) {
				System.out.println(empfänger + " existiert nicht");
				return;
			}
			ben.nextLine();
			System.out.println("Gebe nun den Betreff ein.");
			titel = ben.nextLine();
			System.out.println("Gebe nun die Nachricht ein.");
			System.out.println("Zum beenden 'FERTIG' eingeben.");
			String zusatz;
			inhalt = new StringBuilder();
			while (true) {
				zusatz = ben.nextLine();
				if (zusatz.trim().equals("FERTIG")) {
					break;
				}
				inhalt.append(zusatz).append("\r\n");
			}
			boolean ok = Benutzer.sendMessage(username, Rules.generatePwHash(passwort), empfänger, titel, inhalt.toString());
			if (ok) {
				System.out.println("Die Nachricht "+titel+" wurde erfolgreich an "+empfänger+" geschickt.");
			}
			else {
				System.out.println("Die Nachricht konnte nicht versendet wetrden  :-(");
			}
		} catch (IOException | UserDoesNotExistsExeption e) {
			System.out.println("Es gab probleme mit der Server kommunikation.");
		}
	}
	
	private static void getMsg(long id) {
		try {
			MessageInterface message = Benutzer.getMessage(username, Rules.generatePwHash(passwort), id);
			if (message == null) {
				System.out.println("Nachricht konnte nicht empfangen werden.");
				return;
			}
			System.out.println("Gesendet von: " + message.getSendFrom());
			System.out.println("          am: " + message.getTime());
			System.out.println("     Betreff: " + message.getTitle());
			System.out.println("----------------------------------------------------------");
			System.out.println(message.getInhalt());
			System.out.println("----------------------------------------------------------");
		} catch (UserDoesNotExistsExeption | IOException e) {
			System.out.println("Es gab probleme mit der Server kommunikation.");
		}
	}
	
	private static void list() {
		try {
			ShortMessageInterface[] msgs = Benutzer.hasNewMessages(username, Rules.generatePwHash(passwort));
			for (ShortMessageInterface diese : msgs) {
				System.out.println("ID:      " + diese.getId());
				System.out.println("VON:     " + diese.getSendFrom());
				System.out.println("AN:      " + diese.getTime());
				System.out.println("BETREFF: " + diese.getTitle());
			}
		} catch (UserDoesNotExistsExeption | WrongPasswortException | IOException e) {
			System.out.println("Es gab probleme mit der Server kommunikation.");
			return;
		}
	}
	
	private static void check() {
		try {
			ShortMessageInterface[] msgs = Benutzer.hasNewMessages(username, Rules.generatePwHash(passwort));
			System.out.println("Du hast " + msgs.length + " neue Nachrichten.");
		} catch (UserDoesNotExistsExeption | WrongPasswortException | IOException e) {
			System.out.println("Es gab probleme mit der Server kommunikation.");
		}
	}
	
	private static void intro() {
		ShortMessageInterface[] msgs;
		try {
			msgs = Benutzer.hasNewMessages(username, Rules.generatePwHash(passwort));
			System.out.println("hallo, " + username + " du hast " + msgs.length + " neue Nachrichten.");
		} catch (UserDoesNotExistsExeption | WrongPasswortException | IOException e) {
			System.out.println("Es gab probleme mit der Server kommunikation.");
		}
		System.out.println("Gib '?' ein, um dir alle Kommandos anzeigen zu lassen.");
	}
	
	private static void help() {
		System.out.println("  " + Constants.HELP + " um dies anzuzeigen");
		System.out.println("  " + Constants.USR_CHECK + " um dir die anzahl an neuen Nachrichten anzuzeigen.");
		System.out.println("  " + Constants.USR_LIST + " um dies anzuzeigen");
		System.out.println("  " + Constants.USR_GET_MSG + " um dies anzuzeigen");
		System.out.println("  " + Constants.USR_SEND_MSG + " um dies anzuzeigen");
		System.out.println("  " + Constants.USR_QUIT + " um dies anzuzeigen");
	}
	
	private static void anmeldenOderRegistrieren() {
		GlobalScanner ben = GlobalScanner.getInstance();
		String eingabe;
		while (true) {
			System.out.println("Hast du bereits ein Benutzerkonto oder willst du ein neues erstellen?");
			System.out.println("'[a]nmelden' oder '[e]rstellen'");
			eingabe = ben.next();
			if (eingabe.equalsIgnoreCase("anmelden") || eingabe.equalsIgnoreCase("a")) {
				anmelden();
				return;
			}
			if (eingabe.equalsIgnoreCase("erstellen") || eingabe.equalsIgnoreCase("e")) {
				erstellen();
				return;
			}
			System.out.println(eingabe + " steht nicht zur auswahl");
		}
	}
	
	private static void anmelden() {
		GlobalScanner ben = GlobalScanner.getInstance();
		String name = null;
		long pw;
		while (true) {
			System.out.println("Wie heißt du?");
			name = ben.next();
			pw = passwortEingabe();
			try {
				AssymetrischEigener user = Benutzer.anmelden(name, pw);
				username = name;
				passwort = pw;
				eigenerKey = user;
				return;
			} catch (InvalidUsernameExeption e) {
				System.out.println(name + " ist kein gültiger Benuttzername.");
			} catch (UserDoesNotExistsExeption e) {
				System.out.println(name + " existiert nicht.");
			} catch (WrongPasswortException e) {
				System.out.println("Das war das Falsche Passwort.");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
				}
			} catch (IOException e) {
				System.out.println("Es git gerade probleme mit der Verbindung zum Server.");
			}
		}
	}
	
	private static void erstellen() {
		GlobalScanner ben = GlobalScanner.getInstance();
		while (true) {
			System.out.println("Wie möchtest du heißen?");
			String name = ben.next();
			try {
				UserErgebnis user = Benutzer.erstellen(name);
				passwort = user.getUserPassword();
				username = user.getUsername();
				eigenerKey = user.getUserKey();
				return;
			} catch (UserAlreadyExistsException e) {
				System.out.println(name + " existiert bereits.");
			} catch (InvalidUsernameExeption e) {
				System.out.println(name + " ist kein gültiger Benutzername, bitte suche dir einen anderen aus.");
			} catch (IOException e) {
				System.out.println("Es git gerade probleme mit der Verbindung zum Server.");
			}
		}
	}
	
	private static long passwortEingabe() {
		while (true) {
			String eingabe = unsichtbareEingabe();
			try {
				return Long.parseLong(eingabe);
			} catch (NumberFormatException fehler) {
				System.out.println("Dies ist kein gültiges Passwort.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignore) {
				}
			}
		}
	}
	
	private static String unsichtbareEingabe() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Gebe nun dein Passwort ein.:");
		JPasswordField pass = new JPasswordField(20);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] {"OK" };
		pass.addActionListener(ae -> {
			JComponent comp = (JComponent) ae.getSource();
			  Window win = SwingUtilities.getWindowAncestor(comp);
			  win.dispose();
	    });
		JOptionPane.showOptionDialog(null, panel, "The title", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		char[] password = pass.getPassword();
		return new String(password);
	}

	
}
