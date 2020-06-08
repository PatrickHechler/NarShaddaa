package de.tafelwischenSecure.rsa.schlüssel;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import de.tafelwischenSecure.rsa.schlüssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schlüssel.offen.AssymetrischOffen;

public class AssymetrischPaar implements AssymetrischPaarInterface {
	
	private AssymetrischEigener eigener;
	private AssymetrischOffen offen;
	
	public AssymetrischPaar() {
		KeyPair paar = generiereNeuesPaar(4096);
		generiereVomPaar(paar);
	}
	
	public AssymetrischPaar(int schlüsselstärke) {
		KeyPair paar = generiereNeuesPaar(schlüsselstärke);
		generiereVomPaar(paar);
	}
	
	private void generiereVomPaar(KeyPair paar) {
		eigener = new AssymetrischEigener(paar.getPrivate());
		offen = new AssymetrischOffen(paar.getPublic());
	}
	
	private KeyPair generiereNeuesPaar(int schlüsselstärke) {
		KeyPairGenerator rsaKPG;
		try {
			rsaKPG = KeyPairGenerator.getInstance("RSA");
			rsaKPG.initialize(schlüsselstärke);
			
			KeyPair rückgabe = rsaKPG.generateKeyPair();
			return rückgabe;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AssymetrischPaar(PrivateKey privaterSchlüssel, PublicKey öffentlicherSchlüssel) {
		eigener = new AssymetrischEigener(privaterSchlüssel);
		offen = new AssymetrischOffen(öffentlicherSchlüssel);
	}
	
	public AssymetrischPaar(KeyPair schlüsselPaar) {
		eigener = new AssymetrischEigener(schlüsselPaar.getPrivate());
		offen = new AssymetrischOffen(schlüsselPaar.getPublic());
	}
	
	public AssymetrischPaar(String paarAlsString) {
		String[] stringKeys = paarAlsString.split("-");
		eigener = new AssymetrischEigener(stringKeys[0]);
		offen = new AssymetrischOffen(stringKeys[1]);
	}
	
	@Override
	public long entschlüsselnLong(byte[] entschlüsseln) {
		return eigener.entschlüsselnLong(entschlüsseln);
	}
	
	@Override
	public byte[] entschlüsseln(byte[] entschlüsseln) {
		return eigener.entschlüsseln(entschlüsseln);
	}
	
	@Override
	public byte[] entschlüsseln(String entschlüsseln) {
		return eigener.entschlüsseln(entschlüsseln);
	}
	
	@Override
	public byte[] entschlüsseln(String entschlüsseln, String charset) throws UnsupportedEncodingException {
		return eigener.entschlüsseln(entschlüsseln, charset);
	}
	
	@Override
	public byte[] entschlüsseln(String entschlüsseln, Charset charset) {
		return eigener.entschlüsseln(entschlüsseln, charset);
	}
	
	@Override
	public int entschlüsselnInt(byte[] entschlüsseln) {
		return eigener.entschlüsselnInt(entschlüsseln);
	}
	
	@Override
	public byte[] verschlüsseln(int verschlüsseln) {
		return offen.verschlüsseln(verschlüsseln);
	}
	
	@Override
	public byte[] verschlüsseln(byte[] verschlüsseln) {
		return offen.verschlüsseln(verschlüsseln);
	}
	
	@Override
	public byte[] verschlüsseln(long verschlüsseln) {
		return offen.verschlüsseln(verschlüsseln);
	}
	
	@Override
	public byte[] verschlüsseln(String verschlüsseln) {
		return offen.verschlüsseln(verschlüsseln);
	}
	
	@Override
	public AssymetrischEigener getEigenen() {
		return eigener;
	}
	
	@Override
	public AssymetrischOffen getOffen() {
		return offen;
	}
	
	@Override
	public String getEigenenAlsString() {
		return eigener.toString();
	}
	
	@Override
	public String getOffenAlsString() {
		return offen.toString();
	}
	
	@Override
	public String toString() {
		return eigener.toString() + "-" + offen.toString();
	}
	
	public String getName() {
		return eigener.getName();
	}
	
	public void setName(String name) {
		eigener.setName(name.replaceAll("-", "_"));
		offen.setName(name.replaceAll("-", "_"));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if ( !obj.getClass().equals(AssymetrischPaar.class)) {
			return false;
		}
		
		AssymetrischPaar paar;
		try {
			paar = (AssymetrischPaar) obj;
		} catch (Throwable e) {
			return false;
		}
		AssymetrischEigener ei = paar.getEigenen();
		if (ei == null ^ eigener == null) {
			return false;
		}
		if (eigener != null) {
			if ( !eigener.equals(ei)) {
				return false;
			}
		}
		
		AssymetrischOffen of = paar.getOffen();
		if (of == null ^ offen == null) {
			return false;
		}
		if (offen != null) {
			if ( !offen.equals(of)) {
				return false;
			}
		}
		
		return true;
	}
	
}
