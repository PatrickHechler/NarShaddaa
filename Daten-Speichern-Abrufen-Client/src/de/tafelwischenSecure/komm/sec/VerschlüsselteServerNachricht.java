package de.tafelwischenSecure.komm.sec;

public class Verschl�sselteServerNachricht {
	
	private long seed;
	/**
	 * Mit dem Server Public Key Verschl�sselter Seed
	 */
	private String encryptedSeed;
	/**
	 * Mit dem seed Verschl�sselte Nachricht
	 */
	private String encryptedMessage;
	
	public Verschl�sselteServerNachricht(long seed, String encryptedSeed, String encryptedMessage) {
		this.seed = seed;
		this.encryptedSeed = encryptedSeed;
		this.encryptedMessage = encryptedMessage;
	}
	
	public long getSeed() {
		return seed;
	}
	
	public String getEncryptedSeed() {
		return encryptedSeed;
	}
	
	public String getEncryptedMessage() {
		return encryptedMessage;
	}
	
}
