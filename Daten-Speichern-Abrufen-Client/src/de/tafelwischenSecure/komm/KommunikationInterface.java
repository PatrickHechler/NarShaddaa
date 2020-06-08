package de.tafelwischenSecure.komm;

import java.io.IOException;


public interface KommunikationInterface {
	
	String sendMessage(String message) throws IOException;
	
}