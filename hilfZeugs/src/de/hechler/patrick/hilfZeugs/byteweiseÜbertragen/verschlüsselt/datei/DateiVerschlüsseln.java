package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.datei;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import de.hechler.patrick.hilfZeugs.GlobalScanner;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.DateiLesen;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben.DateiSchreiben;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.schreiben.VerschlüsseltSchreiben;

public class DateiVerschlüsseln {
	
	private static String dateiLeseName;
	private static String dateiSchreibeName;
	private static boolean verschlüsseln;
	
	private static DateiLesen leser;
	private static VerschlüsseltSchreiben schreiber;
	
	public static void main(String[] args) throws Exception {
		boolean machen = SetupZeugs.setup(args);
		
		if ( !machen) {
			return;
		}
		
		while (true) {
			Integer zwischen = leser.getNächstesByte();
			if (zwischen == null) {
				break;
			}
			schreiber.schreibeByte(zwischen);
		}
		
		System.out.println("Die Datei " + dateiLeseName + " wurde nun unter " + dateiSchreibeName + " entschlüsselt.");
		
	}
	
	private static class SetupZeugs {
		
		private static final String DATEI_ENDUNG = ".wbt";
		private static final String VERSCHLÜSSELT_ZUSATZ = "encr";
		private static final String ENTSCHLÜSSELT_ZUSATZ = "decr";
		
		private static boolean setup(String[] befehle) throws IOException, InterruptedException {
			dateiLeseName = (befehle.length >= 1) ? befehle[0] : eingabeDateiNameWort("Wie lautet der Dateiname? (Alles muss zusammengeschrieben sein)");
			verschlüsseln = (befehle.length >= 2) ? Boolean.parseBoolean(befehle[1])
					: eingabeBoolean("Soll ver- oder entschlüsselt werden?", new String[] {"verschlüsseln", "V" }, new String[] {"entschlüsseln", "E" });
			dateiSchreibeName = (befehle.length >= 3) ? befehle[2] : dateiLeseName;
			dateiSchreibeName = namensGeber(verschlüsseln, dateiSchreibeName);
			boolean allowOverwrite = (befehle.length >= 5) ? Boolean.parseBoolean(befehle[4]) : false;
			
			leser = new DateiLesen(dateiLeseName);
			
			boolean nochmal = true;
			while (nochmal) {
				nochmal = false;
				try {
					
					if (befehle.length >= 4) {
						schreiber = new VerschlüsseltSchreiben(new DateiSchreiben(dateiSchreibeName, allowOverwrite), Long.parseLong(befehle[3]));
					} else {
						GlobalScanner eingabe = GlobalScanner.getInstance();
						
						boolean falscheEingabe;
						do {
							falscheEingabe = false;
							
							System.out
									.println("Soll eine neue Verschlüsselungszahl Generiert werden, oder soll diese vorgegeben werden? (\"neu\"/\"selbst\"))");
							String zwischen = eingabe.next();
							
							if (zwischen.equalsIgnoreCase("neu")) {
								schreiber = new VerschlüsseltSchreiben(new DateiSchreiben(dateiSchreibeName, false));
							} else if (zwischen.equalsIgnoreCase("selbst")) {
								System.out.println("Gebt nun die VerschlüsselungsZahl ein.");
								long verZahl = eingabe.nextLong();
								schreiber = new VerschlüsseltSchreiben(new DateiSchreiben(dateiSchreibeName, false), verZahl);
							} else {
								falscheEingabe = true;
							}
							
						} while (falscheEingabe);
						
					}
					
				} catch (FileAlreadyExistsException existiertBereits) {
					boolean[] zwboar = fileExists();
					if ( !zwboar[2]) {
						return false;
					}
					nochmal = zwboar[0];
					allowOverwrite = zwboar[1];
				}
			}
			if (befehle.length < 4) {
				System.out.println("Es wird die verschlüsselungsZahl " + schreiber.getVerschlüsselnungsZahl() + " benutzt.");
			}
			
			System.out.print("Es wird von der Datei " + dateiLeseName + " in die Datei " + dateiSchreibeName + " ");
			if (verschlüsseln) {
				System.out.println("verschlüsselt.");
			} else {
				System.out.println("entschlüsselt.");
			}
			
			return true;
		}
		
		private static boolean eingabeBoolean(String string, String[] ja, String[] nö) throws InterruptedException {
			int runde;
			GlobalScanner eingabe = GlobalScanner.getInstance();
			while (true) {
				System.out.println(string);
				String zwischen = "";
				
				zwischen = eingabe.next();
				
				for (runde = 0; runde < nö.length; runde ++ ) {
					if (ja[runde].equalsIgnoreCase(zwischen)) {
						return true;
					}
				}
				
				for (runde = 0; runde < nö.length; runde ++ ) {
					if (nö[runde].equalsIgnoreCase(zwischen)) {
						return false;
					}
				}
				
				System.out.println(zwischen + " ist keine gültige Eingabe, probiert es doch hiermit:");
				for (runde = 0; runde < nö.length - 1; runde ++ ) {
					System.out.print("'" + nö[runde] + "', ");
				}
				System.out.println("'" + nö[nö.length - 1] + "'");
				for (runde = 0; runde < ja.length - 1; runde ++ ) {
					System.out.print("'" + ja[runde] + "', ");
				}
				System.out.println("'" + ja[ja.length - 1] + "'");
			}
		}
		
		private static String eingabeDateiNameWort(String string) {
			GlobalScanner eingabe = GlobalScanner.getInstance();
			System.out.println(string);
			String rückgabe = eingabe.next();
			// rückgabe = rückgabe.replace('\\', '/');
			return rückgabe;
		}
		
		private static boolean[] fileExists() {
			boolean allowOverwrite = false;
			boolean nochmal = true;
			GlobalScanner eingabe = GlobalScanner.getInstance();
			String zwischen;
			
			boolean falscheEingabe;
			do {
				falscheEingabe = false;
				
				System.out.println("Die Datei " + dateiSchreibeName + " Existiert beriets.");
				System.out.println(
						"Soll die Datei überschrieben werden (\"Ja\"/\"Überschreiben\"), soll ein neuer Dateiname benutzt werden (\"Nein\"/\"Neuer Name\") oder soll der Vorgang abgebrochen werden(\"Abbruch\")?");
				zwischen = eingabe.nextLine();
				
				if (zwischen.equalsIgnoreCase("Ja") || zwischen.equalsIgnoreCase("Überschreiben")) {
					allowOverwrite = true;
				} else if (zwischen.equalsIgnoreCase("Nein") || zwischen.equalsIgnoreCase("Neuer Name")) {
					System.out.println("Wie lautet der neue Dateiname? (Alles muss zusammengeschrieben sein)");
					zwischen = eingabe.next();
					dateiSchreibeName = zwischen;
				} else if (zwischen.equalsIgnoreCase("Abbruch")) {
					return new boolean[] {false, false, false };
				} else {
					nochmal = true;
					System.out.println(zwischen + " ist keine gültige Eingabe.");
				}
				
			} while (falscheEingabe);
			
			return new boolean[] {nochmal, allowOverwrite, true };
		}
		
		private static String namensGeber(boolean verschlüsseln, String dateiNameVorher) {
			int ende = dateiNameVorher.length();
			boolean dateiendungIstDa = dateiNameVorher.substring(ende - DATEI_ENDUNG.length(), ende).equals(DATEI_ENDUNG);
			
			if (verschlüsseln) {
				if ( !dateiendungIstDa) {
					return dateiNameVorher + DATEI_ENDUNG;
				} else if ( !dateiLeseName.equals(dateiSchreibeName)) {
					return dateiNameVorher;
				} else {
					return dateiNameVorher.substring(0, ende - DATEI_ENDUNG.length()) + "-" + VERSCHLÜSSELT_ZUSATZ + DATEI_ENDUNG;
				}
			} else {
				if (dateiendungIstDa) {
					return dateiNameVorher.substring(0, ende - DATEI_ENDUNG.length());
				} else if ( !dateiLeseName.equals(dateiSchreibeName)) {
					return dateiNameVorher;
				} else {
					return ENTSCHLÜSSELT_ZUSATZ + "-" + dateiNameVorher;
				}
			}
			
		}
		
	}
	
}
