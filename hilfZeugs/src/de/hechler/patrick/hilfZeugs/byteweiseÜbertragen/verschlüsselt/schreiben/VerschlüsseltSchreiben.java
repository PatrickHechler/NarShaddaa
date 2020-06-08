package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.schreiben;

import java.io.IOException;

import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben.SchreibenInterface;
import de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.verschlüsselt.verschlüsselungsZahlen.VerschlüsselZahlen;

public class VerschlüsseltSchreiben implements VerschlüsseltSchreibenInterface {
	
	protected VerschlüsselZahlen verschlüsselZahlen;
	protected SchreibenInterface schreiber;
	
	public VerschlüsseltSchreiben(SchreibenInterface schreiber, long seed) {
		this.schreiber = schreiber;
		verschlüsselZahlen = new VerschlüsselZahlen(seed);
	}
	
	public VerschlüsseltSchreiben(SchreibenInterface schreiber) {
		this.schreiber = schreiber;
		verschlüsselZahlen = new VerschlüsselZahlen();
	}
	
	@Override
	public long getGröße() {
		return schreiber.getGröße();
	}
	
	@Override
	public void schreibeByte(int schreibeByte) throws IOException {
		schreibeByte = schreibeByte ^ verschlüsselZahlen.getNächstesByte();
		schreiber.schreibeByte(schreibeByte);
	}
	
	@Override
	public void schreibeBytes(byte[] byes) throws IOException {
		for (byte dieses : byes) {
			schreibeByte(dieses);
		}
	}
	
	@Override
	public long getVerschlüsselnungsZahl() {
		return verschlüsselZahlen.getVerschlüsselnungsZahl();
	}
	
	@Override
	public void close() {
		verschlüsselZahlen.close();
		schreiber.close();
	}
	
}
