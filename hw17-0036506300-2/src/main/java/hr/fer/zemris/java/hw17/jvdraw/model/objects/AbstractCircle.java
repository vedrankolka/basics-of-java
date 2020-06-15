package hr.fer.zemris.java.hw17.jvdraw.model.objects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * An object that is some kind of a circle.
 * 
 * @author Vedran Kolka
 *
 */
public abstract class AbstractCircle extends GeometricalObject {
	/** radius of the circle */
	private int radius;
	/** x component of the circle's center point */
	private int x0;
	/** y component of the circle's center point */
	private int y0;
	/** color of the outline of the circle */
	private Color outlineColor;

	/**
	 * Constructor.
	 * 
	 * @param radius
	 * @param x0
	 * @param y0
	 * @param outlineColor
	 */
	public AbstractCircle(int radius, int x0, int y0, Color outlineColor) {
		this.radius = radius;
		this.x0 = x0;
		this.y0 = y0;
		this.outlineColor = outlineColor;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
		fireGeometricalObjectChanged(this);
	}

	public int getX0() {
		return x0;
	}

	public void setX0(int x0) {
		this.x0 = x0;
		fireGeometricalObjectChanged(this);
	}

	public int getY0() {
		return y0;
	}

	public void setY0(int y0) {
		this.y0 = y0;
		fireGeometricalObjectChanged(this);
	}

	@Override
	public Color getColor() {
		return outlineColor;
	}

	@Override
	public void setColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	@Override
	public void paint(Graphics2D g2d) {
		Color savedColor = g2d.getColor();

		g2d.setColor(getColor());
		g2d.drawOval(x0 - radius, y0 - radius, 2 * radius, 2 * radius);

		g2d.setColor(savedColor);
	}

	/**
	 * Calculates the distance of the given point (<code>x</code>, <code>y</code>)
	 * from the center of the circle, giving the supposed radius of the circle.
	 * 
	 * @param x component of the point
	 * @param y component of the point
	 * @return distance from the point to the center of the circle
	 */
	public int calculateRadius(int x, int y) {
		return (int) Math.sqrt((x - x0) * (x - x0) + (y - y0) * (y - y0));
	}

	@Override
	public String toString() {
		return "(" + x0 + ", " + y0 + "), " + radius;
	}

//	@Override
//	public String format() {
//		return new StringBuilder().append("CIRCLE").append(' ').append(x0).append(' ').append(y0).append(' ')
//				.append(radius).append(' ').append(outlineColor.getRed()).append(' ').append(outlineColor.getGreen())
//				.append(' ').append(outlineColor.getBlue()).toString();
//	}

}
