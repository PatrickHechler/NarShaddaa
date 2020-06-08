package de.hechler.patrick.hilfZeugs.byteweiseÜbertragen.Standard.schreiben;

public class ArraySchreiben implements SchreibenInterface {
	
	byte[] array;
	int index;
	
	public ArraySchreiben(int größe) {
		array = new byte[größe];
		index = 0;
	}
	
	@Override
	public long getGröße() {
		return index;
	}
	
	@Override
	public void schreibeByte(int schreibeByte) {
		array[index] = (byte) schreibeByte;
		index ++ ;
	}
	
	@Override
	public void close() {
		array = null;
	}
	
	public int getmaximaleGröße() {
		return array.length;
	}
	
	public byte[] getArray() {
		return array;
	}
	
	@Override
	public void schreibeBytes(byte[] bytes) throws IndexOutOfBoundsException {
		if (bytes.length + index > array.length) {
			throw new IndexOutOfBoundsException("Der zu schreibende Teil war zu groß");
		}
		for (byte dieses : bytes) {
			schreibeByte(dieses);
		}
	}
	
}
