package de.tafelwischenSecure.serverObjects.user;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.lesen.ArrayLesen;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.lesen.VerschlüsseltLesen;
import de.hechler.patrick.hilfZeugs.umwandeln.StringByteConvert;
import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;
import de.tafelwischenSecure.Rules;
import de.tafelwischenSecure.exception.CorruptServerDataException;
import de.tafelwischenSecure.exceptions.InvalidPwHashExeption;
import de.tafelwischenSecure.exceptions.InvalidUsernameExeption;
import de.tafelwischenSecure.exceptions.UserAlreadyExistsException;
import de.tafelwischenSecure.exceptions.UserDoesNotExistsExeption;
import de.tafelwischenSecure.rsa.schlüssel.AssymetrischPaar;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public class ServerUser implements ServerUserInterface, Comparable <ServerUser> {
	
	/**
	 * The UID of the ServerUser
	 */
	private static final long serialVersionUID = -96411388936436377L;
	
	private static HashMap <String, ServerUser> users;
	
	
	static {
		removeAllUsers();
	}
	
	
	private String passwortHash;
	private String name;
	private AssymetrischOffen offenenKey;
	private String encryptedEigenenKey;
	
	
	public ServerUser(String passwortHash, String name, AssymetrischOffen offenerKey, String encryptedEigenerKey)
			throws UserAlreadyExistsException, InvalidUsernameExeption, InvalidPwHashExeption {
		
		if ( !Rules.isAcceptablePwhash(passwortHash)) {
			throw new InvalidPwHashExeption(passwortHash);
		}
		create(passwortHash, name, offenerKey, encryptedEigenerKey);
	}
	
	public ServerUser(long passwort, String name) throws UserAlreadyExistsException, InvalidUsernameExeption {
		AssymetrischPaar pair = new AssymetrischPaar();
		pair.setName(name);
		byte[] bytes = StringByteConvert.stringZuBytes(pair.getEigenenAlsString());
		VerschlüsseltLesen leser = new VerschlüsseltLesen(new ArrayLesen(bytes), passwort);
		try {
			String encryptedPrivKey = ZahlenUmwandeln.zuHex(leser.getBytes());
			create(Rules.generatePwHash(passwort), name, pair.getOffen(), encryptedPrivKey);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void create(String passwortHash, String name, AssymetrischOffen offenerKey, String encryptedEigenerKey)
			throws InvalidUsernameExeption, UserAlreadyExistsException {
		if (name == null || passwortHash == null) {
			throw new NullPointerException("Weder name, noch der PasswortHash darf null sein.");
		}
		if ( !Rules.isAcceptableName(name)) {
			throw new InvalidUsernameExeption(name);
		}
		if (users.containsKey(name)) {
			throw new UserAlreadyExistsException(name);
		}
		
		this.passwortHash = passwortHash;
		this.name = name;
		
		this.offenenKey = offenerKey;
		this.encryptedEigenenKey = encryptedEigenerKey;
		
		this.offenenKey.setName(name);
		
		addNewUser(name, this);
	}
	
	private static synchronized void addNewUser(String name, ServerUser newUser) {
		users.put(name, newUser);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getPwHash() {
		return passwortHash;
	}
	
	@Override
	public boolean isPwHash(String hash) {
		return passwortHash.equals(hash);
	}
	
	@Override
	public int compareTo(ServerUser o) {
		return name.compareTo(o.getName());
	}
	
	@Override
	public AssymetrischOffen getOffenenKey() {
		return offenenKey;
	}
	
	@Override
	public String getEncryptedEigenenKey() {
		return encryptedEigenenKey;
	}
	
	public static boolean exists(String potentialExistingUser) {
		return users.containsKey(potentialExistingUser);
	}
	
	public static synchronized Collection <ServerUser> getUsers() {
		return Collections.unmodifiableList(new ArrayList <>(users.values()));
	}
	
	public static synchronized Set <String> getUserNames() {
		return Collections.unmodifiableSet(new HashSet <>(users.keySet()));
	}
	
	public static Map <String, ServerUser> getUserMap() {
		return Collections.unmodifiableMap(users);
	}
	
	public static ServerUser getUserByname(String name) throws UserDoesNotExistsExeption {
		if ( !users.containsKey(name)) {
			throw new UserDoesNotExistsExeption(name);
		}
		return users.get(name);
	}
	
	public static void removeAllUsers() {
		users = new HashMap <String, ServerUser>();
	}

	@Override
	public void writeToDataStream(DataOutputStream out) throws IOException {
		out.writeUTF(getName());
		out.writeUTF(getPwHash());
		out.writeUTF(getOffenenKey().toString());
		out.writeUTF(getEncryptedEigenenKey());
	}

	public static ServerUser createFromDataStream(DataInputStream in) throws IOException, CorruptServerDataException {
		String name = in.readUTF();
		String pwHash = in.readUTF();
		String offen = in.readUTF();
		String encEigenerKey = in.readUTF();
		AssymetrischOffen offenerKey = new AssymetrischOffen(offen);
		try {
			ServerUser newUser = new ServerUser(pwHash, name, offenerKey, encEigenerKey);
			return newUser;
		} catch (InvalidUsernameExeption | UserAlreadyExistsException | InvalidPwHashExeption e) {
			e.printStackTrace();
			throw new CorruptServerDataException(e.getStandardNachricht());
		}
	}

	@Override
	public void print() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "USER["+getName()+"]";
	}
	
}
