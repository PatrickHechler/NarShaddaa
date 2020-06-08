package de.tafelwischenSecure.rsa.statischAlt;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

class AssymetrischVerschl�sseln {
	
	public static byte[] verschl�sseln(PublicKey publicKey, byte[] verschl�sseln)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		byte[] r�ckgabe;
		Cipher cipher;
		
		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		r�ckgabe = cipher.doFinal(verschl�sseln);
		
		return r�ckgabe;
	}
	
	public static byte[] entschl�sseln(PrivateKey privateKey, byte[] entschl�seln)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher;
		byte[] r�ckgabe;
		
		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		r�ckgabe = cipher.doFinal(entschl�seln);
		
		return r�ckgabe;
	}
	
	public static KeyPair generator(Integer schl�sselst�rke) throws NoSuchAlgorithmException {
		
		if (schl�sselst�rke == null) {
			schl�sselst�rke = 4096;
		}
		
		KeyPairGenerator rsaKPG = KeyPairGenerator.getInstance("RSA");
		rsaKPG.initialize(schl�sselst�rke);
		
		KeyPair r�ckgabe = rsaKPG.generateKeyPair();
		return r�ckgabe;
	}
	
	public static String toString(Key umwandeln) {
		String r�ckgabe = "";
		int runde;
		
		byte[] bytes = umwandeln.getEncoded();
		
		for (runde = 0; runde < bytes.length; runde ++ ) {
			r�ckgabe += byteToHex(bytes[runde]);
		}
		
		return r�ckgabe;
	}
	
	public static PublicKey generatePublicKeyFromString(String generator) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = hexToByte(generator);
		
		X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		
		return kf.generatePublic(X509publicKey);
	}
	
	public static PrivateKey generatePrivateKeyFromString(String generator) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = hexToByte(generator);
		
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(new PKCS8EncodedKeySpec(bytes));
	}
	
	static String byteToHex(byte umwandeln) {
		String r�ckgabe;
		int zwischen;
		int wandelZahl = umwandeln & 0xFF;
		
		zwischen = wandelZahl;
		
		zwischen = (zwischen >> 4) & 0xF;
		r�ckgabe = kleineZahlZuHex(zwischen);
		
		zwischen = (wandelZahl - (zwischen << 4));
		zwischen = (zwischen & 0xF);
		r�ckgabe += kleineZahlZuHex(zwischen);
		
		return r�ckgabe;
	}
	
	static byte[] hexToByte(String hexZahl) {
		int runde;
		byte[] r�ckgabe;
		
		if (hexZahl.length() % 2 != 0) {
			hexZahl = "0" + hexZahl;
		}
		r�ckgabe = new byte[hexZahl.length() / 2];
		
		for (runde = 0; runde < r�ckgabe.length; runde ++ ) {
			r�ckgabe[runde] = kleinHexToByte(hexZahl.substring(runde * 2, runde * 2 + 2));
		}
		
		return r�ckgabe;
	}
	
	static byte kleinHexToByte(String kleinHex) {
		byte r�ckgabe;
		
		if (kleinHex.length() != 2) {
			throw new RuntimeException("Die kleine HexZahl muss 2 lang sein: " + kleinHex + " ist ung�ltig!");
		}
		
		int zwichen = kleinHex.toUpperCase().charAt(0);
		if (zwichen >= '0' && zwichen <= '9') {
			zwichen += -'0';
		} else if (zwichen >= 'A' && zwichen <= 'F') {
			zwichen += -'A' + 10;
		} else {
			throw new RuntimeException("Die HexZahl darf nur aus Zahlen und den Buchstaben a-f/A-F bestehen: " + kleinHex + " ist ung�ltig!");
		}
		r�ckgabe = (byte) zwichen;
		
		zwichen = kleinHex.toUpperCase().charAt(1);
		if (zwichen >= '0' && zwichen <= '9') {
			zwichen += -'0';
		} else if (zwichen >= 'A' && zwichen <= 'F') {
			zwichen += -'A' + 10;
		} else {
			throw new RuntimeException("Die HexZahl darf nur aus Zahlen und den Buchstaben a-f/A-F bestehen: " + kleinHex + " ist ung�ltig!");
		}
		r�ckgabe = (byte) ( (r�ckgabe << 4) + zwichen);
		
		return r�ckgabe;
	}
	
	static String kleineZahlZuHex(int kleineZahl) {
		
		if (kleineZahl < 10 && kleineZahl >= 0) {
			return kleineZahl + "";
		}
		
		switch (kleineZahl) {
		case 10:
			return "A";
		case 11:
			return "B";
		case 12:
			return "C";
		case 13:
			return "D";
		case 14:
			return "E";
		case 15:
			return "F";
		}
		throw new RuntimeException("kleine Zahl muss eine einstellige HexZahl sein: " + kleineZahl + " ist ung�ltig!");
	}
	
}
