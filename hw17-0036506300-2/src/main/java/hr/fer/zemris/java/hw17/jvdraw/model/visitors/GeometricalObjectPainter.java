package hr.fer.zemris.java.hw17.jvdraw.model.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Line;

/**
 * Visitor that paint the components that it visits
 * 
 * @author Vedran Kolka
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	/** Graphics2D object used for painting the components */
	private Graphics2D g2d;

	/**
	 * Constructor.
	 * @param g2d
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		line.paint(g2d);
	}

	@Override
	public void visit(Circle c) {
		c.paint(g2d);
	}

	@Override
	public void visit(FilledCircle fc) {
		fc.paint(g2d);
	}

}
