package hr.fer.zemris.java.hw17.jvdraw.model.drawing;

/**
 * A listener interested in changes of a {@link DrawingModel} to which it
 * registers itself.
 * 
 * @author Vedran Kolka
 *
 */
public interface DrawingModelListener {

	/**
	 * Action to perform when objects are added to the subject.
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Action to perform when objects are removed from the subject.
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Action to perform when objects in the subject are changed.
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
