package de.tafelwischenSecure.komm.sec;

public class VerschlüsselteServerNachricht {
	
	private long seed;
	/**
	 * Mit dem Server Public Key Verschlüsselter Seed
	 */
	private String encryptedSeed;
	/**
	 * Mit dem seed Verschlüsselte Nachricht
	 */
	private String encryptedMessage;
	
	public VerschlüsselteServerNachricht(long seed, String encryptedSeed, String encryptedMessage) {
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
