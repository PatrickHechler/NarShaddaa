package de.tafelwischenSecure.commandExecution;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.Server;
import de.tafelwischenSecure.exceptions.InvalidUsernameExeption;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.serverObjects.user.ServerUser;

class ServerCommandExecuterTest {
	
	private static ServerCommandExecuterInterface commandExecuter;
	private static Server server;
	
	@BeforeAll
	static void beforeAll() throws IOException {
		server = new Server(0, null);
		commandExecuter = new ServerCommandExecuter(server);
	}
	
	@Test
	void testIsAlive() {
		assertEquals(Constants.FALSE, commandExecuter.isAlive());
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
