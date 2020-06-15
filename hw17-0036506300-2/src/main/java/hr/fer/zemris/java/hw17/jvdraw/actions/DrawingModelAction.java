package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModelListener;

/**
 * Action that operates on a modified {@link DrawingModel} adjusting its enabled
 * state appropriately.
 * 
 * @author Vedran Kolka
 *
 */
public class DrawingModelAction extends AbstractAction implements DrawingModelListener {

	private static final long serialVersionUID = 1L;
	/** Action to run */
	private Runnable action;

	/**
	 * Constructor that takes two arguments.
	 * 
	 * @param drawingModel the model on which the action registers itself.
	 * @param action       to perform
	 */
	public DrawingModelAction(DrawingModel drawingModel, Runnable action) {
		drawingModel.addDrawingModelListener(this);
		this.action = Objects.requireNonNull(action);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action.run();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		setEnabled(source.isModified());
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		setEnabled(source.isModified());
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		setEnabled(source.isModified());
	}

}
