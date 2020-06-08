package de.tafelwischenSecure.rsa.schl�ssel.offen;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;

public class AssymetrischOffen implements AssymetrischOffenInterface {
	
	private PublicKey schl�ssel;
	private Cipher cipher;
	private String name;
	
	public AssymetrischOffen(PublicKey schl�ssel) {
		this.schl�ssel = schl�ssel;
		name = "?";
		ciperInit();
	}
	
	public AssymetrischOffen(String schl�sselAlsString) {
		String[] schl�sselString = schl�sselAlsString.split("=");
		String stringKey = schl�sselString[0];
		byte[] bytes = ZahlenUmwandeln.hexZuByteArray(stringKey);
		
		if (schl�sselString.length > 1) {
			name = schl�sselString[1];
		} else {
			name = "?";
		}
		
		X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(bytes);
		KeyFactory kf;
		try {
			kf = KeyFactory.getInstance("RSA");
			
			schl�ssel = kf.generatePublic(X509publicKey);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		
		ciperInit();
	}
	
	private void ciperInit() {
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, schl�ssel);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] verschl�sseln(int verschl�sseln) {
		return verschl�sseln(ZahlenUmwandeln.zuByteArr(verschl�sseln));
	}
	
	@Override
	public byte[] verschl�sseln(byte[] verschl�sseln) {
		try {
			return cipher.doFinal(verschl�sseln);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] verschl�sseln(long verschl�sseln) {
		return verschl�sseln(ZahlenUmwandeln.zuByteArr(verschl�sseln));
	}
	
	@Override
	public byte[] verschl�sseln(String verschl�sseln) {
		return verschl�sseln(verschl�sseln.getBytes());
	}
	
	@Override
	public String toString() {
		return ZahlenUmwandeln.zuHex(schl�ssel.getEncoded()) + "=" + name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		if (name == null) {
			this.name = "Nope!";
			return;
		}
		this.name = name.replaceAll("=", "_").replaceAll("-", "_");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if ( !obj.getClass().equals(AssymetrischOffen.class)) {
			return false;
		}
		
		AssymetrischOffen eigener = (AssymetrischOffen) obj;
		if (eigener.name == null ^ name == null) {
			return false;
		}
		if (name != null) {
			if ( !name.equals(eigener.name)) {
				return false;
			}
		}
		
		if (schl�ssel == null ^ eigener.schl�ssel == null) {
			return false;
		}
		if (schl�ssel != null) {
			if ( !schl�ssel.equals(eigener.schl�ssel)) {
				return false;
			}
		}
		
		return true;
	}
	
}
