package hr.fer.zemris.java.hw17.jvdraw.model.drawing;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.GeometricalObject;

/**
 * A model of a drawing that holds references to all of its
 * {@link GeometricalObject}s in an order.
 * 
 * @author Vedran Kolka
 *
 */
public interface DrawingModel extends Iterable<GeometricalObject> {
	/**
	 * Returns the number of objects in the model.
	 * 
	 * @return number of objects in the model
	 */
	public int getSize();

	/**
	 * Returns the object at index <code>index</code>
	 * 
	 * @param index of the requested object
	 * @return object at index <code>index</code>
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds the given <code>object</code> to the model.
	 * 
	 * @param object to add
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes the <code>object</code> from the model.
	 * 
	 * @param object to remove
	 */
	public void remove(GeometricalObject object);

	/**
	 * Changes the order of objects in the model by moving the given
	 * <code>object</code> by <code>offset</code> spaces to the right (which mean if
	 * offset is less than 0, the object is moved to the left).
	 * 
	 * @param object to move
	 * @param offset number of positions to move
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns the index of the given <code>object</code> or <code>null</code> if
	 * the object is not in the model.
	 * 
	 * @param object to look for
	 * @return the index of the given <code>object</code> or <code>null</code> if
	 *         the object is not in the model
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Removes all objects from the model.
	 */
	public void clear();

	/**
	 * Sets the modification flag to false, indicating the model has been saved.
	 */
	public void clearModifiedFlag();

	/**
	 * Returns <code>true</code> if the model has been modified since the last save,
	 * <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if the model has been modified since the last save,
	 *         <code>false</code> otherwise
	 */
	public boolean isModified();

	/**
	 * Registers a {@link DrawingModelListener} to the model.
	 * @param l non <code>null</code> listener to register
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes a {@link DrawingModelListener} from the model.
	 * @param l listener to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

}
