package de.hechler.patrick.hilfZeugs.dateiOderOrdner;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DateiOderOrdner {
	
	public static boolean exists(String pfad) {
		return Files.exists(Paths.get(pfad));
	}
	
	public static boolean exists(File pfad) {
		return pfad.exists();
	}
	
	public static boolean create(String pfad) {
		File file = new File(pfad);
		return file.mkdir();
	}
	
	public static boolean create(File file) {
		return file.mkdir();
	}
	
	/**
	 * Returns false if the path exists after the method returns.
	 * 
	 * @param pfad
	 *            the last folder of the 'pfad' will be deleted after the method returns.
	 * 			
	 * @return false if the path exists after the method returns.
	 */
	public static boolean destroy(String pfad) {
		if ( !exists(pfad)) {
			return true;
		}
		File directoryToBeDeleted = new File(pfad);
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				destroy(file);
			}
		}
		return directoryToBeDeleted.delete();
	}
	
	/**
	 * Returns false if the path exists after the method returns.
	 * 
	 * @param pfad
	 *            the last folder of the 'pfad' will be deleted after the method returns.
	 * 			
	 * @return false if the path exists after the method returns.
	 */
	public static boolean destroy(File pfad) {
		if ( !exists(pfad)) {
			return true;
		}
		File[] allContents = pfad.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				destroy(file);
			}
		}
		return pfad.delete();
	}
	
	public static void createDatei(String pfad, String inhalt, String Charset, boolean allowOverrite) throws FileAlreadyExistsException {
		if (exists(pfad) && !allowOverrite) {
			throw new FileAlreadyExistsException(null);
		}
		
	}
	
}
