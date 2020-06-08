package de.tafelwischenSecure.rsa.schl�ssel.offen;

public interface AssymetrischOffenInterface {
	
	public byte[] verschl�sseln(long verschl�sseln);
	
	public byte[] verschl�sseln(int verschl�sseln);
	
	public byte[] verschl�sseln(byte[] verschl�sseln);
	
	public byte[] verschl�sseln(String verschl�sseln);
	
	@Override
	public String toString();
	
	public String getName();
	
	void setName(String name);
	
	@Override
	boolean equals(Object obj);
	
}
