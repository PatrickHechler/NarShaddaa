package de.tafelwischenSecure.benutzer;

import static de.tafelwischenSecure.Constants.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import de.hechler.patrick.hilfZeugs.GlobalScanner;
import de.tafelwischenSecure.Schnittstelle;
import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

class BenutzerALT1 {
	
	public static AssymetrischEigener erstellenUndAnmelden() {
		
		return null;
	}
	
	private static boolean isHexNumber(char hexNumbre) {
		if (hexNumbre >= '0' && hexNumbre <= '9') {
			return true;
		}
		if (hexNumbre >= 'A' && hexNumbre <= 'F') {
			return true;
		}
		if (hexNumbre >= 'a' && hexNumbre <= 'f') {
			return true;
		}
		return false;
	}
	
	private static AssymetrischOffen getVerschl�sselSchl�sselALT() throws IOException {
		Schnittstelle.senden(GET_PUBLIC_KEY_FROM_SERVER);
		try {
			return new AssymetrischOffen(Schnittstelle.empfangen());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	private static AssymetrischEigener anmeldenALT() throws IllegalBlockSizeException, BadPaddingException, IOException {
		String benutzerName;
		GlobalScanner eingabe = GlobalScanner.getInstance();
		AssymetrischOffen verschl�sseler = getVerschl�sselSchl�sselALT();
		do {
			System.out.println("Gebe nun deinen Benutzernamen ein.");
			benutzerName = eingabe.next();
		} while ( !Schnittstelle.isNewAcceptableName(benutzerName));
		do {
			System.out.println("Gebe nun das Passwort ein.");
//			TODO Passworteingabe so machen, das man das getippte nicht sieht.
			long passwort = eingabe.nextLong();
			Schnittstelle.senden(SEND_PW_HASH_TO_SERVER + "'" + verschl�sseler.verschl�sseln(Schnittstelle.generatePwHash(passwort)) + "'");
		} while (Schnittstelle.empfangen().equals("false"));
		try {
			int runde;
			String r�ckgabe = Schnittstelle.empfangen();
			for (runde = 0; runde < r�ckgabe.length(); runde ++ ) {
				if ( !isHexNumber(r�ckgabe.charAt(runde))) {
					return null;
				}
			}
			return new AssymetrischEigener(r�ckgabe);
		} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException e) {
		}
		return null;
	}
	
}

