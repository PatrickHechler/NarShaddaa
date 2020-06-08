package de.tafelwischenSecure.serverObjects.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.bearbeiten.ByteweiseÜbertragen;
import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.Rules;
import de.tafelwischenSecure.exceptions.InvalidPwHashExeption;
import de.tafelwischenSecure.exceptions.InvalidUsernameExeption;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.exceptions.UserException;
import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

class ServerUserTest {
	
	private static String[] userNames = new String[] {"Paul", "Trotal", "Heshcler" };
	
	@BeforeEach
	void setUp() throws Exception {
		ServerUser.removeAllUsers();
		int runde;
		for (runde = 0; runde < userNames.length; runde ++ ) {
			new ServerUser(runde, userNames[runde]);
		}
	}
	
	@AfterEach
	void tearDown() {
		ServerUser.removeAllUsers();
	}
	
	@Test
	void testServerUser() throws UserAlreadyExistsException, InvalidUsernameExeption, InvalidPwHashExeption {
		assertEquals(userNames.length, ServerUser.getUserNames().size());
		assertEquals(userNames.length, ServerUser.getUsers().size());
		String newUser = "Hallo";
		new ServerUser(0, newUser);
		assertEquals(userNames.length + 1, ServerUser.getUserNames().size());
		assertEquals(userNames.length + 1, ServerUser.getUsers().size());
//		assertThrows(InvalidPwHashExeption.class, () -> new ServerUser("", "Nope"));
		assertThrows(InvalidUsernameExeption.class, () -> new ServerUser(99l, ""));
//		new ServerUser("3357afeF3357afeF", "1");
//		assertThrows(InvalidPwHashExeption.class, () -> new ServerUser("6454164fefffuFbc", "a45"));
//		assertThrows(InvalidPwHashExeption.class, () -> new ServerUser("6454164fefffFbc", "a45"));
//		assertThrows(InvalidPwHashExeption.class, () -> new ServerUser("6454164fefffFbc34", "a45"));
//		assertThrows(UserAlreadyExistsException.class, () -> new ServerUser("6454164fefffFFbc", userNames[2]));
//		new ServerUser("6454164fefffFFbc", "Nope");
	}
	
	@Test
	void testGetName() throws UserAlreadyExistsException, InvalidUsernameExeption {
		String name = "Hallo";
		ServerUser serverUser = new ServerUser(8L, name);
		assertEquals(name, serverUser.getName());
		name = "Dies_Sollte_EIN_Akzeptabler_Name_sein_";
		serverUser = new ServerUser(8L, name);
		assertEquals(name, serverUser.getName());
		name = "Dies_Sollte______EIN_Akzeptabler___Name_sein";
		serverUser = new ServerUser(8L, name);
		assertEquals(name, serverUser.getName());
	}
	
	@Test
	void testGetPwHash() throws UserAlreadyExistsException, InvalidUsernameExeption, InvalidPwHashExeption {
		long passwort = 8123L;
		ServerUser serverUser = new ServerUser(passwort, "Name");
		assertEquals(Rules.generatePwHash(passwort), serverUser.getPwHash());
		passwort = 26723846L;
		serverUser = new ServerUser(passwort, "Name2");
		assertEquals(Rules.generatePwHash(passwort), serverUser.getPwHash());
		passwort = 8123L;
		serverUser = new ServerUser(passwort, "Name_3");
		assertEquals(Rules.generatePwHash(passwort), serverUser.getPwHash());
		passwort = 8123346326L;
		serverUser = new ServerUser(passwort, "Name3");
		assertEquals(Rules.generatePwHash(passwort), serverUser.getPwHash());
		passwort = 8123346326L;
//		serverUser = new ServerUser(Rules.generatePwHash(passwort), "Name34573");
//		assertEquals(Rules.generatePwHash(passwort), serverUser.getPwHash());
//		passwort = 2L;
//		serverUser = new ServerUser(Rules.generatePwHash(passwort), "Nam26e3");
//		assertEquals(Rules.generatePwHash(passwort), serverUser.getPwHash());
//		passwort = 812334266L;
//		serverUser = new ServerUser(Rules.generatePwHash(passwort), "N26ame3");
//		assertEquals(Rules.generatePwHash(passwort), serverUser.getPwHash());
//		passwort = -2L;
//		serverUser = new ServerUser(Rules.generatePwHash(passwort), "Nam26e323");
//		assertEquals(Rules.generatePwHash(passwort), serverUser.getPwHash());
		
	}
	
	@Test
	void testIsPwHash() throws UserException {
		for (int runde = 0; runde < userNames.length; runde ++ ) {
			assertTrue(ServerUser.getUserByname(userNames[runde]).isPwHash(Rules.generatePwHash(runde)));
		}
		String passwortHash = Rules.generatePwHash(296726l);
		ServerUser user;
//		user = new ServerUser(passwortHash, "Hallochen");
//		assertTrue(user.isPwHash(passwortHash));
//		passwortHash = Rules.generatePwHash(724862l);
//		user = new ServerUser(passwortHash, "Hallohen");
//		assertTrue(user.isPwHash(passwortHash));
//		passwortHash = Rules.generatePwHash(724826l);
//		user = new ServerUser(passwortHash, "Halloen");
//		assertTrue(user.isPwHash(passwortHash));
//		passwortHash = Rules.generatePwHash(7248l);
//		user = new ServerUser(passwortHash, "Hallon");
//		assertFalse(user.isPwHash(Rules.generatePwHash(15615l)));
//		passwortHash = Rules.generatePwHash(0l);
//		user = new ServerUser(passwortHash, "Hallo");
//		assertFalse(user.isPwHash(Rules.generatePwHash( -1l)));
//		passwortHash = Rules.generatePwHash(0l);
//		user = new ServerUser(passwortHash, "Hall");
//		assertFalse(user.isPwHash(Rules.generatePwHash(1l)));
//		passwortHash = Rules.generatePwHash(1l);
//		user = new ServerUser(passwortHash, "Hal");
//		assertFalse(user.isPwHash(Rules.generatePwHash( -1l)));
//		passwortHash = Rules.generatePwHash(1l);
//		user = new ServerUser(passwortHash, "Ha");
//		assertTrue(user.isPwHash(Rules.generatePwHash(1)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(2l)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(4l)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(8l)));
//		assertTrue(user.isPwHash(Rules.generatePwHash(1)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(16l)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(32l)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(64l)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(128l)));
//		assertTrue(user.isPwHash(Rules.generatePwHash(1)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(256l)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(512l)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(1024l)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(2048l)));
//		assertTrue(user.isPwHash(Rules.generatePwHash(1)));
//		assertFalse(user.isPwHash(Rules.generatePwHash(4096l)));
//		assertTrue(user.isPwHash(Rules.generatePwHash(1)));
	}
	
	@Test
	void testGetKey() throws UserException {
		byte[] bytes;
		byte[] encrypt;
		byte[] decrypt;
		ServerUser user;
		bytes = new byte[] {(byte) 236, (byte) 236, (byte) 23 };
		long password = 136262l;
		user = new ServerUser(password, "Kal");
		AssymetrischOffen offener = user.getOffenenKey();
		String encEigener = user.getEncryptedEigenenKey();
		String eigenerStr = ByteweiseÜbertragen.Verschlüsselt.entschlüsselnString(ZahlenUmwandeln.hexZuByteArray(encEigener), password);
		AssymetrischEigener eigener = new AssymetrischEigener(eigenerStr);
		encrypt = offener.verschlüsseln(bytes);
		decrypt = eigener.entschlüsseln(encrypt);
		int runde;
		for (runde = 0; runde < decrypt.length; runde ++ ) {
			assertEquals(bytes[runde], decrypt[runde]);
		}
	}
	
	@Test
	void testExists() throws UserException {
		String userName = "Menno";
		assertTrue(ServerUser.exists(userNames[0]));
		assertTrue(ServerUser.exists(userNames[1]));
		assertTrue(ServerUser.exists(userNames[2]));
		assertFalse(ServerUser.exists(userName));
		new ServerUser(15l, userName);
		assertTrue(ServerUser.exists(userName));
		assertFalse(ServerUser.exists(userName + "_"));
	}
	
	@Test
	void testGetUserNames() throws UserException {
		Set <String> names = ServerUser.getUserNames();
		assertEquals(userNames.length, names.size());
		for (String dieser : userNames) {
			assertTrue(names.contains(dieser));
		}
		new ServerUser(14, "j");
		for (String dieser : userNames) {
			assertTrue(names.contains(dieser));
		}
		names = ServerUser.getUserNames();
		assertEquals(userNames.length + 1, names.size());
		
		for (String dieser : userNames) {
			assertTrue(names.contains(dieser));
		}
		assertTrue(names.contains("j"));
		
		final Set <String> finalNameSet = ServerUser.getUserNames();
		assertThrows(Throwable.class, () -> finalNameSet.add("HA"));
		
	}
	
	@Test
	void testGetUsers() throws UserException {
		Collection <ServerUser> users = ServerUser.getUsers();
		assertEquals(userNames.length, users.size());
		Iterator <ServerUser> iterator = users.iterator();
		int runde;
		boolean[] fehlerFinder = new boolean[userNames.length];
		for (runde = 0; runde < userNames.length; runde ++ ) {
			ServerUser serverUser = iterator.next();
			for (int innenrunde = 0; innenrunde < userNames.length; innenrunde ++ ) {
				if (serverUser.getName().equals(userNames[innenrunde])) {
					fehlerFinder[innenrunde] = true;
					break;
				}
			}
		}
		for (boolean dieser : fehlerFinder) {
			assertTrue(dieser);
		}
	}
	
}
