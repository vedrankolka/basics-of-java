package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A model of a complex polynomial written in a form of a sum of the complex
 * number z to the power of n multiplied with the respective factor.
 * 
 * @author Vedran Kolka
 *
 */
public class ComplexPolynomial {
	/**
	 * Factors of the respective complex numbers in the polynomial. Factor at index
	 * 0 is the one multiplied with z^0, and the nth factor is the one multiplied
	 * with z^n
	 */
	private List<Complex> factors;

	/**
	 * @param factors
	 * @throws NullPointerException     if a factor is <code>null</code>
	 * @throws IllegalArgumentException if no factors are given
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors.length == 0) {
			throw new IllegalArgumentException("At least one factor must be given.");
		}
		this.factors = new ArrayList<>();
		for (Complex c : factors) {
			this.factors.add(Objects.requireNonNull(c));
		}
	}

	/**
	 * Returns the order of the polynomial.
	 * 
	 * @return order of the polynomial
	 */
	public short order() {
		for (int i = factors.size() - 1; i > 0; --i) {
			if (!factors.get(i).equals(Complex.ZERO))
				return (short) i;
		}
		return 0;
	}

	/**
	 * Multiplies <code>this</code> polynomial with the given polynomial
	 * <code>p</code>.
	 * 
	 * @param p
	 * @return new polynomial that is the result of multiplying <code>this</code>
	 *         with <code>p</code>
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.factors.size() + p.factors.size() - 1];
		for (int i = 0; i < this.factors.size(); ++i) {
			for (int j = 0; j < p.factors.size(); ++j) {
				if (newFactors[i + j] != null) {
					newFactors[i + j] = this.factors.get(i).multiply(p.factors.get(j)).add(newFactors[i + j]);
				} else {
					newFactors[i + j] = this.factors.get(i).multiply(p.factors.get(j));
				}
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Derives <code>this</code> polynomial and returns the result as a new
	 * polynomial.
	 * 
	 * @return derived polynomial of <code>this</code>
	 */
	public ComplexPolynomial derive() {
		Complex[] derivedFactors = new Complex[factors.size() - 1];
		for (int i = 0; i < derivedFactors.length; i++) {
			derivedFactors[i] = factors.get(i + 1).scale(i + 1);
		}
		return new ComplexPolynomial(derivedFactors);
	}

	/**
	 * Computes the polynomial value in the given point <code>z</code>.
	 * 
	 * @param z - the point in which the value is computed
	 * @return a new Complex that is the result of computing
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.size(); ++i) {
			result = z.power(i).multiply(factors.get(i)).add(result);
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// append all factors with the respective power of z
		for (int i = factors.size() - 1; i > 0; i--) {
			sb.append(factors.get(i)+ "*z^" + i + " + ");
		}
		// append the factor of z^0
		sb.append(factors.get(0).toString());
		return sb.toString();
	}

}
