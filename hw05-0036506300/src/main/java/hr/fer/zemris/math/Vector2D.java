package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class models a two-dimensional vector with x and y components.
 * @author Vedran Kolka
 *
 */
public class Vector2D {

	/**
	 * x component of the vector
	 */
	private double x;
	/**
	 * y component of the vector
	 */
	private double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Translates this vector by the <code>offset</code> vector.
	 * @param offset
	 * @throws NullPointerException if <code>offset</code> is <code>null</code>
	 */
	public void translate(Vector2D offset) {
		x += offset.x;
		y += offset.y;
	}
	
	/**
	 * A new vector translated by the given <code>offset</code> vector.
	 * @param offset
	 * @return new translated vector
	 * @throws NullPointerException if <code>offset</code> is <code>null</code>
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D newVector = copy();
		newVector.translate(offset);
		return newVector;
	}

	/**
	 * Rotates this vector for the given <code>angle</code>.
	 * @param angle in radians to rotate
	 */
	public void rotate(double angle) {
		double newAngle = getAngle() + angle;
		double length = getLength();
		x = length*Math.cos(newAngle);
		y = length*Math.sin(newAngle);
	}
	
	/**
	 * Returns a new vector equal to this vector rotated by <code>angle</code> radians.
	 * @param angle
	 * @return new vector equal to this, but rotated by <code>angle</code>
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector = copy(); 
		newVector.rotate(angle);
		return newVector;
	}
	
	/**
	 * Scales this vector <code>scaler</code> times.
	 * @param scaler - the factor by which the vector will be scaled
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Returns a new vector scaled by the factor of <code>scaler</code>.
	 * @param scaler - the factor by which the vector will be scaled
	 * @return new scaled vector
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector = copy();
		newVector.scale(scaler);
		return newVector;
	}
	
	/**
	 * Returns a new vector that is the copy of this vector.
	 * @return a copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	/**
	 * Getter for x
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for y
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Vector2D)) {
			return false;
		}
		Vector2D other = (Vector2D) obj;
		return Math.abs(x-other.x)<10E-6
				&& Math.abs(y-other.y)<10E-6;
	}
	
// private helper methods---------------------------------------------

	/**
	 * Calculates the angle of the vector.
	 * @return angle of the vector
	 */
	private double getAngle() {
		return Math.atan2(y, x);
	}
	
	/**
	 * Calculates the length of the vector.
	 * @return length of the vector
	 */
	private double getLength() {
		return Math.hypot(x, y);
	}
	
}
