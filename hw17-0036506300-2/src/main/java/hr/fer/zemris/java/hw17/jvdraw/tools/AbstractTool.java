package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModel;

/**
 * Abstract tool implementing the basic features of tools.
 * 
 * @author Vedran Kolka
 *
 */
public abstract class AbstractTool implements Tool {
	/** The drawing model on which this tool works */
	protected DrawingModel drawingModel;
	/** colorProvider for the drawing tool */
	protected IColorProvider colorProvider;
	/** Canvas on which the tool paints */
	protected JDrawingCanvas jdc;

	/**
	 * Constructor.
	 * 
	 * @param drawingModel on which to draw
	 * @param colorProvider to provide the color
	 * @param jdc canvas on which to paint
	 */
	public AbstractTool(DrawingModel drawingModel, IColorProvider colorProvider, JDrawingCanvas jdc) {
		this.drawingModel = drawingModel;
		this.colorProvider = colorProvider;
		this.jdc = jdc;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

}
