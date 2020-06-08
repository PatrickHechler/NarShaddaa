package de.tafelwischenSecure;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.tafelwischenSecure.commandExecution.ServerCommandExecuter;
import de.tafelwischenSecure.exception.CorruptServerDataException;
import de.tafelwischenSecure.exceptions.InvalidUsernameExeption;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.serverObjects.user.ServerUser;

class TafelwischenSecureServerTest {
	
	static Server server;
	static ServerCommandExecuter commandExecuter;
	
	@BeforeAll
	static void start() throws CorruptServerDataException, IOException {
		TafelwischenSecureServer.setDebuglog(true);
		server = new Server(2026, "./testData");
		server.load();
		server.startBackground();
		commandExecuter = new ServerCommandExecuter(server);
	}
	
	@AfterAll
	static void stop() {
		try {
			server.stop();
		} catch (IOException e) {
		}
	}
	
	@Test
	void testIsAlive() {
		assertEquals(Constants.TRUE, commandExecuter.isAlive());
	}
	
	@Test
	void testGetPubligKey() {
		assertEquals(server.getPublicKey().toString(), commandExecuter.getPublicKey());
	}
	
	@Test
	void testExistsUser() throws UserAlreadyExistsException, InvalidUsernameExeption {
		ServerUser.removeAllUsers();
		assertTrue(Constants.FALSE.equals(commandExecuter.existsUser("Hallo")));
		new ServerUser(0, "Hallo");
		assertTrue(Constants.TRUE.equals(commandExecuter.existsUser("Hallo")));
		ServerUser.removeAllUsers();
	}
	
	@Test
	void testCreateUser() {
		ServerUser.removeAllUsers();
		assertFalse(Constants.FALSE.equals(commandExecuter.createUser("Menno", true)));
		assertTrue(Constants.FALSE.equals(commandExecuter.createUser("Menno2", false)));
		assertTrue(Constants.FALSE.equals(commandExecuter.createUser("Menno", true)));
		ServerUser.removeAllUsers();
	}
	
	
}
