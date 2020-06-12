package de.tafelwischenSecure.rsa.schlüssel.offen;

public interface AssymetrischOffenInterface {
	
	public byte[] verschlüsseln(long verschlüsseln);
	
	public byte[] verschlüsseln(int verschlüsseln);
	
	public byte[] verschlüsseln(byte[] verschlüsseln);
	
	public byte[] verschlüsseln(String verschlüsseln);
	
	@Override
	public String toString();
	
	public String getName();
	
	void setName(String name);
	
	@Override
	boolean equals(Object obj);
	
}
