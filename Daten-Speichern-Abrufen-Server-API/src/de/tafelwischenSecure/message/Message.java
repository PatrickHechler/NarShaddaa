package de.tafelwischenSecure.message;

public class Message extends ShortMessage implements MessageInterface {
	
	protected final String inhalt;
	
	public Message(String sendFrom, String time, String title, String inhalt) {
		super(sendFrom, time, title, -1);
		this.inhalt=inhalt;
	}
	
	public Message(String sendFrom, String time, String title, String inhalt, long id) {
		super(sendFrom, time, title, id);
		this.inhalt=inhalt;
	}
	
	@Override
	public String getInhalt() {
		return inhalt;
	}
	
}
