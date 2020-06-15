package hr.fer.zemris.java.custom.scripting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ValueWrapperTest {

	@Test
	public void twoNullValuesTest() {
		ValueWrapper v = new ValueWrapper(null);
		assertTrue(v.numCompare(null) == 0);
	}
	
	@Test
	public void nullAndIntegerTest() {
		ValueWrapper v = new ValueWrapper(null);
		assertTrue(v.numCompare(20) < 0);
		assertTrue(v.numCompare(-4) > 0);
		assertTrue(v.numCompare(0) == 0);
	}
	
	@Test
	public void nullAndDoubleTest() {
		ValueWrapper v = new ValueWrapper(null);
		assertTrue(v.numCompare(-3.14) > 0);
		assertTrue(v.numCompare(20.4) < 0);
		assertTrue(v.numCompare(0.00) == 0);
	}
	
	@Test
	public void nullAndStringTest() {
		ValueWrapper v = new ValueWrapper(null);
		assertTrue(v.numCompare("6") < 0);
		assertTrue(v.numCompare("-3.14") > 0);
		assertTrue(v.numCompare("0") == 0);
	}
	
	@Test
	public void twoIntegersTest() {
		ValueWrapper v = new ValueWrapper(Integer.valueOf(4));
		assertTrue(v.numCompare(4) == 0);
		assertTrue(v.numCompare(2) > 0);
		assertTrue(v.numCompare(30) < 0);
	}
	
	@Test
	public void integerAndDoubleTest() {
		ValueWrapper v = new ValueWrapper(Integer.valueOf(4));
		assertTrue(v.numCompare(4.0) == 0);
		assertTrue(v.numCompare(2.12) > 0);
		assertTrue(v.numCompare(100.01) < 0);
	}
	
	@Test
	public void integerAndStringTest() {
		ValueWrapper v = new ValueWrapper(Integer.valueOf(4));
		assertTrue(v.numCompare("4") == 0);
		assertTrue(v.numCompare("4.0") == 0);
		assertTrue(v.numCompare("2.12") > 0);
		assertTrue(v.numCompare("1.01e365") < 0);
	}
	
	@Test
	public void twoDoublesTest() {
		ValueWrapper v = new ValueWrapper(Double.valueOf(4.0));
		assertTrue(v.numCompare(4.0) == 0);
		assertTrue(v.numCompare(2.12) > 0);
		assertTrue(v.numCompare(100.01) < 0);
	}
	
	@Test
	public void doubleAndStringTest() {
		ValueWrapper v = new ValueWrapper(Double.valueOf(4.0));
		assertTrue(v.numCompare("4.0") == 0);
		assertTrue(v.numCompare("2.12") > 0);
		assertTrue(v.numCompare("1.01e365") < 0);
	}
	
	@Test
	public void addTwoNullsTest() {
		ValueWrapper v = new ValueWrapper(null);
		v.add(null);
		assertEquals(Integer.valueOf(0), v.getValue());
	}
	
	@Test
	public void addToDoubleTest() {
		ValueWrapper v = new ValueWrapper(3.25);
		v.add(null);
		assertEquals(3.25, v.getValue());
		v.add("1");
		assertEquals(4.25, v.getValue());
		v.add(2.25);
		assertEquals(6.5, v.getValue());
		v.add(10);
		assertEquals(16.5, v.getValue());
	}
	
	@Test
	public void mulWithDoubleTest() {
		ValueWrapper v = new ValueWrapper("4.00");
		v.multiply(2);
		assertEquals(8.0, v.getValue());
		v.multiply("1");
		assertEquals(8.0, v.getValue());
		v.multiply(10);
		assertEquals(80.0, v.getValue());
		v.multiply(null);
		assertEquals(0.0, v.getValue());
	}
	
	@Test
	public void mulWithIntegerTest() {
		ValueWrapper v = new ValueWrapper(2);
		v.multiply(2);
		assertEquals(4, v.getValue());
		v.multiply("1");
		assertEquals(4, v.getValue());
		v.multiply(5.0);
		assertEquals(20.0, v.getValue());
		v.multiply(null);
		assertEquals(0.0, v.getValue());
	}
	
}
