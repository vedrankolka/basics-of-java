package hr.fer.zemris.java.hw17.jvdraw.model.objects;

/**
 * Interface that model objects interested in the changes of a certain
 * {@link GeometricalObject}.
 * 
 * @author Vedran Kolka
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Action to perform when the subject is changed.
	 * 
	 * @param o changed object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
