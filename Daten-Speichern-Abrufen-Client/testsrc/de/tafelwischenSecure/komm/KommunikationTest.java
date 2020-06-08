package de.tafelwischenSecure.komm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.UTFDataFormatException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tafelwischenSecure.LocalhostTestServer;
import de.tafelwischenSecure.komm.Kommunikation;
import de.tafelwischenSecure.komm.KommunikationInterface;

class KommunikationTest {
	
	private static final int LH_SERVER_PORT = 23482;
	
	private LocalhostTestServer testServer;
	
	@BeforeEach
	public void setUp() {
		testServer = new LocalhostTestServer(LH_SERVER_PORT, "echo: *", 1);
		testServer.start();
	}
	
	@AfterEach
	public void tearDown() {
		testServer.stop();
		testServer = null;
	}
	
	@Test
	void testInvalidHost() {
		assertThrows(IOException.class, () -> {
			KommunikationInterface komm = new Kommunikation("DiesenHostGibtEsNicht", LH_SERVER_PORT);
			komm.sendMessage("egal");
		});
	}
	
	@Test
	void testSendMessageNull2() throws IOException {
		KommunikationInterface komm = new Kommunikation("localhost", LH_SERVER_PORT);
		try {
			komm.sendMessage(null);
		} catch (NullPointerException e) {
			assertEquals("sendWithResponse called with null message is not allowed", e.getMessage());
		}
	}
	
	@Test
	void testSendMessageWithResult() throws IOException {
		KommunikationInterface komm = new Kommunikation("localhost", LH_SERVER_PORT);
		String empfangen = komm.sendMessage("egal");
		assertEquals("echo: egal", empfangen);
	}
	
	@Test
	void testSendMessageWithUmlaut() throws IOException {
		KommunikationInterface komm = new Kommunikation("localhost", LH_SERVER_PORT);
		String empfangen = komm.sendMessage("äöüßÄÖÜ sind auch UTF Zeichen");
		assertEquals("echo: äöüßÄÖÜ sind auch UTF Zeichen", empfangen);
	}
	
	@Test
	void testSendMessageWithEmptyString() throws IOException {
		KommunikationInterface komm = new Kommunikation("localhost", LH_SERVER_PORT);
		String empfangen = komm.sendMessage("");
		assertEquals("echo: ", empfangen);
	}
	
	@Test
	void testSendMessageWith32kString() throws IOException {
		String bigString = "B";
		for (int i = 0; i < 15; i ++ ) {
			bigString += bigString;
		}
		assertEquals(32768, bigString.length());
		KommunikationInterface komm = new Kommunikation("localhost", LH_SERVER_PORT);
		String empfangen = komm.sendMessage(bigString);
		assertEquals("echo: " + bigString, empfangen);
	}
	
	@Test
	void testSendMessageFailsWith64kString() throws IOException {
		String bigString = "B";
		for (int i = 0; i < 16; i ++ ) {
			bigString += bigString;
		}
		final String send64k = bigString;
		assertEquals(65536, bigString.length());
		KommunikationInterface komm = new Kommunikation("localhost", LH_SERVER_PORT);
		assertThrows(UTFDataFormatException.class, () -> {
			komm.sendMessage(send64k);
		});
	}
	
}
