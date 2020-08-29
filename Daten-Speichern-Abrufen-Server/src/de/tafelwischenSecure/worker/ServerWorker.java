package de.tafelwischenSecure.worker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import de.hechler.patrick.hilfZeugs.CompatibilityUtils;
import de.hechler.patrick.hilfZeugs.objects.patExep.NotYetImplementedException;
import de.hechler.patrick.hilfZeugs.umwandeln.Umwandeln;
import de.tafelwischenSecure.Constants;
import de.tafelwischenSecure.Server;
import de.tafelwischenSecure.TafelwischenSecureServer;
import de.tafelwischenSecure.commandExecution.ServerCommandExecuterInterface;
import de.tafelwischenSecure.exceptions.UnknownCommandException;
import de.tafelwischenSecure.secure.DecryptedMessage;
import de.tafelwischenSecure.secure.ServerSidedSecurityManagerInterface;

public class ServerWorker implements ServerWorkerInterface {
	
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private ServerSidedSecurityManagerInterface securityManager;
	private Long seed;
	private ServerCommandExecuterInterface commandExecuter;
	private Server server;
	
	public ServerWorker(Socket socket, ServerSidedSecurityManagerInterface sssm, Server server, ServerCommandExecuterInterface commandExecuter) {
		this.socket = socket;
		this.securityManager = sssm;
		this.commandExecuter = commandExecuter;
		this.seed = null;
		this.server = server;
	}
	
	@Override
	public void run() {
		try {
			init(socket);
			String command = readCommand();
			TafelwischenSecureServer.logDebug("COMMAND = " + command);
			String rückgabe = executeCommand(command);
			TafelwischenSecureServer.logDebug("RESPONS = " + rückgabe);
			sendResponse(rückgabe);
			clearTheNotServerRelevantThings();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void init(Socket socket) throws IOException {
		socket.setSoTimeout(10000);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	@Override
	public Long getSeed() {
		return seed;
	}
	
	@Override
	public String readCommand() throws IOException {
		String rückgabe = input.readUTF();
		if (rückgabe == null || rückgabe.length() < 1) {
			throw new RuntimeException("The command was to short (empty) or null");
		}
		if (rückgabe.split(Constants.COMMAND_SPLITTER)[0].equals(Constants.ENCRYPTED)) {
			String[] cmds = rückgabe.split(Constants.COMMAND_SPLITTER);
			if (cmds.length != 3) {
				throw new RuntimeException("Wrong encrypted message length" + cmds.length);
			}
			DecryptedMessage decryptedMessage = securityManager.decrypt(server.getPrivateKey(), cmds[2], cmds[1]);
			setSeed(decryptedMessage.getSeed());
			return decryptedMessage.getMessage();
		}
		return rückgabe;
	}
	
	@Override
	public String executeCommand(String command) {
		command = CompatibilityUtils.requireNonNullElse(command, Constants.UNKNOWN_COMMAND);
		try {
			if (command.equals(Constants.IS_ALIVE)) {
				return commandExecuter.isAlive();
			}
			if (command.equals(Constants.GET_PUBLIC_KEY_FROM_SERVER)) {
				return commandExecuter.getPublicKey();
			}
			if (command.equals(Constants.GET_SERVER_VERSION)) {
				return commandExecuter.getServerVersion();
			}
			String[] befehle = command.split(Constants.COMMAND_SPLITTER);
			if (befehle[0].equals(Constants.USER)) {
//				List <String> commandList = ListUndArrayUmwandeln.umwandelnString(befehle);
				List <String> commandList = new ArrayList <String>();
				Umwandeln.addToCollection(befehle, commandList);
				commandList.remove(0);
				return executeUserCommand(commandList, command);
			}
			if (befehle[0].equals(Constants.GROUP)) {
//				List <String> commandList = ListUndArrayUmwandeln.umwandelnString(befehle);
				List <String> commandList = new ArrayList <String>();
				Umwandeln.addToCollection(befehle, commandList);
				commandList.remove(0);
				return executeGroupCommand(commandList, command);
			}
		} catch (UnknownCommandException | IndexOutOfBoundsException e) {
		}
		return Constants.UNKNOWN_COMMAND;
	}
	
	
	private String executeGroupCommand(List <String> befehle, String orig) {
		throw new NotYetImplementedException("executeGroupCommand", false);
	}
	
	private String executeUserCommand(List <String> befehle, String orig) throws UnknownCommandException {
		if (Constants.NEW.equals(befehle.get(0))) {
			if (Constants.PASSWORT_HASH.equals(befehle.get(1))) {
				return commandExecuter.createUserWithPwAndKey(befehle.get(2), befehle.get(3), befehle.get(4), befehle.get(5), (getSeed() != null));
			}
			return commandExecuter.createUser(befehle.get(1), (getSeed() != null));
		}
		
		if (Constants.EXISTS.equals(befehle.get(0))) {
			return commandExecuter.existsUser(befehle.get(1));
		}
		
		if (Constants.ANMELDEN.equals(befehle.get(0))) {
			befehle.remove(0);
			return commandExecuter.registerUser(befehle, (getSeed() != null));
		}
		
		if (Constants.PUBLIC_KEY.equals(befehle.get(0))) {
			return commandExecuter.getPublicKeyFromUser(befehle.get(1));
		}
		
		if (Constants.MESSAGE.equals(befehle.get(0))) {
			if (Constants.SEND.equals(befehle.get(1))) {
				String fromUser = befehle.get(2);
				String pwHash = befehle.get(3);
				String toUser = befehle.get(4);
				String title = befehle.get(5);
				String inhalt = orig.split(Constants.COMMAND_SPLITTER, 8)[7];
				return commandExecuter.sendMessageToUser(fromUser, pwHash, toUser, title, inhalt);
			}
			if (Constants.NEW.equals(befehle.get(1))) {
				return commandExecuter.hasNewMessages(befehle.get(2), befehle.get(3));
			}
			if (Constants.GET_ALL.equals(befehle.get(1))) {
				return commandExecuter.getAllMessages(befehle.get(2), befehle.get(3));
			}
			
			befehle.remove(0);
			return commandExecuter.getMessage(befehle);
		}
		
		
		
		throw new UnknownCommandException(befehle);
	}
	
	@Override
	public void sendResponse(String antworten) throws IOException {
		if (getSeed() != null) {
			antworten = securityManager.encrypt(getSeed(), antworten);
		}
		output.writeUTF(antworten);
		output.flush();
	}
	
	@Override
	public void clearTheNotServerRelevantThings() {
		try {
			input.close();
		} catch (IOException e1) {
		}
		input = null;
		try {
			output.close();
		} catch (IOException e) {
		}
		output = null;
		try {
			socket.close();
		} catch (IOException e) {
		}
		socket = null;
		seed = null;
	}
	
}
