package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Circle;

/**
 * A tool for drawing a circle.
 * 
 * @author Vedran Kolka
 *
 */
public class CircleTool extends AbstractTool {
	/**
	 * The circle that is currently being drawn. Only present between the first and
	 * second mouse clicks on the canvas.
	 */
	private Circle currentCircle;

	public CircleTool(DrawingModel drawingModel, IColorProvider colorProvider, JDrawingCanvas jdc) {
		super(drawingModel, colorProvider, jdc);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (currentCircle == null) {
			currentCircle = new Circle(0, e.getX(), e.getY(), colorProvider.getCurrentColor());
			drawingModel.add(currentCircle);
		} else {
			currentCircle.setRadius(currentCircle.calculateRadius(e.getX(), e.getY()));
			currentCircle = null;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentCircle != null) {
			currentCircle.setRadius(currentCircle.calculateRadius(e.getX(), e.getY()));
			jdc.repaint();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (currentCircle != null) {
			currentCircle.paint(g2d);
		}
	}

}
