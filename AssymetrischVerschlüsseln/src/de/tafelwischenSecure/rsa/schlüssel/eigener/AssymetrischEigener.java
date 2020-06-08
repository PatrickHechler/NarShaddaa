package de.tafelwischenSecure.rsa.schl�ssel.eigener;

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
	
	private PrivateKey schl�ssel;
	private Cipher cipher;
	private String name;
	
	private void ciperInit() {
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, schl�ssel);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AssymetrischEigener(String schl�sselAlsString) {
		this(schl�sselAlsString.split("=")[0], (schl�sselAlsString.split("=").length > 1) ? schl�sselAlsString.split("=")[1] : "?");
	}
	
	public AssymetrischEigener(PrivateKey schl�ssel) {
		this(schl�ssel, "?");
	}
	
	public AssymetrischEigener(String schl�sselAlsString, String neuerName) {
		byte[] bytes = ZahlenUmwandeln.hexZuByteArray(schl�sselAlsString.split("=")[0]);
		if (neuerName == null || neuerName.length() < 1) {
			neuerName = "?";
		}
		KeyFactory kf;
		try {
			kf = KeyFactory.getInstance("RSA");
			this.schl�ssel = kf.generatePrivate(new PKCS8EncodedKeySpec(bytes));
			this.name = neuerName;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		
		ciperInit();
	}
	
	public AssymetrischEigener(PrivateKey schl�ssel, String name) {
		if (name == null || name.length() < 1) {
			name = "?";
		}
		
		this.schl�ssel = schl�ssel;
		this.name = name;
		ciperInit();
	}
	
	@Override
	public long entschl�sselnLong(byte[] entschl�sseln) {
		return ZahlenUmwandeln.zuLong(entschl�sseln(entschl�sseln));
	}
	
	@Override
	public int entschl�sselnInt(byte[] entschl�sseln) {
		return ZahlenUmwandeln.zuInt(entschl�sseln(entschl�sseln));
	}
	
	@Override
	public byte[] entschl�sseln(byte[] entschl�sseln) {
		try {
			return cipher.doFinal(entschl�sseln);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] entschl�sseln(String entschl�sseln) {
		return entschl�sseln(entschl�sseln.getBytes(StandardCharsets.UTF_8));
	}
	
	@Override
	public byte[] entschl�sseln(String entschl�sseln, String charset) throws UnsupportedEncodingException {
		return entschl�sseln(entschl�sseln.getBytes(charset));
	}
	
	@Override
	public byte[] entschl�sseln(String entschl�sseln, Charset charset) {
		return entschl�sseln(entschl�sseln.getBytes(charset));
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
		return ZahlenUmwandeln.zuHex(schl�ssel.getEncoded()) + "=" + name;
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
