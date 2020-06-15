package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * A JLabel that updates its text by registering listeners on the color
 * providers given through the constructor.
 * 
 * @author Vedran Kolka
 *
 */
public class JColorLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	/** Text about the foreground color to show */
	private String foreGroundText;
	/** Text about the foreground color to show */
	private String backgroundText;

	/**
	 * Constructor. Registers listeners on the given {@link IColorProvider}s to
	 * update its text on each change of selected colors.
	 * 
	 * @param foregroundColorProvider
	 * @param backgroundColorProvider
	 */
	public JColorLabel(IColorProvider foregroundColorProvider, IColorProvider backgroundColorProvider) {
		foregroundColorProvider.addColorChangeListener((s, oColor, nColor) -> {
			setForegroundText(nColor.getRed(), nColor.getGreen(), nColor.getBlue());
		});

		backgroundColorProvider.addColorChangeListener((s, oColor, nColor) -> {
			setBackgroundText(nColor.getRed(), nColor.getGreen(), nColor.getBlue());
		});

		Color currentFGColor = foregroundColorProvider.getCurrentColor();
		setForegroundText(currentFGColor.getRed(), currentFGColor.getGreen(), currentFGColor.getBlue());

		Color currentBGColor = backgroundColorProvider.getCurrentColor();
		setBackgroundText(currentBGColor.getRed(), currentBGColor.getGreen(), currentBGColor.getBlue());
	}

	/**
	 * Provides text for the first part of the label
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	private void setForegroundText(int red, int green, int blue) {
		foreGroundText = "Foreground color: (" + red + ", " + green + ", " + blue + "), ";
		setText();
	}

	/**
	 * Provides text for the first part of the label
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	private void setBackgroundText(int red, int green, int blue) {
		backgroundText = "background color: (" + red + ", " + green + ", " + blue + ").";
		setText();
	}

	/**
	 * Sets the text of the label by setting the first and second part of the text.
	 */
	private void setText() {
		setText(foreGroundText + backgroundText);
	}
}
