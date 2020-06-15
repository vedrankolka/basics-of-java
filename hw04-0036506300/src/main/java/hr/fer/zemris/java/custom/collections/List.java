package hr.fer.zemris.java.custom.collections;

/**
 * An interface which models a list in which elements are stored in order of adding them.
 * @author Vedran Kolka
 *
 * @param <T>
 */
public interface List<T> extends Collection<T> {
	
	/**
	 * Returns the Object at the given <code>index</code>.
	 * @param index
	 * @return Object at index <code>index</code>
	 */
	T get(int index);
	/**
	 * Inserts (does <b>not overwrite</b>) the given <code>value</code>
	 * at the given <code>position</code>.
	 * @param value
	 * @param position
	 */
	void insert(T value, int position);
	/**
	 * Searches the Collection and return the index of the first occurrence of the given <code>value</code>
	 * or -1 if <code>value</code> is not found.
	 * @param value
	 * @return index of the found Object, -1 if it is not found
	 */
	int indexOf(Object value);
	/**
	 * Removes the element on index <code>index</code>.
	 * @param index
	 */
	void remove(int index);

}
