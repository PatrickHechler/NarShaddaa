package de.tafelwischenSecure.serverObjects.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.exception.CorruptServerDataException;
import de.tafelwischenSecure.exceptions.DuplicateIdException;
import de.tafelwischenSecure.exceptions.InvalidAccessException;
import de.tafelwischenSecure.exceptions.MessageDoesNotExistException;
import de.tafelwischenSecure.message.Message;

public class ServerMessage extends Message implements ServerMessageInterface {
	
	private static final long serialVersionUID = -6452712965485952830L;
	
	private static HashMap <Long, ServerMessage> messages;
	private static long counter;
	
	static {
		removeAllMessages();
	}
	
	private boolean unchecked;
	private final String sendTo;
	
	public ServerMessage(long id, boolean unchecked, String time, String title, String inhalt, String sendFrom, String sendTo) throws DuplicateIdException {
		super(sendFrom, time, title, inhalt, id);
		if (messages.containsKey(id)) {
			throw new DuplicateIdException("message ID: " + id);
		}
		this.unchecked = unchecked;
		this.sendTo = sendTo;
		addMsg(id, this);
	}
	
	private synchronized static void addMsg(long id, ServerMessage serverMessage) {
		messages.put(id, serverMessage);
	}
	
	private static long nextId() {
		long rückgabe = ++ counter;
		while (messages.containsKey(rückgabe)) {
			rückgabe = ++ counter;
			
			if (rückgabe == -1) {
				rückgabe = 0;
				counter = 0;
			}
		}
		return rückgabe;
	}
	
	public ServerMessage(String title, String inhalt, String sendingUser, String destinationUser) {
		super(sendingUser, new SimpleDateFormat("dd.MM.YYYY 'on the' HH:mm:ss").format(new Date()), title, inhalt, nextId());
		this.unchecked = true;
		this.sendTo = destinationUser;
		addMsg(id, this);
	}
	
	public synchronized static Map <Long, ServerMessage> getMessageMap() {
		return Collections.unmodifiableMap(messages);
	}
	
	public synchronized static List <ServerMessage> getMessages() {
		return Collections.unmodifiableList(new ArrayList <ServerMessage>(messages.values()));
	}
	
	public static long getCounter() {
		return counter;
	}
	
	public static void setCounter(long msgCounter) {
		counter = msgCounter;
	}
	
	
	@Override
	public String getShortMessage() {
		String rückgabe;
		rückgabe = id + Constants.COMMAND_SPLITTER + time + Constants.COMMAND_SPLITTER + sendFrom + Constants.COMMAND_SPLITTER + title;
		return rückgabe;
	}
	
	@Override
	public String getSendTo() {
		return sendTo;
	}
	
	@Override
	public boolean isUnchecked() {
		return unchecked;
	}
	
	public String toString() {
		return "MSG[" + getShortMessage() + "]";
	}
	
	@Override
	public void print() {
		System.out.println("ID:        " + id);
		System.out.println("SEND ON:   " + time);
		System.out.println("UNCHECKED: " + unchecked);
		System.out.println("SEND FROM: " + sendFrom);
		System.out.println("SEND TO:   " + sendTo);
		System.out.println("TITLE:     " + title);
		System.out.println("INHALT:    " + inhalt);
		System.out.println();
	}
	
	public static ServerMessage getMessagebyID(long id, String user) throws MessageDoesNotExistException, InvalidAccessException {
		if ( !messages.containsKey(id)) {
			throw new MessageDoesNotExistException(id);
		}
		ServerMessage msg = messages.get(id);
		if (msg.sendTo.equals(user)) {
			msg.unchecked = false;
			return msg;
		}
		throw new InvalidAccessException(user, "message with the id " + id);
	}
	
	public static List <String> getUncheckedMessages(String user) {
		Collection <ServerMessage> nachrichten = messages.values();
		List <String> rückgabe = new ArrayList <String>();
		for (ServerMessage serverMessage : nachrichten) {
			if (serverMessage.sendTo.equals(user) && serverMessage.isUnchecked()) {
				rückgabe.add(serverMessage.getShortMessage());
			}
		}
		return rückgabe;
	}
	
	@Override
	public void writeToDataStream(DataOutputStream out) throws IOException {
		out.writeLong(id);
		out.writeBoolean(unchecked);
		out.writeUTF(time);
		out.writeUTF(title);
		out.writeUTF(inhalt);
		out.writeUTF(sendFrom);
		out.writeUTF(sendTo);
	}
	
	
	public static ServerMessage createFromDataStream(DataInputStream in) throws CorruptServerDataException, IOException {
		long id = in.readLong();
		boolean unchecked = in.readBoolean();
		String time = in.readUTF();
		String title = in.readUTF();
		String inhalt = in.readUTF();
		String sendFrom = in.readUTF();
		String sendTo = in.readUTF();
		try {
			ServerMessage newMsg = new ServerMessage(id, unchecked, time, title, inhalt, sendFrom, sendTo);
			return newMsg;
		} catch (DuplicateIdException e) {
			e.printStackTrace();
			throw new CorruptServerDataException(e.getStandardNachricht());
		}
	}
	
	public static void removeAllMessages() {
		messages = new HashMap <>();
		counter = 0;
	}
	
}