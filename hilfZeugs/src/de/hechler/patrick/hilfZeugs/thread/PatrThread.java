package de.hechler.patrick.hilfZeugs.thread;

public abstract class PatrThread extends Thread {
	
//	@SuppressWarnings("deprecation")
	public void kill() throws Throwable {
		this.finalize();
	}
	
}
