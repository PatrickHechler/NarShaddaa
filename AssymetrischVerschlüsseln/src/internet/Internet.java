package internet;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Internet {
	
	public static void main(String[] args) {
		rsa();
	}
	
	private static void rsa() {
		try {
			// Schritt 1: Einen KeyPairGenerator f�r eine bestimmte Art der Verschl�sselung generieren.
			// caseINsensitiv
			KeyPairGenerator rsaKPG = KeyPairGenerator.getInstance("RSA"); // NoSuchAlgorithmException
			// Infos
			System.out.println("Info KeyPairGenerator");
			System.out.println("Class:     " + rsaKPG); // java.security.KeyPairGenerator$Delegate
			System.out.println("Algorithm: " + rsaKPG.getAlgorithm()); // RSA
			System.out.println("Provider:  " + rsaKPG.getProvider()); // SunRsaSign version 1.8
			System.out.println();
			
			// Schritt 2: Den KeyPairGenerator initialisieren mit der Methode initialize()
			// es gibt 4 initialize()-Methoden, hier wird die einfachste verwendet
			// Methode kann entfallen, dann wird als Standardwert 1024 genommen
			rsaKPG.initialize(4096); // Bitl�nge des Modules
			// java.security.InvalidParameterException: RSA keys must be at least 512 bits long
			// Die Gr��e des Modules bestimmt die L�nge des zu verschl�sselnden Textes
			// Mit einer Bitl�nge von 800 etwa kann man nur Texte bis 102 byte L�nge verschl�sseln
			
			// Schritt 3a: KeyPair erzeugen
			KeyPair kp = rsaKPG.generateKeyPair();
			
			// Schritt 3b: PublicKey erzeugen
			PublicKey publicKey = kp.getPublic();
			System.out.println("Info PublicKey");
			System.out.println("Class:     " + publicKey.getClass()); // sun.security.rsa.RSAPublicKeyImpl
			System.out.println("toString:  " + publicKey);
			System.out.println("Algorithm: " + publicKey.getAlgorithm()); // RSA
			System.out.println("Format:    " + publicKey.getFormat()); // X.509
			System.out.println();
			
			// Schritt 3c: PrivateKey erzeugen
			PrivateKey privateKey = kp.getPrivate();
			System.out.println("Info PrivateKey");
			System.out.println("Class:     " + privateKey); // sun.security.rsa.RSAPrivateCrtKeyImpl
			
			System.out.println("Format:    " + privateKey.getFormat()); // PKCS#8
			System.out.println();
			
			// Schritt 4: Cipherinstanz zum gew�hlten Verschl�sselungsalgorithmus erzeugen
			Cipher cipher = Cipher.getInstance("RSA"); // NoSuchPaddingException
			
			// Schritt 5: Verschl�sselungsmodus einschalten und das in Schritt 3 erzeugte PublicKey-Objekt �bergeben
			cipher.init(Cipher.ENCRYPT_MODE, publicKey); // InvalidKeyException
			// wenn man init wegl��t, dann IllegalStateException: Cipher not initialized
			
			// Schritt 6: Daten in ein Bytearray verwandeln und mit der Methode doFinal verschl�sseln
			String klarText = "-2898261312640379801";
			System.out.println("Text to encrypt:           " + klarText);
			byte[] arrayToEncrypt = klarText.getBytes();
			System.out.println("Array to Encrypt length:   " + arrayToEncrypt.length);
			byte[] encryptedArray = cipher.doFinal(klarText.getBytes()); // IllegalBlockSizeException, BadPaddingException
			System.out.println("Encrypted Array length:    " + encryptedArray.length);
			
			// verschl�sselten String ausgeben
			String encryptedString = new String(encryptedArray); //
			System.out.println("Encrypted Bytes as String: " + encryptedString);
			System.out.println();
			
			// Schritt 7: verschl�sseltes Bytearray senden...
			
			// Schritt 8: Entschl�sseln
			cipher.init(Cipher.DECRYPT_MODE, privateKey); // InvalidKeyException
			byte[] decryptedArray = cipher.doFinal(encryptedArray); // IllegalBlockSizeException, BadPaddingException
			String decryptedString = new String(decryptedArray);
			System.out.println("Info Decrypt");
			System.out.println("Decrypted Array length: " + decryptedArray.length);
			System.out.println("Decrypted String:       " + decryptedString);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
			ex.printStackTrace();
		}
	}
	
}
