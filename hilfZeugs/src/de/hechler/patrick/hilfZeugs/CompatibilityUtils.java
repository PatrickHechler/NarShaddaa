package de.hechler.patrick.hilfZeugs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

public class CompatibilityUtils {
	
	public static byte[] readNBytes(InputStream input, int maxBytes) throws IOException {
		byte[] result = new byte[maxBytes];
		int filled = 0;
		int cnt = input.read(result);
		while (cnt != -1) {
			if (filled == maxBytes) {
				break;
			}
			cnt = input.read(result,  filled,  maxBytes-filled);
		}
		if (filled < maxBytes) {
			result = Arrays.copyOf(result, filled);
		}
		return result;
	}

	public static byte[] readAllBytes(InputStream input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int cnt = input.read(buffer);
		while (cnt != -1) {
			baos.write(buffer, 0, cnt);
			cnt = input.read(buffer);
		}
		return baos.toByteArray();
	}
	
	public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : Objects.requireNonNull(defaultObj, "defaultObj");
    }	
	
}
