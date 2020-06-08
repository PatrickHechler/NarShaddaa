package de.tafelwischenSecure.serverObjects;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public interface ServerObjectInterface extends Serializable {
	
	public void writeToDataStream(DataOutputStream out) throws IOException;
	
	public void print();
	
	
}
