package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.bearbeiten;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.ArrayLesen;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.LesenInterface;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben.SchreibenInterface;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.lesen.VerschlüsseltLesen;
import de.hechler.patrick.hilfZeugs.umwandeln.StringByteConvert;

public class ByteweiseÜbertragen {
	
	public static void übertragen(LesenInterface leser, SchreibenInterface schreiber) throws IOException {
		byte[] bytes;
		while (true) {
			bytes = leser.getBytes();
			if (bytes == null || bytes.length < 1) {
				return;
			}
			schreiber.schreibeBytes(bytes);
		}
	}
	
	public static class Verschlüsselt {
		
		public static byte[] verschlüsseln(byte[] bytes, long seed) {
			try {
				ArrayLesen arrayLeser = new ArrayLesen(bytes);
				VerschlüsseltLesen leser = new VerschlüsseltLesen(arrayLeser, seed);
				return leser.getBytes();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		public static byte[] verschlüsseln(String verschlüsseln, long seed) {
			try {
				ArrayLesen arrayLeser = new ArrayLesen(StringByteConvert.stringZuBytes(verschlüsseln));
				VerschlüsseltLesen leser = new VerschlüsseltLesen(arrayLeser, seed);
				return leser.getBytes();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		public static String entschlüsselnString(byte[] bytes, long seed) {
			try {
				ArrayLesen arrayLeser = new ArrayLesen(bytes);
				VerschlüsseltLesen leser = new VerschlüsseltLesen(arrayLeser, seed);
				return StringByteConvert.bytesZuString(leser.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
}
