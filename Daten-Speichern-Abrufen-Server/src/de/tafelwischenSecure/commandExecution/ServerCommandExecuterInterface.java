package de.tafelwischenSecure.commandExecution;

import java.util.List;

public interface ServerCommandExecuterInterface {
	
	/**
	 * <pre>
	 * Asks the server, which version he has
	 * empfangen:
	 * </pre>
	 */
	public String getServerVersion();
	
	/**
	 * <pre>
	 * Asks the server, if it is there
	 * empfangen:
	 * 	{@link #TRUE}, if the server is ready to receive data
	 * 	{@link #FALSE}, if not
	 * </pre>
	 */
	public String isAlive();
	
	/**
	 * <pre>
	 * this
	 * empfangen:
	 * 	the public key of the server.
	 * </pre>
	 */
	public String getPublicKey();
	
	/**
	 * <pre>
	 * this + username
	 * empfangen:
	 * 	the public key of the user.
	 * </pre>
	 */
	public String getPublicKeyFromUser(String username);
	
	/**
	 * ask the server, if a username exists already: <br>
	 * this + probablyExistingUsername <br>
	 * empfangen: <br>
	 * {@link #TRUE}, if the user exists. <br>
	 * {@link #FALSE} otherwise
	 */
	public String existsUser(String userName);
	
	/**
	 * <pre>
	 * IMPORTANT:
	 * 	If this is not a encrypted sending, the server will not create the new user
	 * 
	 * this + newUsername
	 * empfangen:
	 * 	The Passwort of the new user. + COMMAND_SPLITTER + the private Key of the new user.
	 * 	{@link #FALSE}, if the user already exists or something other was wrong.
	 * </pre>
	 */
	public String createUser(String userName, boolean isEncryptedSending);
	
	/**
	 * <pre>
	 * this + username + COMMAND_SPLITTER + pwHash
	 * empfangen:
	 * 	{@link #FALSE}, if the user does not exists
	 * 	otherwise:
	 * 		The Private Key of this user encrypted with the Passwort of the user
	 * </pre>
	 */
	public String registerUser(List <String> befehle, boolean isEncryptedSending);
	
	/**
	 * this + nameOfTheSendingUser + {@link #COMMAND_SPLITTER} + pwHash + {@link #COMMAND_SPLITTER} + usernameOfTheDestinationUser + {@link #COMMAND_SPLITTER} + nameOfTheMessage +
	 * {@link #COMMAND_SPLITTER} + message
	 * </pre>
	 */
	public String sendMessageToUser(String fromUser, String pwHash, String toUser, String title, String inhalt);
	
	/**
	 * <pre>
	 * this + username
	 * empfangen:
	 * 	{@link #FALSE}, if the user has no unchecked messages
	 * 	numberOfUnchecktMessages + 
	 * 		{@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + messageName
	 * 		{@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + messageName
	 * 		...
	 * </pre>
	 */
	public String hasNewMessages(String username, String pwHash);

	public String getMessage(List <String> befehle);

	public String getAllMessages(String username, String pwHash); 
	
}
