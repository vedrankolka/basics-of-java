package hr.fer.zemris.math;

/**
 * An unmodifiable three-dimensional vector with x, y and z components.
 * 
 * @author Vedran Kolka
 *
 */
public class Vector3 {
	/**
	 * x component of the vector.
	 */
	private double x;
	/**
	 * 
	 */
	private double y;
	/**
	 * z component of the vector.
	 */
	private double z;

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculates and returns the norm (length) of the vector.
	 * 
	 * @return norm of this vector.
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns a new normalized vector of this vector.
	 * 
	 * @return a new normalized vector of this vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Adds <code>this</code> vector to the given <code>other</code> vector and
	 * returns the result as a new vector.
	 * 
	 * @param other vector to add to this vector
	 * @return result of adding <code>this</code> vector to <code>other</code>
	 *         vector
	 */
	public Vector3 add(Vector3 other) {
		double x = this.x + other.x;
		double y = this.y + other.y;
		double z = this.z + other.z;
		return new Vector3(x, y, z);
	}

	/**
	 * Subtracts <code>other</code> vector from <code>this</code> vector and returns
	 * the result as a new vector.
	 * 
	 * @param other vector to substract from this vector
	 * @return result of substracting <code>other</code> vector from
	 *         <code>this</code> vector
	 */
	public Vector3 sub(Vector3 other) {
		double x = this.x - other.x;
		double y = this.y - other.y;
		double z = this.z - other.z;
		return new Vector3(x, y, z);
	}

	/**
	 * Calculates and returns the dot product of <code>this</code> vector and the
	 * given <code>other</code> vector.
	 * 
	 * @param other vector to multiply with this
	 * @return result of the dot product
	 */
	public double dot(Vector3 other) {
		double x = this.x * other.x;
		double y = this.y * other.y;
		double z = this.z * other.z;
		return x + y + z;
	}

	/**
	 * Calculates and returns the cross product of <code>this</code> vector and the
	 * given <code>other</code> vector.
	 * 
	 * @param other vector to multiply with this
	 * @return a new vector that is the result of the cross product
	 */
	public Vector3 cross(Vector3 other) {
		double x = this.y * other.z - this.z * other.y;
		double y = this.z * other.x - this.x * other.z;
		double z = this.x * other.y - this.y * other.x;
		return new Vector3(x, y, z);
	}

	/**
	 * Scales <code>this</code> vector for the given <code>s</code> and returns the
	 * result as a new vector.
	 * 
	 * @param s
	 * @return new vector that is equal to <code>this</code> scaled by
	 *         <code>s</code>
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Calculates and returns the cosine of the angle between <code>this</code> and
	 * <code>other</code> vector.
	 * 
	 * @param other vector
	 * @return cosine between <code>this</code> and <code>other</code>
	 */
	public double cosAngle(Vector3 other) {
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Getter for the x component of <code>this</code> vector.
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for the y component of <code>this</code> vector.
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter for the z component of <code>this</code> vector.
	 * 
	 * @return z
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns the components of <code>this</code> vector
	 * in an array of doubles.
	 * @return double array of the components of <code>this</code> vector
	 */
	public double[] toArray() {
		return new double[]{x, y, z};
	}
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

}
