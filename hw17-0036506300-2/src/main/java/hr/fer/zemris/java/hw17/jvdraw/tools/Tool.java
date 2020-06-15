package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Drawing tool that responds to mouse events.
 * 
 * @author Vedran Kolka
 *
 */
public interface Tool {
	/** Action to perform when the mouse is pressed */
	public void mousePressed(MouseEvent e);

	/** Action to perform when the mouse is released */
	public void mouseReleased(MouseEvent e);

	/** Action to perform when the mouse is clicked */
	public void mouseClicked(MouseEvent e);

	/** Action to perform when the mouse is moved */
	public void mouseMoved(MouseEvent e);

	/** Action to perform when the mouse is dragged */
	public void mouseDragged(MouseEvent e);

	/** Paint whatever the tool paints */
	public void paint(Graphics2D g2d);
}
