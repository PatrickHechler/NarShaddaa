package de.tafelwischenSecure.serverObjects.message;

import de.tafelwischenSecure.message.MessageInterface;
import de.tafelwischenSecure.serverObjects.ServerObjectInterface;

public interface ServerMessageInterface extends ServerObjectInterface, MessageInterface {
	
	public long getId();
	
	public String getShortMessage();
	
	public boolean isUnchecked();
	
	public String getSendTo();
	
}
