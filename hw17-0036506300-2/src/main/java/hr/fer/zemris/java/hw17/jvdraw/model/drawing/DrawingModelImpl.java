package hr.fer.zemris.java.hw17.jvdraw.model.drawing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.GeometricalObjectListener;

/**
 * Implementation of the {@link DrawingModel}.
 * 
 * @author Vedran Kolka
 *
 */
public class DrawingModelImpl implements DrawingModel {
	/** Objects in this model */
	private List<GeometricalObject> objects = new ArrayList<>();
	/** Listeners registered to this models changes */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	/** flag indicating if the model has been modified since the last save */
	private boolean modified;

	/**
	 * Listener to register on all objects in the model to notify the listeners
	 * registered to this model.
	 */
	private GeometricalObjectListener changeListener = go -> {
		int index = indexOf(go);
		fireObjectsChanged(index, index);
	};

	@Override
	public Iterator<GeometricalObject> iterator() {
		return objects.iterator();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(Objects.requireNonNull(object));
		object.addGeometricalObjectListener(changeListener);
		int index0 = objects.size() - 1;
		modified = true;
		fireObjectsAdded(index0, index0);
	}

	@Override
	public void remove(GeometricalObject object) {
		int index0 = objects.indexOf(object);
		objects.remove(index0);
		object.removeGeometricalObjectListener(changeListener);
		modified = true;
		fireObjectsRemoved(index0, index0);
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		objects.remove(index);
		objects.add(index + offset, object);

		int index0 = offset > 0 ? index : index + offset;
		int index1 = offset > 0 ? index + offset : index;
		modified = true;
		fireObjectsChanged(index0, index1);
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		int index1 = objects.size() > 0 ? objects.size() - 1 : 0;
		objects.clear();
		modified = true;
		fireObjectsRemoved(0, index1);
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
		fireObjectsChanged(0, 0);
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies registered listeners that object have been added to the model.
	 * 
	 * @param index0
	 * @param index1
	 */
	private void fireObjectsAdded(int index0, int index1) {
		listeners.forEach(l -> l.objectsAdded(this, index0, index1));
	}

	/**
	 * Notifies registered listeners that objects have been removed from the model
	 * 
	 * @param index0
	 * @param index1
	 */
	private void fireObjectsRemoved(int index0, int index1) {
		listeners.forEach(l -> l.objectsRemoved(this, index0, index1));
	}

	/**
	 * Notifies registered listeners that objects in the model have been changed.
	 * 
	 * @param index0
	 * @param index1
	 */
	private void fireObjectsChanged(int index0, int index1) {
		listeners.forEach(l -> l.objectsChanged(this, index0, index1));
	}

}
