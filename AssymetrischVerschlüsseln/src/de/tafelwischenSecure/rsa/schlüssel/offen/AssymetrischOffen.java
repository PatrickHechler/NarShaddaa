package de.tafelwischenSecure.rsa.schlüssel.offen;

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
	
	private PublicKey schlüssel;
	private Cipher cipher;
	private String name;
	
	public AssymetrischOffen(PublicKey schlüssel) {
		this.schlüssel = schlüssel;
		name = "?";
		ciperInit();
	}
	
	public AssymetrischOffen(String schlüsselAlsString) {
		String[] schlüsselString = schlüsselAlsString.split("=");
		String stringKey = schlüsselString[0];
		byte[] bytes = ZahlenUmwandeln.hexZuByteArray(stringKey);
		
		if (schlüsselString.length > 1) {
			name = schlüsselString[1];
		} else {
			name = "?";
		}
		
		X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(bytes);
		KeyFactory kf;
		try {
			kf = KeyFactory.getInstance("RSA");
			
			schlüssel = kf.generatePublic(X509publicKey);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		
		ciperInit();
	}
	
	private void ciperInit() {
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, schlüssel);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] verschlüsseln(int verschlüsseln) {
		return verschlüsseln(ZahlenUmwandeln.zuByteArr(verschlüsseln));
	}
	
	@Override
	public byte[] verschlüsseln(byte[] verschlüsseln) {
		try {
			return cipher.doFinal(verschlüsseln);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] verschlüsseln(long verschlüsseln) {
		return verschlüsseln(ZahlenUmwandeln.zuByteArr(verschlüsseln));
	}
	
	@Override
	public byte[] verschlüsseln(String verschlüsseln) {
		return verschlüsseln(verschlüsseln.getBytes());
	}
	
	@Override
	public String toString() {
		return ZahlenUmwandeln.zuHex(schlüssel.getEncoded()) + "=" + name;
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
		
		if (schlüssel == null ^ eigener.schlüssel == null) {
			return false;
		}
		if (schlüssel != null) {
			if ( !schlüssel.equals(eigener.schlüssel)) {
				return false;
			}
		}
		
		return true;
	}
	
}
