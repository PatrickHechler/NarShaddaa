package de.hechler.patrick.hilfZeugs.umwandeln;

import java.util.ArrayList;
import java.util.List;

public class ListUndArrayUmwandeln {
	
	public static String[] umwandelnString(List <String> umwandeln) {
		String[] rüStrings = new String[umwandeln.size()];
		int runde;
		for (runde = 0; runde < rüStrings.length; runde ++ ) {
			rüStrings[runde] = umwandeln.get(runde);
		}
		return rüStrings;
	}
	
	public static List <String> umwandelnString(String[] umwandeln) {
		List <String> rüStrings = new ArrayList <String>();
		for (String string : umwandeln) {
			rüStrings.add(string);
		}
		return rüStrings;
	}
	
	public static Integer[] umwandelnInteger(List <Integer> umwandeln) {
		Integer[] rüIntegers = new Integer[umwandeln.size()];
		int runde;
		for (runde = 0; runde < rüIntegers.length; runde ++ ) {
			rüIntegers[runde] = umwandeln.get(runde);
		}
		return rüIntegers;
	}
	
	public static List <Integer> umwandelnInteger(Integer[] umwandeln) {
		List <Integer> rüIntegers = new ArrayList <Integer>();
		for (Integer integer : umwandeln) {
			rüIntegers.add(integer);
		}
		return rüIntegers;
	}
	
	public static List <Integer> unwndelnInt(int[] umwandeln) {
		List <Integer> rüInts = new ArrayList <Integer>();
		for (Integer integer : umwandeln) {
			rüInts.add(integer);
		}
		return rüInts;
	}
	
	public static int[] byteZuPosInt(byte[] zeile) {
		int[] rückgabe = new int[zeile.length];
		int runde;
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = ((int) zeile[runde]) & 0xFF;
		}
		return rückgabe;
	}
	
	public static byte[] intZuByte(int[] posiBytes) {
		byte[] rückgabe = new byte[posiBytes.length];
		int runde;
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde] = (byte) posiBytes[runde];
		}
		return rückgabe;
	}
	
	public static Byte[] umwandeln(List <Byte> umwandeln) {
		Byte[] rückgabe = new Byte[umwandeln.size()];
		int runde;
		for (runde = 0; runde < rückgabe.length; runde ++ ) {
			rückgabe[runde]=umwandeln.get(runde);
		}
		return rückgabe;
	}
	
}
