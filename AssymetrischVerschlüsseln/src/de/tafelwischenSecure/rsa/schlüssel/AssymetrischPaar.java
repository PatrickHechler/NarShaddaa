package de.tafelwischenSecure.rsa.schl�ssel;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import de.tafelwischenSecure.rsa.schl�ssel.eigener.AssymetrischEigener;
import de.tafelwischenSecure.rsa.schl�ssel.offen.AssymetrischOffen;

public class AssymetrischPaar implements AssymetrischPaarInterface {
	
	private AssymetrischEigener eigener;
	private AssymetrischOffen offen;
	
	public AssymetrischPaar() {
		KeyPair paar = generiereNeuesPaar(4096);
		generiereVomPaar(paar);
	}
	
	public AssymetrischPaar(int schl�sselst�rke) {
		KeyPair paar = generiereNeuesPaar(schl�sselst�rke);
		generiereVomPaar(paar);
	}
	
	private void generiereVomPaar(KeyPair paar) {
		eigener = new AssymetrischEigener(paar.getPrivate());
		offen = new AssymetrischOffen(paar.getPublic());
	}
	
	private KeyPair generiereNeuesPaar(int schl�sselst�rke) {
		KeyPairGenerator rsaKPG;
		try {
			rsaKPG = KeyPairGenerator.getInstance("RSA");
			rsaKPG.initialize(schl�sselst�rke);
			
			KeyPair r�ckgabe = rsaKPG.generateKeyPair();
			return r�ckgabe;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AssymetrischPaar(PrivateKey privaterSchl�ssel, PublicKey �ffentlicherSchl�ssel) {
		eigener = new AssymetrischEigener(privaterSchl�ssel);
		offen = new AssymetrischOffen(�ffentlicherSchl�ssel);
	}
	
	public AssymetrischPaar(KeyPair schl�sselPaar) {
		eigener = new AssymetrischEigener(schl�sselPaar.getPrivate());
		offen = new AssymetrischOffen(schl�sselPaar.getPublic());
	}
	
	public AssymetrischPaar(String paarAlsString) {
		String[] stringKeys = paarAlsString.split("-");
		eigener = new AssymetrischEigener(stringKeys[0]);
		offen = new AssymetrischOffen(stringKeys[1]);
	}
	
	@Override
	public long entschl�sselnLong(byte[] entschl�sseln) {
		return eigener.entschl�sselnLong(entschl�sseln);
	}
	
	@Override
	public byte[] entschl�sseln(byte[] entschl�sseln) {
		return eigener.entschl�sseln(entschl�sseln);
	}
	
	@Override
	public byte[] entschl�sseln(String entschl�sseln) {
		return eigener.entschl�sseln(entschl�sseln);
	}
	
	@Override
	public byte[] entschl�sseln(String entschl�sseln, String charset) throws UnsupportedEncodingException {
		return eigener.entschl�sseln(entschl�sseln, charset);
	}
	
	@Override
	public byte[] entschl�sseln(String entschl�sseln, Charset charset) {
		return eigener.entschl�sseln(entschl�sseln, charset);
	}
	
	@Override
	public int entschl�sselnInt(byte[] entschl�sseln) {
		return eigener.entschl�sselnInt(entschl�sseln);
	}
	
	@Override
	public byte[] verschl�sseln(int verschl�sseln) {
		return offen.verschl�sseln(verschl�sseln);
	}
	
	@Override
	public byte[] verschl�sseln(byte[] verschl�sseln) {
		return offen.verschl�sseln(verschl�sseln);
	}
	
	@Override
	public byte[] verschl�sseln(long verschl�sseln) {
		return offen.verschl�sseln(verschl�sseln);
	}
	
	@Override
	public byte[] verschl�sseln(String verschl�sseln) {
		return offen.verschl�sseln(verschl�sseln);
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
