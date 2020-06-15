package hr.fer.zemris.java.hw17.jvdraw.canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * A component on which {@link GeometricalObject}s are drawn using {@link Tool}s
 * on a {@link DrawingModel}.
 * 
 * @author Vedran Kolka
 *
 */
public class JDrawingCanvas extends JComponent {

	private static final long serialVersionUID = 1L;
	/** SUpplier of the tool to use. */
	private Supplier<Tool> toolSupplier;
	/** Drawing model on which to draw */
	private DrawingModel drawingModel;

	/**
	 * Constructor.
	 * 
	 * @param drawingModel to edit
	 * @param toolSupplier to supply the tool used for editing
	 */
	public JDrawingCanvas(DrawingModel drawingModel, Supplier<Tool> toolSupplier) {

		drawingModel.addDrawingModelListener(new RepaintDrawingModelListener());
		this.drawingModel = drawingModel;
		this.toolSupplier = toolSupplier;

		MouseAdapter toolMouseAdapter = new ToolMouseAdapter();
		addMouseListener(toolMouseAdapter);
		addMouseMotionListener(toolMouseAdapter);
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		Color savedColor = g2d.getColor();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(savedColor);

		GeometricalObjectPainter gop = new GeometricalObjectPainter(g2d);
		for (GeometricalObject go : drawingModel) {
			go.accept(gop);
		}

		toolSupplier.get().paint(g2d);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

	/**
	 * A listener that calls the repaint method on each change of the drawing model
	 * 
	 * @author Vedran Kolka
	 *
	 */
	private class RepaintDrawingModelListener implements DrawingModelListener {

		@Override
		public void objectsAdded(DrawingModel source, int index0, int index1) {
			repaint();
		}

		@Override
		public void objectsRemoved(DrawingModel source, int index0, int index1) {
			repaint();
		}

		@Override
		public void objectsChanged(DrawingModel source, int index0, int index1) {
			repaint();
		}

	}

	/**
	 * A mouse adapter that forwards its events to the tool supplied by the tool
	 * supplier.
	 * 
	 * @author Vedran Kolka
	 *
	 */
	private class ToolMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			toolSupplier.get().mouseClicked(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			toolSupplier.get().mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			toolSupplier.get().mouseReleased(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			toolSupplier.get().mouseDragged(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			toolSupplier.get().mouseMoved(e);
		}

	}
}
