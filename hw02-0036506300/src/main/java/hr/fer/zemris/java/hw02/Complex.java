package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * Models an unmodifiable complex number z = real + i*imaginary .
 * All modification methods return a new instance of a ComplexNumber.
 * @author Vedran Kolka
 *
 */
public class Complex {

	/**
	 * Real part of the ComplexNumber.
	 */
	private final double real;
	/**
	 * Imaginary part of the ComplexNumber.
	 */
	private final double imaginary;
	
	/**
	 * Creates a 
	 * @param real
	 * @param imaginary
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Returns a new ComplexNumber with a real part of <code>real</code> and imaginary part of 0.
	 * @param real
	 * @return ComplexNumber real + 0*i
	 */
	public static Complex fromReal(double real) {
		return new Complex(real, 0);
	}
	
	/**
	 * Returns a new ComplexNumber with a real part of 0 and imaginary part of <code>imaginary</code>.
	 * @param imaginary
	 * @return ComplexNumber 0 + imaginary*i
	 */
	public static Complex fromImaginary(double imaginary) {
		return new Complex(0, imaginary);
	}
	
	/**
	 * Returns a new ComplexNumber with a magnitude of the given <code>magnitude</code>
	 * and angle of given <code>angle</code>.
	 * @param magnitude
	 * @param angle
	 * @return ComplexNumber magnitude*(cos(angle) + i*sin(angle))
	 */
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude*Math.cos(angle);
		double imaginary = magnitude*Math.sin(angle);
		return new Complex(real, imaginary);
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
	
	
	/**
	 * Adds <code>this</code> ComplexNumber to ComplexNumber <code>c</code> and return a new ComplexNUmber as the result.
	 * @param c
	 * @return z = this + c
	 */
	public Complex add(Complex c) {
		double real = this.real + c.real;
		double imaginary = this.imaginary + c.imaginary;
		return new Complex(real, imaginary);
	}
	
	/**
	 * Subtracts the ComplexNumber <code>c</code> from <code>this</code> ComplexNUmber
	 * and returns a new ComplexNumber as the result.
	 * @param c
	 * @return z = this - c
	 */
	public Complex sub(Complex c) {
		double real = this.real - c.real;
		double imaginary = this.imaginary - c.imaginary;
		return new Complex(real, imaginary);
	}
	
	/**
	 * Multiplies ComplexNumbers <code>this</code> and <code>c</code>.
	 * Returns a new ComplexNumber as the result.
	 * @param c
	 * @return z = this * c
	 */
	public Complex mul(Complex c) {
		double real = this.real*c.real - this.imaginary*c.imaginary;
		double imaginary = this.real*c.imaginary + this.imaginary*c.real;
		return new Complex(real, imaginary);
	}
	
	/**
	 * Divides ComplexNumber <code>this</code> by ComplexNumber <code>c</code>.
	 * Returns a new ComplexNumber as the result.
	 * @param c
	 * @return z = this / c
	 * @throws IllegalArgumentException if <code>c</code> is zero
	 */
	public Complex div(Complex c) {
		if(c.getReal()==0 && c.getImaginary()==0) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
		double denominator = c.real*c.real + c.imaginary*c.imaginary;
		double real = (this.real*c.real + this.imaginary*c.imaginary)/denominator;
		double imaginary = (this.imaginary*c.real - this.real*c.imaginary)/denominator;
		return new Complex(real, imaginary);
	}
	
	/**
	 * Calculates ComplexNumber <code>this</code> to the power of <code>n</code>.
	 * Returns a new ComplexNUmber as the result. Only for n>=0 .
	 * @param n
	 * @return z = this^n
	 * @throws IllegalArgumentException if n is less than 0
	 */
	public Complex power(int n) {
		if(n<0) {
			throw new IllegalArgumentException("n must be 0 or greater. It was " + n);
		}
		double magnitude = Math.pow(getMagnitude(), n);
		double angle = n*this.getAngle();
		return Complex.fromMagnitudeAndAngle(magnitude, angle);
	}
	
	/**
	 * Calculates all the <code>n</code>th roots of <code>this</code> ComplexNumber and returns them in an array.
	 * @param n
	 * @return array of <code>n</code>th roots of <code>this</code>
	 * @throws IllegalArgumentException if n is less than 1
	 */
	public Complex[] root(int n) {
		if(n<1) {
			throw new IllegalArgumentException("n must be greater than 0. It was " + n);
		}
		Complex[] roots = new Complex[n];
		double magnitude = Math.pow(getMagnitude(), 1.0/n);
		double theta = getAngle();
		for(int i = 0 ; i<n ; ++i) {
			double angle = theta/n + 2*Math.PI*i/n;
			roots[i] = Complex.fromMagnitudeAndAngle(magnitude, angle);
		}
		return roots;
	}
	
	/**
	 * @return real part of this ComplexNumber
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * @return imaginary part of this CompolexNumber
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns the magnitude r from the conversion of rectangular coordinates (<code>real</code>, <code>imaginary</code>)
	 * to polar coordinates (r, theta).
	 * @return magnitude of this ComplexNumber
	 */
	public double getMagnitude() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Returns the angle theta from the conversion of rectangular coordinates (<code>real</code>, <code>imaginary</code>)
	 * to polar coordinates (r, theta).
	 * @return angle of this ComplexNumber [0, 2pi>
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		return angle>=0 ? angle : angle + 2*Math.PI;
	}
	
	@Override
	public String toString() {
		return real + (imaginary<0 ? "" : "+")  + imaginary + "i";
	}

	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Complex)) {
			return false;
		}
		Complex other = (Complex) obj;
		return Double.doubleToLongBits(imaginary) == Double.doubleToLongBits(other.imaginary)
				&& Double.doubleToLongBits(real) == Double.doubleToLongBits(other.real);
	}
	
	
	
}
