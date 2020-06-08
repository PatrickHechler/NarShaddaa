package de.tafelwischenSecure;

import java.lang.reflect.Field;

import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.command.Command;
import de.tafelwischenSecure.command.CommandEnum;

public class Rules {
	
	/**
	 * <pre>
	 * Ein Name (Benutzername/Gruppenname) darf nur aus Buchstaben(groß und klein), Zahlen und '_' bestehen.
	 * Ein Name darf nicht aus Kommandos bestehen.
	 * </pre>
	 * 
	 * @param potentialacceptableName
	 *            Dieser String wird überprüft. Wenn er nur aus Buchstaben/Zahlen und '_' besteht wird true zurückgegeben.
	 * 			
	 * @return true, wenn der Übergabewert sich an diese Regeln hält, ansonsten false.
	 */
	public static boolean isAcceptableName(String potentialacceptableName) {
		if (potentialacceptableName == null) {
			return false;
		}
		if (potentialacceptableName.equalsIgnoreCase("admin")) {
			return false;
		}
		
		Field[] declaredFields = Constants.class.getDeclaredFields();
		for (Field field : declaredFields) {
			Command commandAnnotation = field.getAnnotation(Command.class);
			if (commandAnnotation != null) {
				try {
					if (potentialacceptableName.equalsIgnoreCase((String) field.get(String.class))) {
						return false;
					}
					if (potentialacceptableName.equalsIgnoreCase(field.getName())) {
						return false;
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}
		}
		CommandEnum[] cmdEnums = CommandEnum.values();
		for (CommandEnum diesesEnum : cmdEnums) {
			if (diesesEnum.name().equalsIgnoreCase(potentialacceptableName)) {
				return false;
			}
		}
		
		for (char prüfen : potentialacceptableName.toCharArray()) {
			if (prüfen <= '9' && prüfen >= '0') {
			} else if (prüfen <= 'Z' && prüfen >= 'A') {
			} else if (prüfen <= 'z' && prüfen >= 'a') {
			} else if (prüfen == '_') {
			} else {
				return false;
			}
		}
		if (potentialacceptableName.length() >= 256) {
			return false;
		}
		if (potentialacceptableName.length() < 1) {
			return false;
		}
		return true;
	}
	
	public static String generatePwHash(long passwort) {
		long origPasswort = passwort;
		String rückgabe = "";
		int runde;
		for (runde = 0; runde < 8; runde ++ ) {
			String hex = ZahlenUmwandeln.zuHex((byte) (passwort & 0xFF));
			rückgabe += ZahlenUmwandeln.zuHex((byte) hex.hashCode());
			passwort = passwort >> 8;
		}
		rückgabe = ZahlenUmwandeln.zuHex(Long.reverse(origPasswort) ^ (Long.reverseBytes(origPasswort) ^ origPasswort) ^ ZahlenUmwandeln.hexZuLong(rückgabe));
		return rückgabe;
	}
	
	public static boolean isAcceptablePwhash(String potentialAcceptablePasswortHash) {
		if ( ! (potentialAcceptablePasswortHash.length() == generatePwHash(0).length())) {
			return false;
		}
		try {
			byte[] bytes1 = ZahlenUmwandeln.hexZuByteArray(potentialAcceptablePasswortHash);
			long zahl1 = ZahlenUmwandeln.zuLong(bytes1);
			long zahl2 = ZahlenUmwandeln.hexZuLong(potentialAcceptablePasswortHash);
			byte[] bytes2 = ZahlenUmwandeln.zuByteArr(zahl2);
			if (bytes1.length != bytes2.length) {
				return false;
			}
			if (zahl1 != zahl2) {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}
	
}
