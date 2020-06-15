package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Line;

/**
 * Line for drawing a tool.
 * 
 * @author Vedran Kolka
 *
 */
public class LineTool extends AbstractTool {
	/**
	 * Current line being drawn. Only present between two mouse clicks defining the
	 * line.
	 */
	private Line currentLine;

	public LineTool(DrawingModel drawingModel, IColorProvider colorProvider, JDrawingCanvas jdc) {
		super(drawingModel, colorProvider, jdc);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (currentLine == null) {
			currentLine = new Line(e.getX(), e.getY(), e.getX(), e.getY(), colorProvider.getCurrentColor());
			drawingModel.add(currentLine);
		} else {
			currentLine.setX1(e.getX());
			currentLine.setY1(e.getY());
			currentLine = null;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentLine != null) {
			currentLine.setX1(e.getX());
			currentLine.setY1(e.getY());
			jdc.repaint();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (currentLine != null) {
			currentLine.paint(g2d);
		}
	}

}
