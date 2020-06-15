package hr.fer.zemris.java.hw17.jvdraw.model.list;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.GeometricalObject;

/**
 * Model of a list of {@link GeometricalObject}s for a {@link JList} that also
 * offers methods of a subject in the listener design pattern.
 * 
 * @author Vedran Kolka
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> {

	private static final long serialVersionUID = 1L;
	/** The model to which this list is an adapter */
	private DrawingModel drawingModel;

	/**
	 * Constructor.
	 * 
	 * @param drawingModel to adapt to
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(new ListModelDrawingListener());
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	/**
	 * A listener that encloses the changes of the drawing model into changes of the list model about which it
	 * notifies its <b>list</b>eners.
	 * @author Vedran Kolka
	 *
	 */
	private class ListModelDrawingListener implements DrawingModelListener {

		@Override
		public void objectsAdded(DrawingModel source, int index0, int index1) {
			fireIntervalAdded(source, index0, index1);
		}

		@Override
		public void objectsRemoved(DrawingModel source, int index0, int index1) {
			fireIntervalRemoved(source, index0, index1);
		}

		@Override
		public void objectsChanged(DrawingModel source, int index0, int index1) {
			fireContentsChanged(source, index0, index1);
		}

	}

}
