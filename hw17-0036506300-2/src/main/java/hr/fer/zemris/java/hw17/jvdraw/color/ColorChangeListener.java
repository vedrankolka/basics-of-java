package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

/**
 * A listener interested in the change of color of an {@link IColorProvider} to
 * which it registers itself.
 * 
 * @author Vedran Kolka
 *
 */
public interface ColorChangeListener {
	/**
	 * The method to be called by the {@link IColorProvider} when the color is
	 * changed.
	 * 
	 * @param source   source of the change
	 * @param oldColor color that was previously selected
	 * @param newColor color that is now selected
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
