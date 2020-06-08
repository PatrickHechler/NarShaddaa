package de.hechler.patrick.hilfZeugs.umwandeln;

import java.nio.charset.StandardCharsets;

public class StringByteConvert {
	
	public static byte[] stringZuBytes(String umwandeln) {
		return umwandeln.getBytes(StandardCharsets.UTF_8);
	}
	
	public static String bytesZuString(byte[] umwandeln) {
		return new String(umwandeln, StandardCharsets.UTF_8);
	}
	
}
