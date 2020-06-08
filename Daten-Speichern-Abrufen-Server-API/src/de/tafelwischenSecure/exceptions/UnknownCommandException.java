package de.tafelwischenSecure.exceptions;

import java.util.List;

public class UnknownCommandException extends TafelwischenException {
	
	private static final long serialVersionUID = 4230120947117873963L;
	
	private String cmd;
	private List <String> cmds;
	
	public UnknownCommandException(String command) {
		super("This is an unknown command" + command);
		cmd = command;
	}
	
	public UnknownCommandException(List <String> command) {
		super("This is an unknown command" + command);
		cmds = command;
	}
	
	public String getCommand() {
		if (cmds != null) {
			return cmds.toString();
		}
		return cmd;
	}
	
	@Override
	public String getStandardNachricht() {
		return "Dies ist ein unbekanntes Kommando";
	}
	
}
