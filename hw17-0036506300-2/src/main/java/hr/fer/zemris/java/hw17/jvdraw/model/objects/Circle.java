package hr.fer.zemris.java.hw17.jvdraw.model.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.editor.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectFormatter;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectVisitor;

/**
 * A class that models a circle.
 * 
 * @author Vedran Kolka
 *
 */
public class Circle extends AbstractCircle {

	/**
	 * Constructor.
	 * 
	 * @param radius
	 * @param x0
	 * @param y0
	 * @param outlineColor
	 */
	public Circle(int radius, int x0, int y0, Color outlineColor) {
		super(radius, x0, y0, outlineColor);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	@Override
	public String toString() {
		return "Circle " + super.toString();
	}

	/**
	 * Parses the given <code>line</code> by separating it by spaces and reads the
	 * properties defining a circle.
	 * <p>
	 * The expected format is defined by the {@link GeometricalObjectFormatter}
	 * 
	 * @param line
	 * @return {@link Circle}
	 */
	public static Circle parse(String line) {

		String[] parts = line.split("\\s+");

		int x0 = Integer.parseInt(parts[1]);
		int y0 = Integer.parseInt(parts[2]);
		int radius = Integer.parseInt(parts[3]);
		int r = Integer.parseInt(parts[4]);
		int g = Integer.parseInt(parts[5]);
		int b = Integer.parseInt(parts[6]);

		return new Circle(radius, x0, y0, new Color(r, g, b));
	}

}
