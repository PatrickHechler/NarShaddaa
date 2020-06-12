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
			// Schritt 1: Einen KeyPairGenerator für eine bestimmte Art der Verschlüsselung generieren.
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
			rsaKPG.initialize(4096); // Bitlünge des Modules
			// java.security.InvalidParameterException: RSA keys must be at least 512 bits long
			// Die Grüße des Modules bestimmt die Lünge des zu verschlüsselnden Textes
			// Mit einer Bitlünge von 800 etwa kann man nur Texte bis 102 byte Lünge verschlüsseln
			
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
			
			// Schritt 4: Cipherinstanz zum gewühlten Verschlüsselungsalgorithmus erzeugen
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // NoSuchPaddingException
			
			// Schritt 5: Verschlüsselungsmodus einschalten und das in Schritt 3 erzeugte PublicKey-Objekt übergeben
			cipher.init(Cipher.ENCRYPT_MODE, publicKey); // InvalidKeyException
			// wenn man init weglüüt, dann IllegalStateException: Cipher not initialized
			
			// Schritt 6: Daten in ein Bytearray verwandeln und mit der Methode doFinal verschlüsseln
			String klarText = "-2898261312640379801";
			System.out.println("Text to encrypt:           " + klarText);
			byte[] arrayToEncrypt = klarText.getBytes();
			System.out.println("Array to Encrypt length:   " + arrayToEncrypt.length);
			byte[] encryptedArray = cipher.doFinal(klarText.getBytes()); // IllegalBlockSizeException, BadPaddingException
			System.out.println("Encrypted Array length:    " + encryptedArray.length);
			
			// verschlüsselten String ausgeben
			String encryptedString = new String(encryptedArray); //
			System.out.println("Encrypted Bytes as String: " + encryptedString);
			System.out.println();
			
			// Schritt 7: verschlüsseltes Bytearray senden...
			
			// Schritt 8: Entschlüsseln
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
