package hr.fer.zemris.java.hw17.jvdraw.model.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectVisitor;

/**
 * A class that models a geometrical object with a color.
 * 
 * @author Vedran Kolka
 *
 */
public abstract class GeometricalObject {
	/** Listeners registered to this object */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * The method called when accepting a visitor
	 * 
	 * @param v visitor to accept
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates a {@link GeometricalObjectEditor} for this object
	 * 
	 * @return created {@link GeometricalObjectEditor}
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Registers a listener to this object
	 * 
	 * @param l non <code>null</code> listener to register
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	/**
	 * Removes the listener from the registered listeners.
	 * 
	 * @param l listener to remove
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all registered listeners that this object has changed.
	 * 
	 * @param source
	 */
	protected void fireGeometricalObjectChanged(GeometricalObject source) {
		listeners.forEach(l -> l.geometricalObjectChanged(source));
	}

	/**
	 * Paints this object using the given {@link Graphics2D} <code>g2d</code>
	 * @param g2d
	 */
	public abstract void paint(Graphics2D g2d);

//	public abstract String format();
	
	public abstract Color getColor();

	public abstract void setColor(Color color);
}
