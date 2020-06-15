package hr.fer.zemris.java.hw17.jvdraw.model.visitors;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Line;

/**
 * A visitor of {@link GeometricalObject}s
 * 
 * @author Vedran Kolka
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Action to perform when visiting a {@link Line}
	 * 
	 * @param line to visit
	 */
	public abstract void visit(Line line);

	/**
	 * Action to perform when visiting a {@link Circle}
	 * 
	 * @param circle to visit
	 */
	public abstract void visit(Circle circle);

	/**
	 * Action to perform when visiting a {@link FilledCircle}
	 * 
	 * @param filledCircle to visit
	 */
	public abstract void visit(FilledCircle filledCircle);
}
