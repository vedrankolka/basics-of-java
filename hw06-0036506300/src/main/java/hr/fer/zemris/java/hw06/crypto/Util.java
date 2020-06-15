package hr.fer.zemris.java.hw06.crypto;

/**
 * A class which provides methods for converting hexadecimal strings
 * to bytes and vice versa.
 * @author Vedran Kolka
 *
 */
public class Util {
	
	private static final byte LETTER_OFFSET = 87;
	private static final byte DIGIT_OFFSET = 48;
	private static final byte F = 0b1111;

	/**
	 * Converts the given string into an array of bytes whose values represent
	 * the given strings hexadecimal value.
	 * @param keyText
	 * @return byteArray
	 * @throws IllegalArgumentException if a character is not a hexadecimal symbol
	 */
	public static byte[] hextobyte(String keyText) {
		char[] characters = keyText.toLowerCase().toCharArray();
		int length = characters.length;
		byte[] byteArray = new byte[length/2 + length%2];
		
		if(length == 0) {
			return byteArray;
		}
		int start = 0;
		if(length % 2 != 0) {
			byteArray[0] = getByte(characters[0]);
			start++;
		}
		for(int i = start, j = start ; i<length ; i += 2) {
			byte b1 = getByte(characters[i]);
			byte b2 = getByte(characters[i+1]);
			byteArray[j++] = merge(b1, b2);
		}
		return byteArray;
	}
	
	/**
	 * Converts the given byteArray to a string representation of the given <code>byteArray</code>
	 * in hexadecimal symbols and returns it (in lowercase letters).
	 * @param byteArray
	 * @return string representing the byteArray value in hexadecimal symbols
	 */
	public static String bytetohex(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for(byte b : byteArray) {
			byte[] twoBytes = split(b);
			char c1 = getChar(twoBytes[0]);
			char c2 = getChar(twoBytes[1]);
			sb.append(c1).append(c2);
		}
		return sb.toString();
	}

	/**
	 * Converts the given byte <code>b</code> to a character which represents the byte
	 * with a hexadecimal symbol.
	 * @param b
	 * @return hexadecimal symbol representing the given <code>b</code> value
	 */
	static char getChar(byte b) {
		return (char)(b + ( b > 9 ? LETTER_OFFSET : DIGIT_OFFSET));
	}

	/**
	 * Splits the given byte <code>b</code> into two bytes as showed in the example: 
	 * byte xxxxyyyy -> 0000xxxx, 0000yyyy . Returns them in a byte array.
	 * @param b = xxxxyyyy
	 * @return array of two bytes {0000xxxx, 0000yyyy}
	 */
	static byte[] split(byte b) {
		byte b1 = (byte) ((0b11110000 & b) >>> 4);
		byte b2 = (byte) (0b00001111 & b);
		byte[] pair = {b1, b2};
		return pair;
	}

	/**
	 * Rotates b1 to the left to make room for b2, then returns the result of or operation
	 * of the two bytes.
	 * Bytes b1 and b2 are expected to have values only in the last four bits  
	 * @param b1
	 * @param b2
	 * @return b1*16+b2
	 */
	static byte merge(byte b1, byte b2) {
		return (byte)( b1<<4 | b2);
	}

	/**
	 * Converts the given character into a byte.
	 * Expects digits and lowercase letters.
	 * @param c
	 * @return byte value of the given char
	 * @throws IllegalArgumentException if <code>c</code> is not an expected character
	 */
	static byte getByte(char c) {
		byte b = (byte)c;

		if(Character.isLetter(c)) {
			b -= LETTER_OFFSET;
			if(b > F) {
				throw new IllegalArgumentException("Character '" + c +
						"' is not a valid hexadecimal character.");
			}
			if(b < 0) {
				throw new IllegalArgumentException("A lowercase character was expected.");
			}
			return b;
		}
		if(Character.isDigit(c)) {
			b -= DIGIT_OFFSET;
			return b;
		}
		//if it is neither, it is not valid
		throw new IllegalArgumentException("Character '" + c + "' is not a valid hexacedimal character." );
	}
	
}
