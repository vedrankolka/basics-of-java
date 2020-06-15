package hr.fer.zemris.java.hw17.jvdraw.model.objects;

import java.awt.Color;
import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectFormatter;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectVisitor;

/**
 * A class that models a line.
 * 
 * @author Vedran Kolka
 *
 */
public class Line extends GeometricalObject {
	/** x component of the start point of the line */
	private int x0;
	/** y component of the start point of the line */
	private int y0;
	/** x component of the end point of the line */
	private int x1;
	/** y component of the end point of the line */
	private int y1;
	/** Color of the line */
	private Color color;

	/**
	 * Constructor.
	 * 
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param color
	 */
	public Line(int x0, int y0, int x1, int y1, Color color) {
		super();
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
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

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
		fireGeometricalObjectChanged(this);
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
		fireGeometricalObjectChanged(this);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		fireGeometricalObjectChanged(this);
	}

	@Override
	public void paint(Graphics2D g2d) {
		Color savedColor = g2d.getColor();

		g2d.setColor(color);
		g2d.drawLine(x0, y0, x1, y1);

		g2d.setColor(savedColor);
	}

	@Override
	public String toString() {
		return "Line " + "(" + x0 + ", " + y0 + ")-(" + x1 + ", " + y1 + ")";
	}

//	@Override
//	public String format() {
//		return new StringBuilder().append("LINE")
//				.append(' ').append(x0)
//				.append(' ').append(y0)
//				.append(' ').append(x1)
//				.append(' ').append(y1)
//				.append(' ').append(color.getRed())
//				.append(' ').append(color.getGreen())
//				.append(' ').append(color.getBlue())
//				.toString();
//	}

	/**
	 * Parses the given <code>line</code> by separating it by spaces and reads the
	 * properties defining a Line.
	 * <p>
	 * The expected format is defined by the {@link GeometricalObjectFormatter}
	 * 
	 * @param line of text to parse
	 * @return {@link Line}
	 */
	public static Line parse(String line) {
		String[] parts = line.split("\\s+");

		int x0 = Integer.parseInt(parts[1]);
		int y0 = Integer.parseInt(parts[2]);
		int x1 = Integer.parseInt(parts[3]);
		int y1 = Integer.parseInt(parts[4]);
		int r = Integer.parseInt(parts[5]);
		int g = Integer.parseInt(parts[6]);
		int b = Integer.parseInt(parts[7]);

		return new Line(x0, y0, x1, y1, new Color(r, g, b));
	}

}
