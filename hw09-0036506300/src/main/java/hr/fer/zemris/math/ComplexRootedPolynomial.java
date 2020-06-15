package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A model of a polynomial of complex numbers written in the form with explicit
 * nullpoints: f(z) = z0 * (z - z1) * ... * (z - zn)
 * 
 * @author Vedran Kolka
 *
 */
public class ComplexRootedPolynomial {

	private List<Complex> nullpoints;

	private Complex constant;

	/**
	 * @param constant z0
	 * @param roots    z1, ..., zn
	 * @throws NullPointerException if the constant or a root is <code>null</code>
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = Objects.requireNonNull(constant);
		nullpoints = new ArrayList<>();
		for (Complex c : roots) {
			nullpoints.add(Objects.requireNonNull(c));
		}
	}

	/**
	 * Computes the value of the polynomial at given point <code>z</code>
	 * 
	 * @param z
	 * @return value of the polynomial at the given point
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (Complex zn : nullpoints) {
			result = z.sub(zn).multiply(result);
			// if the result is zero, there is no need to compute any more
			if (result.equals(Complex.ZERO))
				break;
		}

		return result;
	}

	/**
	 * Transforms <code>this</code> polynomial to an equivalent ComplexPolynom.
	 * 
	 * @return ComplexPolynom that is the equivalent of <code>this</code>
	 *         ComplexRootedPolynom
	 */
	public ComplexPolynomial toComplexPolynom() {
		
		ComplexPolynomial result = new ComplexPolynomial(constant);
		
		for(Complex z : nullpoints) {
			ComplexPolynomial c = new ComplexPolynomial(z.negate(), Complex.ONE);
			result = result.multiply(c);
		}
		
		return result;
	}

	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		// first set the minimum to the first difference and change the index if necessary
		double min = nullpoints.get(0).sub(z).module();
		if(min <= threshold) index = 0;
		// search for a closer root
		for (int i = 1; i < nullpoints.size(); ++i) {
			double module = nullpoints.get(i).sub(z).module();
			if (module > threshold)
				continue;
			if(module < min) {
				min = module;
				index = i;
			}
		}

		return index;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(constant.toString());

		for (Complex c : nullpoints) {
			sb.append("*(z - " + c + ")");
		}

		return sb.toString();
	}
	
	/**
	 * Returns the order of the polynomial.
	 * 
	 * @return order of the polynomial
	 */
	public short order() {
		for (int i = nullpoints.size() - 1; i > 0; --i) {
			if (!nullpoints.get(i).equals(Complex.ZERO))
				return (short) i;
		}
		return 0;
	}

}
