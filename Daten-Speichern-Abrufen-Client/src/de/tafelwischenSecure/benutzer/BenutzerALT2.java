package de.tafelwischenSecure.benutzer;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import de.hechler.patrick.hilfZeugs.GlobalScanner;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.ArrayLesen;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben.ArraySchreiben;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.lesen.VerschlüsseltLesen;
import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.Schnittstelle;
import de.tafelwischenSecure.rsa.schlüssel.AssymetrischPaar;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

class BenutzerALT2 {
	
	/**
	 * Lässt den Benutzer sich bei einem Bereits existierendem Benutzerkonto anmelden.
	 * 
	 * @return
	 *         Den Privaten Schlüssel des angemeldeten Benutzers. <br>
	 *         null, falls es Fehler beim anmelden gab.
	 * @throws IOException 
	 */
	public AssymetrischEigener anmelden() throws IOException {
		AssymetrischOffen serverKomm;
		int kommNummer;
		String benutzername;
		GlobalScanner eingabe = GlobalScanner.getInstance();
		
//		get username
		do {
			System.out.println("Gebe nun den Benutzernamen ein.");
			benutzername = eingabe.next();
			if (existsName(benutzername)) {
				break;
			}
			System.out.println("Der Benutzername '" + benutzername + "' exsistiert nicht.");
		} while (true);
		
//		get PublicKey from the server
		{
			Object[] obj = getKeyFromServer(benutzername);
			if (obj == null) {
				return null;
			}
			kommNummer = (int) obj[0];
			serverKomm = (AssymetrischOffen) obj[1];
		}
		
//		check access to the user
		do {
			System.out.println("Gebe nun das Passwort ein.");
			if (eingabe.hasNextLong()) {
//				TODO Passworteingabe unsichtbar machen
				long passwort = eingabe.nextLong();
				byte[] bytes = ZahlenUmwandeln.hexZuByteArray(Schnittstelle.generatePwHash(passwort));
				String verschlüsselt = kommNummer + Constants.COMMAND_SPLITTER;
				try {
					bytes = serverKomm.verschlüsseln(bytes);
				} catch (IllegalBlockSizeException | BadPaddingException e) {
					return null;
				}
				verschlüsselt += ZahlenUmwandeln.zuHex(bytes);
				Schnittstelle.senden(verschlüsselt);
				String key = Schnittstelle.empfangen();
				if (key.equals(Constants.FALSE)) {
					System.out.println("Das ist das falsche Passwort.");
				} else {
					bytes = ZahlenUmwandeln.hexZuByteArray(key);
					VerschlüsseltLesen leser;
					ArraySchreiben schreiber;
					leser = new VerschlüsseltLesen(new ArrayLesen(bytes), passwort);
					int runde;
					schreiber = new ArraySchreiben(bytes.length);
					int[] zahlen;
					for (runde = 0; runde < bytes.length; runde ++ ) {
						try {
							schreiber.schreibeByte(leser.getNächstesByte());
						} catch (Exception e) {
							return null;
						}
					}
					zahlen = schreiber.getArray();
					for (runde = 0; runde < zahlen.length; runde ++ ) {
						bytes[runde] = (byte) zahlen[runde];
					}
					
//					Gebe den Privaten Schlüssel des Benutzers zurück
					{
						try {
							return new AssymetrischEigener(ZahlenUmwandeln.zuHex(bytes));
						} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException e) {
							return null;
						}
					}
				}
			} else {
				System.out.println("Dies ist kein gültiges Passwort");
			}
		} while (true);
	}
	
	private Object[] getKeyFromServer(String benutzerName) {
		System.out.println(Constants.GET_PUBLIC_KEY_FROM_SERVER + benutzerName);
		try {
			String[] args = Schnittstelle.empfangen().split(Constants.COMMAND_SPLITTER);
			return new Object[] {Integer.parseInt(args[0]), new AssymetrischOffen(args[1]) };
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException e) {
			return null;
		}
	}
	
	private boolean existsName(String benutzername) throws IOException {
		Schnittstelle.senden(Constants.USER_EXISTS + benutzername);
		return Schnittstelle.empfangen().equals(Constants.TRUE);
	}
	
	/**
	 * Erstellt ein Benutzerkonto.
	 * 
	 * @return
	 *         den Privaten Schlüssel, des soeben generierten Benutzers
	 *         null, falls etwas zu schief läuft.
	 * 		
	 * @throws IOException
	 */
	public AssymetrischEigener erstellenUndAnmelden() throws IOException {
		String userName;
		GlobalScanner eingabe = GlobalScanner.getInstance();
		AssymetrischOffen serverKomm;
		AssymetrischPaar serverEmpf;
		int empfZahl;
		int kommZahl;
		try {
			serverEmpf = new AssymetrischPaar();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e1) {
			try {
				serverEmpf = new AssymetrischPaar();
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				System.out.println("Irgendetwas ist schiefgelaufen.");
				System.out.println("Soll ich es erneut probieren? (J/n)");
				char entscheidung = eingabe.next().charAt(0);
				if (entscheidung == 'n') {
					return null;
				}
				return erstellenUndAnmelden();
			}
		}
		
//		sende den public key zum Server
		{
			Schnittstelle.senden(Constants.SEND_PUBLIC_KEY_TO_SERVER + serverEmpf.getOffenAlsString());
			empfZahl = Integer.parseInt(Schnittstelle.empfangen());
		}
		do {
			System.out.println("Gebe nun deinen Name ein.");
			System.out.println("Nur Buchstaben, Zahlen und '_'");
			userName = eingabe.next();
			if (Schnittstelle.isAcceptableName(userName)) {
				System.out.println(userName + " ist kein Akzeptabler Benutzername.");
			} else if (Schnittstelle.isNewAcceptableName(userName)) {
				System.out.println(userName + " existiert bereits");
			} else {
				System.out.println("Dein Benutzername ist: " + userName);
				Schnittstelle.senden(Constants.SEND_NEW_USER_TO_SERVER + userName);
				String empfangen = Schnittstelle.empfangen();
				if (empfangen.equals(Constants.FALSE)) {
					System.out.println("Irgendetwas ist beim Passwortgenerieren schiefgelaufen.");
					System.out.println("Probiere es doch gleich nochmal (vielleicht mit einem anderem Benutzernamen)");
				} else {
					String[] emp = empfangen.split(Constants.COMMAND_SPLITTER);
					System.out.println("Dein Passwort ist: '" + emp[0] + "'.");
					System.out.println("Merke es dir gut. Wenn du das vergisst komst du nie wieder daran.");
					try {
						return new AssymetrischEigener(emp[1]);
					} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException e) {
						System.out.println("Irgendetwas ist mit deinem Schlüssel schiefgelaufen.");
						System.out.println("Probieren doch einfach mal dich anzumelden.");
						return anmelden();
					}
				}
			}
		} while (true);
	}
	
}
