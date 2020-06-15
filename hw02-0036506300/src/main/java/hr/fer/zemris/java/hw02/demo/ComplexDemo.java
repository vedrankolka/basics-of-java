package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.Complex;

/**
 * Demonstrates the use of ComplexNumber class on a few basic examples.
 * @author Vedran Kolka
 *
 */
public class ComplexDemo {

	public static void main(String[] args) {
		Complex c1 = new Complex(2, 3);
		Complex c2 = Complex.parse("2.5-3i");
		Complex c3 = c1.add(Complex.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}
	
}
