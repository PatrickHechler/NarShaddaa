package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DateiSchreiben implements DateienSchreibenInterface {
	
	private final String datei;
	private long größe;
	private final FileOutputStream output;
	
	public DateiSchreiben(String dateiName, boolean allowOverwrite) throws IOException {
		datei = dateiName;
		if ( !allowOverwrite && Files.exists(Paths.get(dateiName))) {
			throw new FileAlreadyExistsException(dateiName);
		}
		output = new FileOutputStream(dateiName);
		größe = 0;
		
	}
	
	@Override
	public long getGröße() {
		return größe;
	}
	
	@Override
	public void schreibeByte(int schreibeByte) throws IOException {
		output.write(schreibeByte);
		größe ++ ;
	}
	
	@Override
	public void schreibeBytes(byte[] bytes) throws IOException {
		output.write(bytes);
		größe += bytes.length;
	}
	
	@Override
	public void close() {
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() {
		return datei;
	}
	
}
