package de.tafelwischenSecure.serverObjects.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.exceptions.DuplicateIdException;
import de.tafelwischenSecure.exceptions.InvalidAccessException;
import de.tafelwischenSecure.exceptions.MessageDoesNotExistException;
import de.tafelwischenSecure.exceptions.UserDoesNotExistsExeption;

class ServerMessageTest {
	
	private ServerMessage msgA; 
	private ServerMessage msgB; 
	private ServerMessage msgC; 
	private ServerMessage msgD; 
	
	@BeforeEach
	void setUp() throws Exception {
		ServerMessage.removeAllMessages();
		msgA = new ServerMessage("MSGA", "Wichtige Nachricht A von Bob an Patrick", "Bob", "Patrick");
		msgB = new ServerMessage("MSGB", "Wichtige Nachricht B von Patrick an Ben", "Patrick", "Bob");
		msgC = new ServerMessage("MSGC", "Wichtige Nachricht C von Alice an Patrick", "Alice", "Patrick");
		msgD = new ServerMessage("MSGC", "Wichtige Nachricht D von Alice an Patrick", "Alice", "Patrick");
	}

	@AfterEach
	void tearDown() {
		ServerMessage.removeAllMessages();
	}


	@Test
	void testServerMessage() throws UserDoesNotExistsExeption {
		ServerMessage msg = new ServerMessage("TITLE", "INHALT", "FROM", "TO");
		assertEquals(msg.getTitle(), "TITLE");
		assertEquals(msg.getInhalt(), "INHALT");
		assertEquals(msg.getSendFrom(), "FROM");
		assertEquals(msg.getSendTo(), "TO");
		assertThrows(DuplicateIdException.class, () -> new ServerMessage(msg.getId(), true, "TIME", "TITLE", "INHALT", "FROM", "TO"));
	}

	

	
	@Test
	void testGetUncheckedMessages() throws UserDoesNotExistsExeption, MessageDoesNotExistException, InvalidAccessException {
		List <String> uncheckedMessages = ServerMessage.getUncheckedMessages("Patrick");
		assertEquals(3, uncheckedMessages.size());
		Set<Long> ids = uncheckedMessages.stream()
			.map((str) -> Long.parseLong(str.split(Constants.COMMAND_SPLITTER)[0]))
			.collect(Collectors.toSet());
		assertTrue(ids.contains(msgA.getId()));
		assertFalse(ids.contains(msgB.getId()));
		assertTrue(ids.contains(msgC.getId()));
		assertTrue(ids.contains(msgD.getId()));

		// get message C should set unchecked to false:
		ServerMessage msg = ServerMessage.getMessagebyID(msgC.getId(), msgC.getSendTo());
		assertEquals(msgC.getId(), msg.getId());
		
		uncheckedMessages = ServerMessage.getUncheckedMessages("Patrick");
		assertEquals(2, uncheckedMessages.size());
		ids = uncheckedMessages.stream()
			.map((str) -> Long.parseLong(str.split(Constants.COMMAND_SPLITTER)[0]))
			.collect(Collectors.toSet());
		assertTrue(ids.contains(msgA.getId()));
		assertFalse(ids.contains(msgB.getId()));
		assertFalse(ids.contains(msgC.getId()));
		assertTrue(ids.contains(msgD.getId()));

	}

	
	@Test
	void testGetMessagebyID() throws MessageDoesNotExistException, InvalidAccessException {
		assertThrows(MessageDoesNotExistException.class, () -> ServerMessage.getMessagebyID(msgD.getId()+1, "Patrick"));
		assertThrows(InvalidAccessException.class, () -> ServerMessage.getMessagebyID(msgA.getId(), "Hacker"));
		assertTrue(msgA.isUnchecked());
		ServerMessage msg = ServerMessage.getMessagebyID(msgA.getId(), msgA.getSendTo());
		assertFalse(msgA.isUnchecked());
		assertEquals(msgA.getId(), msg.getId());
		assertEquals(msgA.getInhalt(), msg.getInhalt());
		assertEquals(msgA.getSendFrom(), msg.getSendFrom());
		assertEquals(msgA.getSendTo(), msg.getSendTo());
		assertEquals(msgA.getTime(), msg.getTime());
		assertEquals(msgA.getTitle(), msg.getTitle());
	}
	
	@Test
	void testGetShortMessageby() throws MessageDoesNotExistException, InvalidAccessException, DuplicateIdException {
		ServerMessage msg = new ServerMessage("TITLE", "INHALT", "FROM", "TO");
		assertEquals(msg.getShortMessage(), msg.getId()+Constants.COMMAND_SPLITTER+msg.getTime()+Constants.COMMAND_SPLITTER+"FROM"+Constants.COMMAND_SPLITTER+"TITLE");
		msg = new ServerMessage(msg.getId()+1, true, "TIME", "TITLE", "INHALT", "FROM", "TO");
		assertEquals(msg.getShortMessage(), msg.getId()+Constants.COMMAND_SPLITTER+"TIME"+Constants.COMMAND_SPLITTER+"FROM"+Constants.COMMAND_SPLITTER+"TITLE");
	}

	
	
}
