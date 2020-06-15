package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FactorialTest {

	@Test
	public void faktorijelaOd6() {
		double d = Factorial.factorial(6);
		assertEquals(d, 720, "6! nije " + d);
	}
	
	@Test
	public void faktorijelaOd0() {
		double d = Factorial.factorial(0);
		assertEquals(d, 1, "0! nije " + d);
	}
	
	@Test
	public void faktorijelaOdMinus1(){
		assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-1));
	}
	
	@Test
	public void faktorijelaOd170() {
		double d = Factorial.factorial(170);
		assertEquals(d, 7.257415615307994E306);
	}
	
	@Test
	public void faktorijelaOd171() {
		assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(171));
	}
	
}
