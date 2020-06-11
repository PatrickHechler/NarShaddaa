package de.tafelwischenSecure;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import de.tafelwischenSecure.command.Command;
import de.tafelwischenSecure.command.CommandEnum;
import de.tafelwischenSecure.command.Implemented;


public class Constants {
	
	public static final Charset CHARSET = StandardCharsets.UTF_8;
	
	/**
	 * <pre>
	 * Asks the server, if it is there
	 * empfangen:
	 * 	{@link #TRUE}, if the server is ready to receive data
	 * 	{@link #FALSE}, if not
	 * </pre>
	 */
	@Command(spezify = CommandEnum.command)
	@Implemented(since = 1)
	public static final String IS_ALIVE = "ALV";
	/**
	 * <pre>
	 * Asks the server, which version he has
	 * empfangen:
	 * </pre>
	 */
	@Command(spezify = CommandEnum.command)
	@Implemented(since = 2)
	public static final String GET_SERVER_VERSION = "GSV";
	/**
	 * When the server receives an Message/Command and does not know what for a Command this is, this will be answered.
	 */
	@Command(spezify = CommandEnum.error)
	public static final String UNKNOWN_COMMAND = "UKC";
	
	@Command(spezify = CommandEnum.boolisch)
	public static final String FALSE = "false";
	@Command(spezify = CommandEnum.boolisch)
	public static final String TRUE = "true";
	@Command(spezify = CommandEnum.connecter)
	public static final String COMMAND_SPLITTER = "⁰";
	/**
	 * this + encryptedSymetricSeed + COMMAND_SPLITTER + encryptedMessage
	 */
	@Command(spezify = CommandEnum.prefix)
	@Implemented(since = 2)
	public static final String ENCRYPTED = "ENC";
	
	/**
	 * parameter to ask if something exist.
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String EXISTS = "EXI";
	/**
	 * parameter for public key
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String PUBLIC_KEY = "PUK";
	/**
	 * parameter for passwortHash
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String PASSWORT_HASH = "PWH";
	/**
	 * parameter to create a new group/user
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String NEW = "NEW";
	/**
	 * prefix for every user sending
	 * It is the first part of all user constants
	 */
	@Command(spezify = CommandEnum.prefix)
	public static final String USER = "BEN";
	/**
	 * parameter for a User to register himself
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String ANMELDEN = "REG";
	/**
	 * ask the server, if a username exists already: <br>
	 * this + probablyExistingUsername <br>
	 * empfangen: <br>
	 * {@link #TRUE}, if the user exists. <br>
	 * {@link #FALSE} otherwise
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 1)
	public static final String USER_EXISTS = USER + COMMAND_SPLITTER + EXISTS + COMMAND_SPLITTER;
	/**
	 * <pre>
	 * IMPORTANT:
	 * 	If this is not a encrypted sending, the server will not create the new user
	 * 
	 * this + newUsername
	 * empfangen:
	 * 	The Passwort of the new user. + COMMAND_SPLITTER + the public Key of the new user.
	 * 	{@link #FALSE}, if the user already exists or something other was wrong.
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 1)
	public static final String SEND_NEW_USER_TO_SERVER = USER + COMMAND_SPLITTER + NEW + COMMAND_SPLITTER;
	/**
	 * <pre>
	 * IMPORTANT:
	 * 	If this is not a encrypted sending, the server will not create the new user
	 * 
	 * this + username + {@link #COMMAND_SPLITTER} + pwHash +{@link #COMMAND_SPLITTER} +  encryptedPrivateKey +{@link #COMMAND_SPLITTER} +  publicKey
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 5)
	public static final String SEND_NEW_USER_WITH_PWHASH_AND_KEY_TO_SERVER = USER + COMMAND_SPLITTER + NEW + COMMAND_SPLITTER + PASSWORT_HASH
			+ COMMAND_SPLITTER;
	/**
	 * <pre>
	 * this
	 * empfangen:
	 * 	the public key of the server.
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 1)
	public static final String GET_PUBLIC_KEY_FROM_SERVER = USER + COMMAND_SPLITTER + PUBLIC_KEY;
	/**
	 * <pre>
	 * this + username
	 * empfangen:
	 * 	the public key of the user.
	 * 	{@link #FALSE} if the user does not exists
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 2)
	public static final String GET_PUBLIC_KEY_FROM_USER = USER + COMMAND_SPLITTER + PUBLIC_KEY + COMMAND_SPLITTER;
	/**
	 * <pre>
	 * this + username + COMMAND_SPLITTER + pwHash
	 * empfangen:
	 * 	{@link #FALSE}, if the user does not exists
	 * 	otherwise:
	 * 		The public Key of this user encrypted with the Passwort of the user
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 1)
	public static final String REGISTER_USER = USER + COMMAND_SPLITTER + ANMELDEN + COMMAND_SPLITTER;
	/**
	 * <pre>
	 * answer, when the Passwort was wrong
	 * </pre>
	 */
	@Command(spezify = CommandEnum.error)
	@Implemented(since = 3)
	public static final String WRONG_PW = "WPW";
	
	/**
	 * parameter to for messages
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String MESSAGE = "MSG";
	/**
	 * parameter to for sending messages
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String SEND = "SND";
	/**
	 * parameter to get messages
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String GET_ALL = "GAL";
	/**
	 * <pre>
	 * this + username + {@link #COMMAND_SPLITTER} + pwHash
	 * empfangen:
	 * 	{@link #FALSE}, if the user has no unchecked messages, or something was wrong
	 * 	
	 * 	numberOfUnchecktMessages + 
	 * 	{@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + messageTitle + {@link #COMMAND_SPLITTER} + sendFrom + {@link #COMMAND_SPLITTER} + sendTime + 
	 * 	{@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + messageTitle + {@link #COMMAND_SPLITTER} + sendFrom + {@link #COMMAND_SPLITTER} + sendTime + 
	 * 	{@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + messageTitle + {@link #COMMAND_SPLITTER} + sendFrom + {@link #COMMAND_SPLITTER} + sendTime + 
	 * 	...
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 3)
	public static final String HAS_HEW_MESSAGES = USER + COMMAND_SPLITTER + MESSAGE + COMMAND_SPLITTER + NEW + COMMAND_SPLITTER;
	/**
	 * <pre>
	 * this + username + {@link #COMMAND_SPLITTER} + pwHash
	 * empfangen:
	 * 	{@link #FALSE}, if something was wrong
	 * 	
	 * 	numberOfMessages + 
	 * 	{@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + messageTitle + {@link #COMMAND_SPLITTER} + sendFrom + {@link #COMMAND_SPLITTER} + sendTime + 
	 * 	{@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + messageTitle + {@link #COMMAND_SPLITTER} + sendFrom + {@link #COMMAND_SPLITTER} + sendTime + 
	 * 	{@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + messageTitle + {@link #COMMAND_SPLITTER} + sendFrom + {@link #COMMAND_SPLITTER} + sendTime + 
	 * 	...
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 4)
	public static final String GET_ALL_MESSAGES = USER + COMMAND_SPLITTER + MESSAGE + COMMAND_SPLITTER + GET_ALL + COMMAND_SPLITTER;
	/**
	 * <pre>
	 * this + username + {@link #COMMAND_SPLITTER} + messageID + {@link #COMMAND_SPLITTER} + pwHash
	 * empfangen:
	 * 	{@link #FALSE}, if the message does not exist or something other was wrong
	 * 	otherwise:
	 * 		the message:
	 * 			The Time, when the server got the message
	 * 			+ The username of the user, who send the message
	 * 			+ The title of the message
	 * 			+ text of the message
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 3)
	public static final String GET_MESSAGE = USER + COMMAND_SPLITTER + MESSAGE + COMMAND_SPLITTER;
	/**
	 * <pre>
	 * this + nameOfTheSendingUser + {@link #COMMAND_SPLITTER} + pwHash + {@link #COMMAND_SPLITTER} + usernameOfTheDestinationUser + {@link #COMMAND_SPLITTER} + titleOfTheMessage +
	 * {@link #COMMAND_SPLITTER} + message
	 * empfangen:
	 * 	{@link #TRUE} if the message was send.
	 * 	{@link #FALSE} otherwise
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	@Implemented(since = 3)
	public static final String SEND_MESSAGE = USER + COMMAND_SPLITTER + MESSAGE + COMMAND_SPLITTER + SEND + COMMAND_SPLITTER;
	
	/**
	 * parameter to add something to a existing Group
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String ADD = "ADD";
	/**
	 * parameter to remove something from a existing Group <br>
	 * if you do that with an admin the admin will be after that a normal member
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String REMOVE = "RMV";
	/**
	 * prefix for every Group sending <br>
	 * It is the first part of all group constants
	 */
	@Command(spezify = CommandEnum.prefix)
	public static final String GROUP = "GRP";
	/**
	 * parameter for admin operations with Groups
	 */
	@Command(spezify = CommandEnum.parameter)
	public static final String GROUP_ADMIN = "GAD";
	/**
	 * creates a new Group: <br>
	 * this + groupName + {@link #COMMAND_SPLITTER} + admin1, admin2... + {@link #COMMAND_SPLITTER} + member1, member2...
	 */
	@Command(spezify = CommandEnum.commandPart)
	public static final String GROUP_NEW = GROUP + COMMAND_SPLITTER + NEW + COMMAND_SPLITTER;
	/**
	 * adds a member to the Group: <br>
	 * this + neuesMember + {@link #COMMAND_SPLITTER} + groupName
	 */
	@Command(spezify = CommandEnum.commandPart)
	public static final String GROUP_ADD_USER = GROUP + COMMAND_SPLITTER + ADD + COMMAND_SPLITTER + USER + COMMAND_SPLITTER;
	/**
	 * adds an admin to the Group: <br>
	 * this + newAdmin + {@link #COMMAND_SPLITTER} + groupName
	 */
	@Command(spezify = CommandEnum.commandPart)
	public static final String GROUP_ADD_ADMIN = GROUP + COMMAND_SPLITTER + ADD + COMMAND_SPLITTER + GROUP_ADMIN + COMMAND_SPLITTER;
	/**
	 * removes a member from an existing group: <br>
	 * this + exMember + COMMAND_SPLITTER + groupName
	 */
	@Command(spezify = CommandEnum.commandPart)
	public static final String GROUP_REMOVE_USER = GROUP + COMMAND_SPLITTER + REMOVE + COMMAND_SPLITTER + USER + COMMAND_SPLITTER;
	/**
	 * makes an admin of the group to a normal member: <br>
	 * this + exAdmin + COMMAND_SPLITTER + groupName
	 */
	@Command(spezify = CommandEnum.commandPart)
	public static final String GROUP_REMOVE_ADMIN = GROUP + COMMAND_SPLITTER + REMOVE + COMMAND_SPLITTER + GROUP_ADMIN + COMMAND_SPLITTER;
	/**
	 * <pre>
	 * this + nameOfProbablyExistingGroup
	 * empfangen:
	 * 	{@link #TRUE}, if the Group exists
	 * 	{@link #FALSE}, if the Group does not exists
	 * </pre>
	 */
	@Command(spezify = CommandEnum.commandPart)
	public static final String GROUP_EXISTS = GROUP + COMMAND_SPLITTER + EXISTS;
	
	/**
	 * prefix for all server admin sendings
	 */
	@Command(spezify = CommandEnum.prefix)
	public static final String SERVER_ADMIN = "ADM";
	/**
	 * This is an admin command
	 * stops the server
	 */
	@Command(spezify = CommandEnum.adminCommand)
	public static final String ADM_EXIT = "exit";
	
	/**
	 * This is an admin command
	 * saves the current server state
	 */
	@Command(spezify = CommandEnum.adminCommand)
	public static final String ADM_SAVE = "save";
	/**
	 * This is an admin command
	 * shows all admin Commands
	 */
	@Command(spezify = CommandEnum.adminClientCommand)
	public static final String HELP = "?";
	/**
	 * This is an admin command
	 * this + nameOfTheBackupFile
	 * makes a backup from the server in the file name
	 */
	@Command(spezify = CommandEnum.adminCommand)
	public static final String ADM_BACKUP = "backup";
	/**
	 * This is an admin command
	 * this + nameOfTheBackupFile
	 * restores the state from a backup
	 */
	@Command(spezify = CommandEnum.adminCommand)
	public static final String ADM_RESTORE = "restore";
	/**
	 * This is an admin command
	 * makes an factory-reset
	 */
	@Command(spezify = CommandEnum.adminCommand)
	public static final String ADM_RESET = "reset";
	/**
	 * This is an admin command
	 * removes all messages.
	 */
	@Command(spezify = CommandEnum.adminCommand)
	public static final String ADM_RESETMESSAGES = "resetmessages";
	/**
	 * This is an admin command
	 * outputs the list of all user data.
	 */
	@Command(spezify = CommandEnum.adminCommand)
	public static final String ADM_LISTUSERS = "listusers";
	/**
	 * This is an admin command
	 * outputs the list of all messages.
	 */
	@Command(spezify = CommandEnum.adminCommand)
	public static final String ADM_LISTMESSAGES = "listmessages";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_CHECK = "check";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_LIST = "list";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_GET_MSG = "getMsg";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_GET_ENC_MSG = "getEncMsg";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_GET_ENC_MSG_LO = "getencmsg";
	
	public static final String USR_GET_MSG_LO = "getmsg";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_SEND_MSG = "sendMsg";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_SEND_ENC_MSG = "sendEncMsg";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_SEND_ENC_MSG_LO = "sendencmsg";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_SEND_MSG_LO = "sendmsg";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_QUIT = "quit";
	
	@Command(spezify = CommandEnum.clientCommand)
	public static final String USR_LIST_ALL = "listall";
	
	public static final int DEAFULT_PORT = 8925;
	
	public static final String DEAFULT_HOST = "localhost";
	
}
