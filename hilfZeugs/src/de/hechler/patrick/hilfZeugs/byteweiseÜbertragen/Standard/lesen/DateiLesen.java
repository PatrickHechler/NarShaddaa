package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DateiLesen implements DateiLesenInterface {
	
	private final String datei;
	private final long größe;
	private final FileInputStream input;
	
	private long position;
	
	public DateiLesen(String dateiName) throws IOException {
		
		datei = dateiName;
		
		größe = Files.size(Paths.get(dateiName));
		
		input = new FileInputStream(dateiName);
		
		position = 0;
		
	}
	
	@Override
	public long getGröße() {
		return größe;
	}
	
	@Override
	public long getAnzahlBisherigerBytes() {
		return position;
	}
	
	@Override
	public byte[] getBytes() throws IOException {
		return getBytes(1024);
	}
	
	@Override
	public byte[] getBytes(int maxBytes) throws IOException {
		if (position >= größe) {
			return null;
		}
//		JAVA 1.8
		/* if (position + maxBytes > größe) {
		 * maxBytes = (int) (größe - position);
		 * }
		 * byte[] bytes = new byte[maxBytes];
		 * position += maxBytes;
		 * input.read(bytes);
		 * return bytes; */
//		JAVA 1.8 ENDE
		
		if ( (position + maxBytes) < größe) {
			position += maxBytes;
			return input.readNBytes(maxBytes);
		}
		position = größe;
		return input.readAllBytes();
	}
	
	@Override
	public Integer getNächstesByte() {
		int rückgabe;
		
		try {
			rückgabe = input.read();
		} catch (IOException e) {
			return null;
		}
		
		if (rückgabe == -1) {
			return null;
		}
		
		position ++ ;
		return rückgabe;
	}
	
	@Override
	public void close() {
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() {
		return datei;
	}
	
}
