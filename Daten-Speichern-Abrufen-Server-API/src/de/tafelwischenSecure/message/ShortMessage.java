package de.tafelwischenSecure.message;

public class ShortMessage implements ShortMessageInterface {
	
	protected final String sendFrom;
	protected final String time;
	protected final String title;
	protected final long id;
	
	public ShortMessage(String sendFrom, String time, String title, long id) {
		this.sendFrom = sendFrom;
		this.time = time;
		this.title = title;
		this.id = id;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getTime() {
		return time;
	}
	
	@Override
	public String getSendFrom() {
		return sendFrom;
	}
	
	@Override
	public long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "MSG["+id+";"+sendFrom+";"+time+";"+title+"]";
	}
	
}
