package hr.fer.oprpp1.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class used for calculating byte and hexadecimal numbers
 * @author Vito Sabalic
 *
 */
public class Util {
	
	/**
	 * The hex digit helper array
	 */
	public static final char[] hexDigits = "0123456789abcdef".toCharArray();
	
	/**
	 * Transforms the provided byte array to a hex string
	 * @param bytearray The provided byte array
	 * @return returns the calculated hex string
	 */
	public static String bytetohex(byte[] bytearray) {
		
		
		char[] hex = new char[bytearray.length * 2];
		int index = 0;
		
		for(byte b : bytearray) {
			hex[index++] = hexDigits[(b >> 4) & 0xF];
			hex[index++] = hexDigits[b & 0xF];
		}
		
		return String.valueOf(hex);
		
	}
	
	
	/**
	 * Transforms the provided hex string to a byte array
	 * @param keyText The provided hex string
	 * @return returns the calculated byte array
	 */
	public static byte[] hextobyte(String keyText) {
		
		List<Character> digits = new ArrayList<>();
		
		for(int i = 0; i < hexDigits.length; i++) {
			digits.add(hexDigits[i]);
		}
		
		
		if(keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("The size of the key text is not even");
		}
		
		byte[] bytes = new byte[keyText.length() / 2];
		
		char[] hex = keyText.toLowerCase().toCharArray();
		int index = 0;
		
		try {
			for(int i = 0; i < hex.length; i++) {
				
				int first = digits.indexOf(hex[i]);
				int second = digits.indexOf(hex[++i]);
				
				bytes[index++] = (byte) (((first & 0xF ) << 4 ) | (second & 0xF));
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The provided key text is not valid");
		}
		
		return bytes;

	}

}
