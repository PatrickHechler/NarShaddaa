package de.tafelwischenSecure.benutzer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.Schnittstelle;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.exceptions.UserException;
import de.tafelwischenSecure.exceptions.WrongPasswortException;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

class BenutzerLiveTest {
	
	private static final String USERNAME = "Patrick_Hechler";
	private static final long PASSWORT = -1190983749827095235L;
	
	private static final long LONGTOENCRYPT = 12348765936489L;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@BeforeEach
	void setUp() throws Exception {
		Schnittstelle.configServer("localhost", Constants.DEAFULT_PORT);
	}
	
	
	@Test
	void testLiveServerCommands() throws IOException, UserException, WrongPasswortException {
		int serverVersion = Schnittstelle.getServerVersion();
		assertEquals(5, serverVersion);
		try {
			UserErgebnis erstellen = Benutzer.erstellen(USERNAME);
			String errMsg = "Please change the JUNIT Test:\r\nprivate static final long PASSWORT = "+erstellen.getUserPassword()+"l;";
			System.err.println(errMsg);
			fail(errMsg);
		}
		catch (UserAlreadyExistsException uae) {
			try {
				Benutzer.anmelden(USERNAME, PASSWORT);
			}
			catch (WrongPasswortException wpe) {
				String errMsg = "PASSWORT in JUNIT Test is wrong, please create a new user!";
				System.err.println(errMsg);
				fail(errMsg);
			}
		}
		assertThrows(WrongPasswortException.class, () -> {
			Benutzer.anmelden(USERNAME, PASSWORT - 12366);
		});
		AssymetrischEigener eigenerKey = Benutzer.anmelden(USERNAME, PASSWORT);
		assertNotNull(eigenerKey);
		
		AssymetrischOffen offenKey = Benutzer.getOffenFromUser(USERNAME);
		assertNotNull(offenKey);
		
		
		byte[] encryptedSeedForUser = offenKey.verschlüsseln(LONGTOENCRYPT);
		long decryptedSeed = eigenerKey.entschlüsselnLong(encryptedSeedForUser);
		
		assertEquals(LONGTOENCRYPT, decryptedSeed);
	}
	
}
