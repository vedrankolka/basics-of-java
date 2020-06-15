package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw06.crypto.Util.*;

public class UtilTest {

	@Test
	public void mergeTest() {
		byte b1 = 0b10;
		byte b2 = 0b10;
		byte result = merge(b1, b2);
		assertEquals(0b100010, result);
	}
	
	@Test
	public void getByteValidLetterTest() {
		assertEquals(0b1111, getByte('f'));
		assertEquals(0b1010, getByte('a'));
		assertEquals(0b1011, getByte('b'));
	}
	
	@Test
	public void getByteDigitTest() {
		assertEquals(0b0000, getByte('0'));
		assertEquals(0b0001, getByte('1'));
		assertEquals(0b1001, getByte('9'));
	}
	
	@Test
	public void hextobyteEvenStringLengthTest() {
		byte[] byteArray = hextobyte("01Ae22");
		assertEquals(1, byteArray[0]);
		assertEquals(-82, byteArray[1]);
		assertEquals(34, byteArray[2]);
	}
	
	@Test
	public void hextobyteUnevenStringLengthTest() {
		byte[] byteArray = hextobyte("1Ae22");
		assertEquals(1, byteArray[0]);
		assertEquals(-82, byteArray[1]);
		assertEquals(34, byteArray[2]);
	}
	
	@Test
	public void getCharTest() {
		assertEquals('a', getChar((byte)0b1010));
		assertEquals('b', getChar((byte)0b1011));
		assertEquals('f', getChar((byte)0b1111));
		assertEquals('9', getChar((byte)0b1001));
		assertEquals('0', getChar((byte)0b0000));
	}
	
	@Test
	public void bytetohexTest() {
		byte[] bytes = { 1, -82, 34 };
		assertEquals("01ae22", bytetohex(bytes));
	}
	
	@Test
	public void convertBackAndForthTest() {
		String hex = "2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598";
		byte[] bytes = hextobyte(hex);
		String hexReconstructed = bytetohex(bytes);
		assertEquals(hex, hexReconstructed);
		byte[] bytesReconstructed = hextobyte(hexReconstructed);
		assertEquals(bytes.length, bytesReconstructed.length);
		for( int i = 0 ; i<bytes.length ; ++i ) {
			assertEquals(bytes[i], bytesReconstructed[i]);
		}
	}
	
}
