package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple model of an unmodifiable complex number with defined standard
 * arithmetic operations amongst complex numbers.
 * 
 * @author Vedran Kolka
 *
 */
public class Complex {

	private static final double DELTA = 10e-6;
	/**
	 * Real part of the ComplexNumber.
	 */
	private final double real;
	/**
	 * Imaginary part of the ComplexNumber.
	 */
	private final double imaginary;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Creates a Complex number with the given <code>real</code> and
	 * <code>imaginary</code> parts.
	 * 
	 * @param real
	 * @param imaginary
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Calculates and returns the module of <code>this</code> Complex number.
	 * 
	 * @return modue of <code>this</code> Complex number
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Returns the angle theta from the conversion of rectangular coordinates
	 * (<code>real</code>, <code>imaginary</code>) to polar coordinates (r, theta).
	 * 
	 * @return angle of this Complex number [0, 2pi> in radians
	 */
	public double angle() {
		double angle = Math.atan2(imaginary, real);
		return angle >= 0 ? angle : angle + 2 * Math.PI;
	}

	/**
	 * Multiplies Complex numbers <code>this</code> and <code>c</code>. Returns a
	 * new Complex number as the result.
	 * 
	 * @param c
	 * @return z = this * c
	 */
	public Complex multiply(Complex c) {
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.real * c.imaginary + this.imaginary * c.real;
		return new Complex(real, imaginary);
	}

	/**
	 * Divides Complex number <code>this</code> by Complex number <code>c</code>.
	 * Returns a new Complex number as the result.
	 * 
	 * @param c
	 * @return z = this / c
	 * @throws IllegalArgumentException if <code>c</code> is zero
	 */
	public Complex divide(Complex c) {
		if (c.real == 0 && c.imaginary == 0) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		double real = (this.real * c.real + this.imaginary * c.imaginary) / denominator;
		double imaginary = (this.imaginary * c.real - this.real * c.imaginary) / denominator;
		return new Complex(real, imaginary);
	}

	/**
	 * Returns the negative of <code>this</code> Complex number.
	 * 
	 * @return new Complex number that is <code>this</code> multiplied by -1
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Adds <code>this</code> Complex number to Complex <code>c</code> and return a
	 * new Complex number as the result.
	 * 
	 * @param c
	 * @return z = this + c
	 */
	public Complex add(Complex c) {
		double real = this.real + c.real;
		double imaginary = this.imaginary + c.imaginary;
		return new Complex(real, imaginary);
	}

	/**
	 * Subtracts the Complex number <code>c</code> from <code>this</code> Complex
	 * number and returns a new Complex number as the result.
	 * 
	 * @param c
	 * @return z = this - c
	 */
	public Complex sub(Complex c) {
		double real = this.real - c.real;
		double imaginary = this.imaginary - c.imaginary;
		return new Complex(real, imaginary);
	}

	/**
	 * Calculates Complex number <code>this</code> to the power of <code>n</code>.
	 * Returns a new Complex number as the result. Only for n>=0 .
	 * 
	 * @param n
	 * @return z = this^n
	 * @throws IllegalArgumentException if n is less than 0
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be 0 or greater. It was " + n);
		}
		double magnitude = Math.pow(module(), n);
		double angle = n * angle();
		return Complex.fromModuleAndAngle(magnitude, angle);
	}

	/**
	 * Calculates all the <code>n</code>th roots of <code>this</code> Complex and
	 * returns them in a list.
	 * 
	 * @param n
	 * @return list of <code>n</code>th roots of <code>this</code>
	 * @throws IllegalArgumentException if n is less than 1
	 */
	public List<Complex> root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("n must be greater than 0. It was " + n);
		}
		List<Complex> roots = new ArrayList<>();
		double magnitude = Math.pow(module(), 1.0 / n);
		double theta = angle();
		for (int i = 0; i < n; ++i) {
			double angle = theta / n + 2 * Math.PI * i / n;
			roots.add(Complex.fromModuleAndAngle(magnitude, angle));
		}
		return roots;
	}
	
	/**
	 * Scales <code>this</code> complex number as if it is a vector
	 * with the given real value <code>s</code>.
	 * The method is used to simplyfy multiplying with a real number.
	 * @param s - real number for scaling <code>this</code> Complex
	 * @return a new Complex that is equal to <code>this</code>*s
	 */
	public Complex scale(double s) {
		return new Complex(real*s, imaginary*s);
	}

	/**
	 * Returns a new Complex number with a magnitude of the given
	 * <code>module</code> and angle of given <code>angle</code> in radians.
	 * 
	 * @param module of the desired Complex
	 * @param angle  in radians of the desired Complex
	 * @return Complex module*(cos(angle) + i*sin(angle))
	 */
	public static Complex fromModuleAndAngle(double module, double angle) {
		double real = module * Math.cos(angle);
		double imaginary = module * Math.sin(angle);
		return new Complex(real, imaginary);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		
		return doublesEquals(this.real, other.real) && doublesEquals(this.imaginary, other.imaginary);
	}

	private boolean doublesEquals(double d1, double d2) {
		return Math.abs(d2 - d1) < DELTA;
	}
	
	@Override
	public String toString() {
		return "(" + real + (imaginary<0 ? "" : "+")  + imaginary + "i" + ")";
	}
	
	/**
	 * Parses the given String <code>s</code> to a ComplexNumber. The string is expected to have no whitespaces.
	 * @param s
	 * @return ComplexNumber
	 * @throws NumberFormatException if s is not parsable to a ComplexNumber
	 * @throws NullPointerException if <code>s</code> is <code>null</code>
	 */
	public static Complex parse(String s) {
		if(s==null) {
			throw new NullPointerException("Cannot parse null.");
		}
		if(s.length()==0) {
			throw new NumberFormatException("Cannot parse empty string.");
		}
		
		int index = Math.max(s.lastIndexOf('+'), s.lastIndexOf('-'));
		
		if(index==s.length()-1) {
			throw new NumberFormatException("Number cannot end with a + or -.");
		}
		
		if(index==-1 || index==0) {
			return parsePart(s);
		}
		
		String firstSubstring = s.substring(0, index);
		String secondSubstring = s.substring(index, s.length());
		Complex c1 = parsePart(firstSubstring);
		Complex c2 = parsePart(secondSubstring);
		
		return c1.add(c2);
	}
	
	/**
	 * Parses the String <code>s</code> to a ComplexNumber. Expects only a real part or only an imaginary part.
	 * @param s
	 * @return ComplexNumber
	 * @throws NumberFormatException if <code>s</code> is not parsable
	 */
	private static Complex parsePart(String s) {
		//if it is a real part, parse it
		if(s.indexOf('i')==-1) {
			return new Complex(Double.parseDouble(s), 0);
		}
		//if it is imaginary and only 'i', return i
		if(s.length()==1) {
			return new Complex(0, 1);
		}
		//if it is imaginary and 'i' is at the end as expected, remove 'i' and parse it
		if(s.indexOf('i')==s.length()-1) {
			String onlyNumber = s.substring(0, s.length()-1);
			//if s was "-i" onlyNumbers will be "-"
			//so a '1' is added for onlyNumbers to be parsable
			if(onlyNumber.length()==1 && !Character.isDigit(s.charAt(0))) {
				onlyNumber += '1';
			}
			return new Complex(0, Double.parseDouble(onlyNumber));
		}
		throw new NumberFormatException("Cannot parse " + s + " to ComplexNumber");
	}	

}
