package hr.fer.zemris.java.custom.collections;

/**
 * Abstract representation of a collection of Objects.
 * @author Vedran Kolka
 *
 */
public class Collection {

	protected Collection() {
		
	}
	
	/**
	 * The method checks if the Collection is empty.
	 * @return <code>true</code> if the Collection contains no Object, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return false;
	}
	
	/**
	 * The method returns the number of currently stored Objects in the Collection.
	 * @return size of Collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given <code>value</code> into the Collection.
	 * @param value
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks if the Collection contains the given <code>value</code>, as determined by <code>equals</code> method.
	 * @param value
	 * @return <code>true</code> if the Collection contains the given <code>value</code>, <code>false</code> otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Returns <code>true</code> only if the Collection contains given <code>value</code> as determined by
	 *  <code>equals</code> method and removes one occurrence of it.
	 * @param value
	 * @return <code>true</code> if <code>value</code> is removed from the Collection, <code>false</code> otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of the Collection, fills it with Collection content and
	 * returns the array. This method never returns <code>null</code>.
	 * @return array of Objects from the Collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls <code>processor.process</code> method for each element in the Collection.
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Adds all the elements from the Collection <code>other</code> to this Collection.
	 * @param other
	 */
	public void addAll(Collection other) {
		//models an Object which is used to add an Object to whereToCopy collection
		class CopyProcessor extends Processor {
			private Collection whereToCopy; 
			public CopyProcessor(Collection whereToCopy) {
				this.whereToCopy = whereToCopy;
			}
			@Override
			public void process(Object value) {
				whereToCopy.add(value);
			}
		}
		/*Calls the process method of the created CopyProcessor to copy each 
		  element from Collection other to Collection this*/
		other.forEach(new CopyProcessor(this));
		
	}
	
	/**
	 * Removes all elements from the Collection.
	 */
	public void clear() {
		
	}
	
}
