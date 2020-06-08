package de.tafelwischenSecure.gruppe;

import java.io.IOException;

import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.Schnittstelle;

public class Gruppe {
	
	public static boolean create(String gruppenName, String[] mitglieder, String[] admins) throws IOException {
		if (admins == null || admins.length == 0) {
			return false;
		}
		mitglieder = (mitglieder != null) ? new String[0] : mitglieder;
		StringBuilder admi = new StringBuilder();
		StringBuilder mitgl = new StringBuilder();
		for (String dieses : admins) {
			admi.append(dieses);
			admi.append(",");
		}
		for (String dieses : mitglieder) {
			mitgl.append(dieses);
			mitgl.append(",");
		}
		String empfangen = Schnittstelle
				.senden(Constants.GROUP_NEW + gruppenName + Constants.COMMAND_SPLITTER + admi.toString() + Constants.COMMAND_SPLITTER + mitgl.toString());
		return (Constants.TRUE.equals(empfangen));
	}
	
	public static boolean exists(String groupName) throws IOException {
		String empfangen = Schnittstelle.senden(Constants.GROUP_EXISTS + groupName);
		return (Constants.TRUE.equals(empfangen));
	}
	
	public static boolean addMember(String newMember, String adminName) {
//		TODO machen
		return false;
	}
	
	public static boolean removeMember(String formerMember, String adminName) {
//		TODO machen
		return false;
	}
	
	public static boolean makeToAdmin(String newAdmin, String adminName) {
//		TODO machen
		return false;
	}
	
	public static boolean degradeAdmin(String formerAdmin, String adminName) {
//		TODO machen
		return false;
	}
	
}

