package de.tafelwischenSecure.message;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tafelwischenSecure.Rules;
import de.tafelwischenSecure.Schnittstelle;
import de.tafelwischenSecure.benutzer.Benutzer;
import de.tafelwischenSecure.benutzer.UserErgebnis;
import de.tafelwischenSecure.exceptions.InvalidUsernameExeption;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.exceptions.UserDoesNotExistsExeption;
import de.tafelwischenSecure.exceptions.UserException;
import de.tafelwischenSecure.exceptions.WrongPasswortException;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

class MessageLiveTest {
	
	private static final String USERNAME1 = "JUnit_Alice";
	private static final long PASSWORT1 = -9085505973771263875l;
	private static final String PASSWORT1_HASH = Rules.generatePwHash(PASSWORT1);
	
	private static final String USERNAME2 = "JUnit_Bob";
	private static final long PASSWORT2 = -4066588620648793791l;
	private static final String PASSWORT2_HASH = Rules.generatePwHash(PASSWORT2);
	
	private static final String USERNAME3 = "JUnit_Charly";
	private static final long PASSWORT3 = 6577904893962204887l;
	private static final String PASSWORT3_HASH = Rules.generatePwHash(PASSWORT3);
	

	@BeforeEach
	void setUp() throws Exception {
		Schnittstelle.configServer("localhost", 5869);
	}
	
	
	@Test
	void testLiveServerCommands() throws IOException, UserException, WrongPasswortException {
		int serverVersion = Schnittstelle.getServerVersion();
		assertEquals(3, serverVersion);

		createTestUser(1, USERNAME1, PASSWORT1);
		createTestUser(2, USERNAME2, PASSWORT2);
		createTestUser(3, USERNAME3, PASSWORT3);

		boolean ok;
		ok = Benutzer.sendMessage(USERNAME1, PASSWORT1_HASH, USERNAME2, "Msg1to2A", "Hallo "+USERNAME2+" Viele grüße, "+USERNAME1);
		assertTrue(ok);
		ok = Benutzer.sendMessage(USERNAME1, PASSWORT1_HASH, USERNAME2, "Msg1to2B", "Hallo "+USERNAME2+" Nochmal viele grüße, "+USERNAME1);
		assertTrue(ok);
		ok = Benutzer.sendMessage(USERNAME2, PASSWORT2_HASH, USERNAME3, "Msg2to3", "Hallo "+USERNAME3+" Viele grüße, "+USERNAME2);
		assertTrue(ok);
		ok = Benutzer.sendMessage(USERNAME3, PASSWORT3_HASH, USERNAME2, "Msg3to2", "Hallo "+USERNAME2+" Viele grüße, "+USERNAME3);
		assertTrue(ok);

		ShortMessageInterface[] newMessagesFor2 = Benutzer.hasNewMessages(USERNAME2, PASSWORT2_HASH);
		assertEquals(3,  newMessagesFor2.length);
		
		for (ShortMessageInterface msg:newMessagesFor2) {
			System.out.println(msg);
		}
		
		MessageInterface msg = Benutzer.getMessage(USERNAME2, PASSWORT2_HASH, newMessagesFor2[0].getId());
		assertNotNull(msg);
		
		System.out.println(msg.getInhalt());
		
		newMessagesFor2 = Benutzer.hasNewMessages(USERNAME2, PASSWORT2_HASH);
		assertEquals(2,  newMessagesFor2.length);
		
		
		
	}

	
	
	private void createTestUser(int count, String username, long passwort) throws IOException, InvalidUsernameExeption, UserDoesNotExistsExeption {
		try {
			UserErgebnis erstellen = Benutzer.erstellen(username);
			String errMsg = "Please change the JUNIT Test:\r\nprivate static final long PASSWORT"+count+" = "+erstellen.getUserPassword()+"l;";
			System.err.println(errMsg);
			fail(errMsg);
		}
		catch (UserAlreadyExistsException uae) {
			try {
				Benutzer.anmelden(username, passwort);
			}
			catch (WrongPasswortException wpe) {
				String errMsg = "PASSWORT"+count+" in JUNIT Test is wrong, please create a new user!";
				System.err.println(errMsg);
				fail(errMsg);
			}
		}
	}
	

}
