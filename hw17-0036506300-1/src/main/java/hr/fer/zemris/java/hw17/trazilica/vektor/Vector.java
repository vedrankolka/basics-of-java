package hr.fer.zemris.java.hw17.trazilica.vektor;
/**
 * An n-dimensional vector.
 * @author Vedran Kolka
 *
 */
public class Vector {
	/** values of the vector */
	private double[] values;
	/** module of the vector */
	private Double module;

	public Vector(double[] values) {
		this.values = checkValues(values);
	}

	public double getModule() {
		if (module == null) {
			module = calculateModule();
		}

		return module;
	}

	public double[] getValues() {
		return values;
	}

	/**
	 * Multiplies this vector scalarno with the given vecctor and returns the result.
	 * @param other
	 * @return
	 */
	public double multiply(Vector other) {
		double result = 0.0;

		for (int i = 0; i < values.length; i++) {
			result += values[i] * other.values[i];
		}

		return result;
	}

	/**
	 * Calculates the cosine of the angle between this vector and the <code>other</code> vector.
	 * @param other
	 * @return
	 */
	public double calculateCosine(Vector other) {
		return this.multiply(other) / (this.getModule() * other.getModule());
	}

	/**
	 * Calculates the module of this vector.
	 * @return module of this vector
	 */
	private double calculateModule() {

		double sum = 0.0;

		for (int i = 0; i < values.length; ++i) {
			sum += values[i] * values[i];
		}

		return Math.sqrt(sum);
	}

	/**
	 * Checks if the values are valid
	 * @param values
	 * @return
	 * @throws NullPointerException if the values are <code>null</code>
	 * @throws IllegalArgumentException if the length of values is 0
	 */
	private static double[] checkValues(double[] values) {
		if (values.length == 0)
			throw new IllegalArgumentException();

		return values;
	}

}
