package de.tafelwischenSecure.rsa.schlüssel.eigener;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import de.hechler.patrick.hilfZeugs.umwandeln.ZahlenUmwandeln;

public class AssymetrischEigener implements AssymetrischEigenerInterface {
	
	private PrivateKey schlüssel;
	private Cipher cipher;
	private String name;
	
	private void ciperInit() {
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, schlüssel);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AssymetrischEigener(String schlüsselAlsString) {
		this(schlüsselAlsString.split("=")[0], (schlüsselAlsString.split("=").length > 1) ? schlüsselAlsString.split("=")[1] : "?");
	}
	
	public AssymetrischEigener(PrivateKey schlüssel) {
		this(schlüssel, "?");
	}
	
	public AssymetrischEigener(String schlüsselAlsString, String neuerName) {
		byte[] bytes = ZahlenUmwandeln.hexZuByteArray(schlüsselAlsString.split("=")[0]);
		if (neuerName == null || neuerName.length() < 1) {
			neuerName = "?";
		}
		KeyFactory kf;
		try {
			kf = KeyFactory.getInstance("RSA");
			this.schlüssel = kf.generatePrivate(new PKCS8EncodedKeySpec(bytes));
			this.name = neuerName;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		
		ciperInit();
	}
	
	public AssymetrischEigener(PrivateKey schlüssel, String name) {
		if (name == null || name.length() < 1) {
			name = "?";
		}
		
		this.schlüssel = schlüssel;
		this.name = name;
		ciperInit();
	}
	
	@Override
	public long entschlüsselnLong(byte[] entschlüsseln) {
		return ZahlenUmwandeln.zuLong(entschlüsseln(entschlüsseln));
	}
	
	@Override
	public int entschlüsselnInt(byte[] entschlüsseln) {
		return ZahlenUmwandeln.zuInt(entschlüsseln(entschlüsseln));
	}
	
	@Override
	public byte[] entschlüsseln(byte[] entschlüsseln) {
		try {
			return cipher.doFinal(entschlüsseln);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] entschlüsseln(String entschlüsseln) {
		return entschlüsseln(entschlüsseln.getBytes(StandardCharsets.UTF_8));
	}
	
	@Override
	public byte[] entschlüsseln(String entschlüsseln, String charset) throws UnsupportedEncodingException {
		return entschlüsseln(entschlüsseln.getBytes(charset));
	}
	
	@Override
	public byte[] entschlüsseln(String entschlüsseln, Charset charset) {
		return entschlüsseln(entschlüsseln.getBytes(charset));
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name == null || name.length() < 1) {
			this.name = "Nope!";
			return;
		}
		this.name = name.replaceAll("=", "_").replaceAll("-", "_");
	}
	
	@Override
	public String toString() {
		return ZahlenUmwandeln.zuHex(schlüssel.getEncoded()) + "=" + name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if ( !obj.getClass().equals(AssymetrischEigener.class)) {
			return false;
		}
		
		AssymetrischEigener eigener = (AssymetrischEigener) obj;
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
