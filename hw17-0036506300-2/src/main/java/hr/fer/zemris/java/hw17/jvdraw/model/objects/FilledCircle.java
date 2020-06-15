package hr.fer.zemris.java.hw17.jvdraw.model.objects;

import java.awt.Color;
import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectFormatter;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectVisitor;

/**
 * A circle that is filled.
 * 
 * @author Vedran Kolka
 *
 */
public class FilledCircle extends AbstractCircle {
	/** Color of the circles inside */
	private Color fillColor;

	/**
	 * Constructor.
	 * @param radius
	 * @param x0
	 * @param y0
	 * @param outlineColor
	 * @param fillColor
	 */
	public FilledCircle(int radius, int x0, int y0, Color outlineColor, Color fillColor) {
		super(radius, x0, y0, outlineColor);
		this.fillColor = fillColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		fireGeometricalObjectChanged(this);
	}

	@Override
	public void paint(Graphics2D g2d) {
		Color savedColor = g2d.getColor();
		int r = getRadius();

		g2d.setColor(getFillColor());
		g2d.fillOval(getX0() - r, getY0() - r, 2 * r, 2 * r);

		super.paint(g2d);

		g2d.setColor(savedColor);
	}

	@Override
	public String toString() {
		return "Filled circle " + super.toString() + ", " + encode(fillColor.getRGB());
	}

//	@Override
//	public String format() {
//		return "F" + super.format() + " " +
//				fillColor.getRed() + " " +
//				fillColor.getGreen() + " " + 
//				fillColor.getBlue();
//	}

	/**
	 * Parses the given <code>line</code> by separating it by spaces and reads the
	 * properties defining a filled circle.
	 * <p>
	 * The expected format is defined by the {@link GeometricalObjectFormatter}
	 * 
	 * @param line
	 * @return {@link FilledCircle}
	 */
	public static FilledCircle parse(String line) {
		String[] parts = line.split("\\s+");

		int x0 = Integer.parseInt(parts[1]);
		int y0 = Integer.parseInt(parts[2]);
		int r = Integer.parseInt(parts[3]);
		int r1 = Integer.parseInt(parts[4]);
		int g1 = Integer.parseInt(parts[5]);
		int b1 = Integer.parseInt(parts[6]);
		int r2 = Integer.parseInt(parts[7]);
		int g2 = Integer.parseInt(parts[8]);
		int b2 = Integer.parseInt(parts[9]);

		return new FilledCircle(r, x0, y0, new Color(r1, g1, b1), new Color(r2, g2, b2));
	}

	public String encode(int rgb) {
		String coded = String.format("%x", rgb & 0xFFFFFF).toUpperCase();
		coded = "#" + "0".repeat(6 - coded.length()) + coded;
		return coded;
	}

}
