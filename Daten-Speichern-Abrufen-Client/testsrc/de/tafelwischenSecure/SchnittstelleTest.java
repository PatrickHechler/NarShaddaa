package de.tafelwischenSecure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.StartsWith;

import de.tafelwischenSecure.komm.KommunikationInterface;
import de.tafelwischenSecure.komm.sec.SecurityManagerInterface;
import de.tafelwischenSecure.komm.sec.VerschlüsselteServerNachricht;
import de.tafelwischenSecure.rsa.schlüssel.AssymetrischPaar;

class SchnittstelleTest {
	
	private static final String NEW_USERNAME = "Patrick_Hechler";
	
	
	@AfterEach
	public void tearDown() {
		Schnittstelle.setDefaultConfig();
		Schnittstelle.setDefaultSecurityManager();
	}
	
	
	@Test
	@Disabled
	void testEmpfangen() {
		fail("Not yet implemented");
	}
	
	@Test
	void testSendeWithoutServer() {
		Schnittstelle.setKommunikation(null);
		assertThrows(IOException.class, () -> {
			Schnittstelle.senden(Constants.IS_ALIVE);
		});
	}
	
	@Test
	void testVerschlüsseltSendenErgebnis() throws Exception {
		AssymetrischPaar serverKeys = new AssymetrischPaar();
		String empfangen;
		
		KommunikationInterface kommMock = mock(KommunikationInterface.class);
		Schnittstelle.setKommunikation(kommMock);
		
		SecurityManagerInterface secureMock = mock(SecurityManagerInterface.class);
		Schnittstelle.setSecurityManager(secureMock);
		
		when(kommMock.sendMessage(Constants.GET_PUBLIC_KEY_FROM_SERVER)).thenReturn(serverKeys.getOffenAlsString());
		when(kommMock.sendMessage(argThat(new StartsWith(Constants.ENCRYPTED)))).thenReturn("");
		
		when(secureMock.encrypt(any(), any())).thenReturn(new VerschlüsselteServerNachricht(1234L, "FFFFFF", "FFFFFF"));
		when(secureMock.decrypt(anyLong(), anyString())).thenReturn("ServerAnswer");
		
		empfangen = Schnittstelle.verschlüsseltSenden(Constants.IS_ALIVE);
		assertEquals("ServerAnswer", empfangen);
		
		verify(kommMock).sendMessage(Constants.GET_PUBLIC_KEY_FROM_SERVER);
		verify(kommMock).sendMessage(argThat(new StartsWith(Constants.ENCRYPTED)));
		verify(secureMock).encrypt(any(), any());
		verify(secureMock).decrypt(anyLong(), anyString());
	}
	
	@Test
	void testSendeIsAliveWithMockServer() throws IOException {
		KommunikationInterface kommMock = mock(KommunikationInterface.class);
		when(kommMock.sendMessage(Constants.IS_ALIVE)).thenReturn(Constants.TRUE);
		Schnittstelle.setKommunikation(kommMock);
		String empfangen = Schnittstelle.senden(Constants.IS_ALIVE);
		assertEquals(Constants.TRUE, empfangen);
		verify(kommMock).sendMessage(Constants.IS_ALIVE);
	}
	
	@Test
	@Disabled
	void testVersclüsseltSenden() {
		fail("Not yet implemented");
	}
	
	@Test
	@Disabled
	void testVerschlüsseltEmpfangen() {
		fail("Not yet implemented");
	}
	
	@Test
	void testRulesIsAcceptableName() {
		assertTrue(Rules.isAcceptableName("ahgogen_qeghag"));
		assertFalse(Rules.isAcceptableName("sagß"));
		assertFalse(Rules.isAcceptableName("Hallo du da"));
		assertFalse(Rules.isAcceptableName(""));
		assertTrue(Rules.isAcceptableName("Ich_Bin_13_Jahre_ALT"));
		assertFalse(Rules.isAcceptableName(
				"gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGWEGAWEGHAEGHOBVUHEQNVOANUOVENEVOAsdsdsdsdsdsdsdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"));
		assertFalse(Rules.isAcceptableName(null));
	}
	
	@Test
	void testIsNewAcceptableName() throws IOException {
		KommunikationInterface kommMock = mock(KommunikationInterface.class);
		when(kommMock.sendMessage(Constants.USER_EXISTS + NEW_USERNAME)).thenReturn(Constants.FALSE);
		Schnittstelle.setKommunikation(kommMock);
		
		boolean ergebnis = Schnittstelle.isNewAcceptableName(NEW_USERNAME);
		
		assertTrue(ergebnis);
		verify(kommMock).sendMessage(Constants.USER_EXISTS + NEW_USERNAME);
	}
	
	@Test
	void testIsNewAcceptableNameFail() throws IOException {
		KommunikationInterface kommMock = mock(KommunikationInterface.class);
		when(kommMock.sendMessage(Constants.USER_EXISTS + NEW_USERNAME)).thenReturn(Constants.TRUE);
		Schnittstelle.setKommunikation(kommMock);
		
		boolean ergebnis = Schnittstelle.isNewAcceptableName(NEW_USERNAME);
		
		assertFalse(ergebnis);
		verify(kommMock).sendMessage(Constants.USER_EXISTS + NEW_USERNAME);
	}
	
	@Test
	void testRulesGeneratePwHash() {
		long zahl;
		zahl = 38734237;
		String generatePwHash = Rules.generatePwHash(zahl);
		assertEquals("0F902F40024F099D", generatePwHash);
		generatePwHash = Rules.generatePwHash( -1);
		assertEquals("3F3F3F3F3F3F3F3F", generatePwHash);
		generatePwHash = Rules.generatePwHash(0);
		assertEquals("0000000000000000", generatePwHash);
		generatePwHash = Rules.generatePwHash(Long.MAX_VALUE);
		assertEquals("BF3F3F3F3F3F3F91", generatePwHash);
		generatePwHash = Rules.generatePwHash(Long.MIN_VALUE);
		assertEquals("8000000000000079", generatePwHash);
	}
	
}
