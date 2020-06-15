package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	@Test
	public void constructorTest() {
		Complex c1 = new Complex(2.2, 0.5);
		assertEquals(2.2, c1.getReal());
		assertEquals(0.5, c1.getImaginary());
	}
	
	@Test
	public void fromRealTest() {
		Complex c1 = Complex.fromReal(5.6);
		assertEquals(5.6, c1.getReal());
		assertEquals(0, c1.getImaginary());
	}
	
	@Test
	public void fromImaginaryTest() {
		Complex c1 = Complex.fromImaginary(5.6);
		assertEquals(0, c1.getReal());
		assertEquals(5.6, c1.getImaginary());
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		Complex c1 = Complex.fromMagnitudeAndAngle(5, 0.927295218);
		assertTrue(Math.abs(c1.getReal()-3)<10E-6);
		assertTrue(Math.abs(c1.getImaginary()-4)<10E-6);
		assertTrue(Math.abs(c1.getMagnitude()-5)<10E-6);
		assertTrue(Math.abs(c1.getAngle()-0.927295218)<10E-6);
	}
	
	@Test
	public void addTest() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(-1, 2);
		Complex c3 = c1.add(c2);
		assertEquals(1, c3.getReal());
		assertEquals(5, c3.getImaginary());
	}
	
	@Test
	public void subTest() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(-1, 2);
		Complex c3 = c1.sub(c2);
		assertEquals(3, c3.getReal());
		assertEquals(1, c3.getImaginary());
	}
	
	@Test
	public void mulTest() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(-1, 2);
		Complex c3 = c1.mul(c2);
		assertEquals(-8, c3.getReal());
		assertEquals(1, c3.getImaginary());
	}
	
	@Test
	public void divTest() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(-1, 2);
		Complex c3 = c1.div(c2);
		assertEquals(0.8, c3.getReal());
		assertEquals(-1.4, c3.getImaginary());
	}
	
	@Test
	public void divByZeroTest() {
		Complex c1 = new Complex(2, 3);
		Complex c2 = new Complex(0, 0);
		assertThrows(IllegalArgumentException.class, () -> c1.div(c2));
	}
	
	@Test
	public void powerTest() {
		Complex c1 = new Complex(2, 3);
		Complex c3 = c1.power(4);
		assertTrue(Math.abs(c3.getReal()+119)<10E-6);
		assertTrue(Math.abs(c3.getImaginary()+120)<10E-6);
	}
	
	@Test
	public void powerOfZeroTest() {
		Complex c1 = new Complex(2, 3);
		Complex c3 = c1.power(0);
		assertEquals(1, c3.getReal());
		assertEquals(0, c3.getImaginary());
	}
	
	@Test
	public void powerOfNegativeTest() {
		Complex c1 = new Complex(2, 3);
		assertThrows(IllegalArgumentException.class, () -> c1.power(-1));
	}
	
	@Test
	public void rootTest() {
		Complex c1 = new Complex(2, 3);
		Complex[] roots = c1.root(3);
		assertEquals(3, roots.length);
		assertTrue(Math.abs(roots[0].getReal()-1.45186)<10E-5);
		assertTrue(Math.abs(roots[0].getImaginary()-0.49340)<10E-5);
		assertTrue(Math.abs(roots[1].getReal()+1.1532)<10E-5);
		assertTrue(Math.abs(roots[1].getImaginary()-1.0106)<10E-5);
		assertTrue(Math.abs(roots[2].getReal()+0.2986)<10E-5);
		assertTrue(Math.abs(roots[2].getImaginary()+1.5040)<10E-5);
	}
	
	@Test
	public void rootWithInvalidArgumentTest() {
		Complex c1 = new Complex(2, 3);
		assertThrows(IllegalArgumentException.class, () -> c1.root(0));
	}
	
	@Test
	public void getRealTest() {
		Complex c1 = new Complex(3, 4);
		assertEquals(3, c1.getReal());
	}
	
	@Test
	public void getImaginaryTest() {
		Complex c1 = new Complex(3, 4);
		assertEquals(4, c1.getImaginary());
	}
	
	@Test
	public void getMagnitudeTest() {
		Complex c1 = new Complex(4, -3);
		assertEquals(5, c1.getMagnitude());
	}
	
	@Test
	public void getAngleFirstQuadrantTest() {
		Complex c1 = new Complex(1, 1);
		assertTrue(Math.abs(c1.getAngle()-Math.PI/4)<10E-6);
	}
	
	@Test
	public void getAngleSecondQuadrantTest() {
		Complex c1 = new Complex(-1, 1);
		assertTrue(Math.abs(c1.getAngle()-3*Math.PI/4)<10E-6);
	}
	
	@Test
	public void getAngleThirdQuadrantTest() {
		Complex c1 = new Complex(-1, -1);
		assertTrue(Math.abs(c1.getAngle()-5*Math.PI/4)<10E-6);
	}
	
	@Test
	public void getAngleFourthQuadrantTest() {
		Complex c1 = new Complex(1, -1);
		assertTrue(Math.abs(c1.getAngle()-7*Math.PI/4)<10E-6);
	}
	
	@Test
	public void getAngleOfZeroTest() {
		Complex c1 = new Complex(0,0);
		assertEquals(0, c1.getAngle());
	}
	
	@Test
	public void getAngleOfITest() {
		Complex c1 = new Complex(0,1);
		assertTrue(Math.abs(c1.getAngle()-Math.PI/2)<10E-6);
	}
	
	@Test
	public void parseOnlyPositiveRealTest() {
		Complex c1 = Complex.parse("3.51");
		Complex c2 = new Complex(3.51, 0);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parseOnlyNegativeRealTest() {
		Complex c1 = Complex.parse("-3.17");
		Complex c2 = new Complex(-3.17, 0);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parseOnlyImaginaryWithNumberTest() {
		Complex c1 = Complex.parse("-2.71i");
		Complex c2 = new Complex(0, -2.71);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parseOnlyPositiveITest() {
		Complex c1 = Complex.parse("i");
		Complex c2 = new Complex(0, 1);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parseNegativeITest() {
		Complex c1 = Complex.parse("-i");
		Complex c2 = new Complex(0, -1);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parsePositiveITest() {
		Complex c1 = Complex.parse("+i");
		Complex c2 = new Complex(0, 1);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parse1Test() {
		Complex c1 = Complex.parse("1");
		Complex c2 = new Complex(1, 0);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parseGeneralCaseTest1() {
		Complex c1 = Complex.parse("-2.71-3.15i");
		Complex c2 = new Complex(-2.71, -3.15);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parseGeneralCaseTest2() {
		Complex c1 = Complex.parse("-2.71i-3.15");
		Complex c2 = new Complex(-3.15, -2.71);
		assertEquals(c2, c1);
	}
	
	@Test
	public void parseEmptyStringTest() {
		assertThrows(NumberFormatException.class, () -> Complex.parse(""));
	}
	
	@Test
	public void parseNullTest() {
		assertThrows(NullPointerException.class, () -> Complex.parse(null));
	}
	
	@Test
	public void parseDoublePlusTest() {
		assertThrows(NumberFormatException.class, () -> Complex.parse("3++6i"));
	}
	
	@Test
	public void parseStringWithWhitespacesTest() {
		assertThrows(NumberFormatException.class, () -> Complex.parse("1 + i"));
	}
	
	@Test
	public void equalsTrueTest() {
		Complex c1 = new Complex(5.33, -11.06);
		Complex c2 = new Complex(5.33, -11.06);
		assertTrue(c1.equals(c2));
	}
	
	@Test
	public void equalsFalseTest() {
		Complex c1 = new Complex(5.33, -11.06);
		Complex c2 = new Complex(5.30, -11.06);
		assertFalse(c1.equals(c2));
	}
	
	@Test
	public void hashCodeTrueTest() {
		Complex c1 = new Complex(5.33, -11.06);
		Complex c2 = new Complex(5.33, -11.06);
		assertEquals(c1.hashCode(), c2.hashCode());
	}
	
}
