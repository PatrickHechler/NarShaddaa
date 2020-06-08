package de.hechler.patrick.hilfZeugs;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.lesen.SocketLesen;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Socket.senden.SocketSenden;

class SocketBenutzen implements Runnable {
	
	public static void main(String[] args) throws IOException {
		
		int runde;
		
		new Thread(new SocketBenutzen()).start();
		
		SocketSenden sender = new SocketSenden("localhost", 8080);
		
		for (runde = 0; runde < args.length; runde ++ ) {
			
			sender.schreibeByte(Byte.parseByte(args[runde]));
			
		}
		
		sender.close();
		
	}
	
	@Override
	public void run() {
		try {
			// Wartet etwas,damit der Sender einen Kanal öffnen kann, welchen dieser Akzeptieren kann.
			Thread.sleep(100);
			SocketLesen empfänger = new SocketLesen(8080);
			
			Integer übergabe = 0;
			while (übergabe != null) {
				
				übergabe = empfänger.getNächstesByte();
				
				System.out.println(übergabe);
				
			}
			empfänger.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
