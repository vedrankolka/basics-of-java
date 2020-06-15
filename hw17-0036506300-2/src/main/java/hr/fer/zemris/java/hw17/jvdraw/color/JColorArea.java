package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * A component that shows itself as a rectangle painted in its currently
 * selected color.
 * 
 * @author Vedran Kolka
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	private static final long serialVersionUID = 1L;
	/** currently selected color */
	private Color selectedColor;
	/** listeners registered to this component */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chooseColor();
			}
		});
		
		this.addColorChangeListener((s, oc, nc) -> repaint());
	}

	/**
	 * Shows a JColorChooser dialog and sets the selectedColor to the selected
	 * color.
	 */
	protected void chooseColor() {
		Color newColor = JColorChooser.showDialog(this, "Choose a color", selectedColor);
		if (newColor != null) {
			setSelectedColor(newColor);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Color savedColor = g.getColor();
		g.setColor(selectedColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(savedColor);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	private void setSelectedColor(Color newColor) {
		Color oldColor = selectedColor;
		this.selectedColor = newColor;
		fire(oldColor, newColor);
	}

	/**
	 * Notifies all registered listeners about the change of color.
	 * 
	 * @param oldColor
	 * @param newColor
	 */
	private void fire(Color oldColor, Color newColor) {
		listeners.forEach(l -> l.newColorSelected(this, oldColor, newColor));
	}

}
