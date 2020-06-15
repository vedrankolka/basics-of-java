package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

/**
 * An interface that defines an object that can change its color and notify
 * interested listeners about the change.
 * 
 * @author Vedran Kolka
 *
 */
public interface IColorProvider {

	/**
	 * Returns the currently selected color.
	 * 
	 * @return currently selected color
	 */
	public Color getCurrentColor();

	/**
	 * Adds a listener to this component.
	 * 
	 * @param l {@link ColorChangeListener} to add
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the listener from this component.
	 * 
	 * @param l {@link ColorChangeListener} to remove
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}
