package hr.fer.zemris.java.custom.collections;

/**
 * Abstract representation of a collection of Objects.
 * @author Vedran Kolka
 *
 */
public interface Collection<T> {
	
	/**
	 * The method checks if the Collection is empty.
	 * @return <code>true</code> if the Collection contains no Object, <code>false</code> otherwise
	 */
	default boolean isEmpty() {
		return size()==0;
	}
	
	/**
	 * The method returns the number of currently stored Objects in the Collection.
	 * @return size of Collection
	 */
	int size();
	
	/**
	 * Adds the given <code>value</code> into the Collection.
	 * @param value
	 */
	void add(T value);
	
	/**
	 * Checks if the Collection contains the given <code>value</code>, as determined by <code>equals</code> method.
	 * @param value
	 * @return <code>true</code> if the Collection contains the given <code>value</code>, <code>false</code> otherwise
	 */
	boolean contains(Object value);
	
	/**
	 * Returns <code>true</code> only if the Collection contains given <code>value</code> as determined by
	 *  <code>equals</code> method and removes one occurrence of it.
	 * @param value
	 * @return <code>true</code> if <code>value</code> is removed from the Collection, <code>false</code> otherwise
	 */
	boolean remove(Object value);
	
	/**
	 * Allocates new array with size equals to the size of the Collection, fills it with Collection content and
	 * returns the array. This method never returns <code>null</code>.
	 * @return array of Objects from the Collection
	 */
	Object[] toArray();
	
	/**
	 * Calls <code>processor.process</code> method for each element in the Collection.
	 * @param processor
	 */
	default void forEach(Processor<T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Adds all the elements from the Collection <code>other</code> to this Collection.
	 * @param other
	 */
	//TODO KONZE kak namjestit da smijem primit <? extends T>
	default void addAll(Collection<T> other) {
		
		class CopyProcessor implements Processor<T> {
			@Override
			public void process(T value) {
				add(value);
			}
		}
		/*Calls the process method of the created CopyProcessor to copy each 
		  element from Collection other to Collection this*/
		other.forEach(new CopyProcessor());
		
	}
	
	/**
	 * Adds all acceptable elements from <code>col</code> to <code>this</code> collection,
	 * as determined by the <code>tester</code>.
	 * @param col
	 * @param tester
	 */
	//TODO KONZE kak namjestit da smijem primit <? extends T>
	default void addAllSatisfying(Collection<T> col, Tester tester) {
		ElementsGetter<T> getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			T t = getter.getNextElement();
			if(tester.test(t)) {
				this.add(t);
			}
		}
	}
	
	/**
	 * Removes all elements from the Collection.
	 */
	public void clear();
	
	/**
	 * Creates an ElementGetter for the collection it is called upon.
	 * @return ElementGetter
	 */
	ElementsGetter<T> createElementsGetter();
	
}
