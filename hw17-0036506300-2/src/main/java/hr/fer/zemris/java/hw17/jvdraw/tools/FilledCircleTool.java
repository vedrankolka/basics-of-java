package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.FilledCircle;

/**
 * A tool for drawing a filled circle.
 * 
 * @author Vedran Kolka
 *
 */
public class FilledCircleTool extends AbstractTool {
	/** Color provider for the fill color of the circle to draw */
	private IColorProvider fillColorProvider;
	/**
	 * Current filled circle being drawn. Only present between two mouse clicks that
	 * define the circle.
	 */
	private FilledCircle currentFilledCircle;

	public FilledCircleTool(DrawingModel drawingModel, IColorProvider colorProvider, IColorProvider fillColorProvider,
			JDrawingCanvas jdc) {
		super(drawingModel, colorProvider, jdc);
		this.fillColorProvider = fillColorProvider;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (currentFilledCircle == null) {
			currentFilledCircle = new FilledCircle(0, e.getX(), e.getY(), colorProvider.getCurrentColor(),
					fillColorProvider.getCurrentColor());
			drawingModel.add(currentFilledCircle);
		} else {
			currentFilledCircle.setRadius(currentFilledCircle.calculateRadius(e.getX(), e.getY()));
			currentFilledCircle = null;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentFilledCircle != null) {
			currentFilledCircle.setRadius(currentFilledCircle.calculateRadius(e.getX(), e.getY()));
			jdc.repaint();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (currentFilledCircle != null) {
			currentFilledCircle.paint(g2d);
		}
	}

}
