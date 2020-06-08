package de.hechler.patrick.hilfZeugs.umwandeln;

public class ZahlenUmwandeln {
	
	public static String zuHexJava(long umwandeln) {
		return Long.toHexString(umwandeln);
	}
	
	public static String zuHexJava(int umwandeln) {
		return Integer.toHexString(umwandeln);
	}
	
	public static String zuHex(long umwandeln) {
		return zuHex(zuByteArr(umwandeln));
	}
	
	public static String zuHex(int umwandeln) {
		return zuHex(zuByteArr(umwandeln));
	}
	
	public static String zuHex(byte[] umwandeln) {
		int runde;
		String rückgabe = "";
		for (runde = 0; runde < umwandeln.length; runde ++ ) {
			rückgabe += zuHex(umwandeln[runde]);
		}
		return rückgabe;
	}
	
	public static String zuHex(byte umwandeln) {
		String rückgabe;
		int zwischen;
		int wandelZahl = umwandeln & 0xFF;
		
		zwischen = wandelZahl;
		
		zwischen = (zwischen >> 4) & 0xF;
		rückgabe = hexZahlen.kleineZahlZuHex(zwischen);
		
		zwischen = (wandelZahl - (zwischen << 4));
		zwischen = (zwischen & 0xF);
		rückgabe += hexZahlen.kleineZahlZuHex(zwischen);
		
		return rückgabe;
	}
	
	public static long hexZuLong(String hexZahl) {
		long rückgabe = 0;
		byte[] bytes = hexZuByteArray(hexZahl);
		for (byte dieses : bytes) {
			rückgabe = (rückgabe << 8) + ( ((int) dieses) & 0xFF);
		}
		return rückgabe;
	}
	
	public static byte[] hexZuByteArray(String hexZahl) {
		int runde;
		byte[] rückgabe;
		if (hexZahl.length() % 2 != 0) {
			hexZahl = "0" + hexZahl;
		}
		rückgabe = new byte[hexZahl.length() / 2];
		
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = hexZahlen.kleinHexZoByte(hexZahl.substring(runde * 2, runde * 2 + 2));
		}
		
		return rückgabe;
	}
	
	private static class hexZahlen {
		
		private static byte kleinHexZoByte(String kleinHex) {
			byte rückgabe;
			
			if (kleinHex.length() != 2) {
				throw new RuntimeException("Die kleine HexZahl muss 2 lang sein: '" + kleinHex + "' ist ungültig!");
			}
			
			int zwichen = kleinHex.toUpperCase().charAt(0);
			if (zwichen >= '0' && zwichen <= '9') {
				zwichen += -'0';
			} else if (zwichen >= 'A' && zwichen <= 'F') {
				zwichen += -'A' + 10;
			} else {
				throw new RuntimeException("Die HexZahl darf nur aus Zahlen und den Buchstaben a-f/A-F bestehen: '" + kleinHex + "' ist ungültig!");
			}
			rückgabe = (byte) zwichen;
			
			zwichen = kleinHex.toUpperCase().charAt(1);
			if (zwichen >= '0' && zwichen <= '9') {
				zwichen += -'0';
			} else if (zwichen >= 'A' && zwichen <= 'F') {
				zwichen += -'A' + 10;
			} else {
				throw new RuntimeException("Die HexZahl darf nur aus Zahlen und den Buchstaben a-f/A-F bestehen: '" + kleinHex + "' ist ungültig!");
			}
			rückgabe = (byte) ( (rückgabe << 4) + zwichen);
			
			return rückgabe;
		}
		
		private static String kleineZahlZuHex(int kleineZahl) {
			
			if (kleineZahl < 10 && kleineZahl >= 0) {
				return kleineZahl + "";
			}
			
			switch (kleineZahl) {
			case 10:
				return "A";
			case 11:
				return "B";
			case 12:
				return "C";
			case 13:
				return "D";
			case 14:
				return "E";
			case 15:
				return "F";
			}
			throw new RuntimeException("kleine Zahl muss eine einstellige HexZahl sein: " + kleineZahl + " ist ungültig!");
		}
		
	}
	
	public static byte[] zuByteArr(long umwandeln) {
		byte[] rückgabe = new byte[8];
		int runde;
		
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = (byte) ( (umwandeln >> (56 - runde * 8)) & 0xFF);
		}
		
		return rückgabe;
	}
	
	public static long zuLong(byte[] umwandeln) {
		long rückgabe = 0;
		
		for (int runde = 0; runde < umwandeln.length; runde ++ ) {
			rückgabe = (rückgabe << 8) + ( ((long) umwandeln[runde]) & 0xFF);
		}
		
		return rückgabe;
	}
	
	public static byte[] zuByteArr(int umwandeln) {
		byte[] rückgabe = new byte[4];
		int runde;
		
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = (byte) ( (umwandeln >> (24 - runde * 8)) & 0xFF);
		}
		
		return rückgabe;
	}
	
	public static int zuInt(byte[] umwandeln) {
		int rückgabe = 0;
		
		for (int runde = 0; runde < umwandeln.length; runde ++ ) {
			rückgabe = (rückgabe << 8) + ( ((int) umwandeln[runde]) & 0xFF);
		}
		
		return rückgabe;
	}
	
}
