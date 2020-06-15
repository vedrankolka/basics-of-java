package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntegerStorage {

	/**
	 * Value of the integer.
	 */
	private int value;
	/**
	 * List of observers to be notified when a change of the value occurs.
	 * {@linkplain}
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructs an IntegerStorage with the <code>value</code> set to <code>initialValue</code>.
	 * @param initialValue
	 */
	public IntegerStorage(int initialValue) {
		this.observers = new ArrayList<>();
		this.value = initialValue;
	}

	/**
	 * Adds the given <code>observer</code> into the list of <code>observers</code>.
	 * @param observer
	 * @throws NullPointerException if <code>observer</code> is <code>null</code>
	 */
	public void addObserver(IntegerStorageObserver observer) {
		observers = new ArrayList<>(observers);
		observers.add(Objects.requireNonNull(observer));
	}

	/**
	 * Removes the observer <code>observer</code> from <code>observers</code>
	 * if it was registered, does nothing otherwise.
	 * @param observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers = new ArrayList<>(observers);
		observers.remove(observer);
	}

	/**
	 * Removes all registered observers from the list <code>observers</code>.
	 */
	public void clearObservers() {
		observers.clear();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			int oldValue = this.value;
			// Update current value
			this.value = value;
			IntegerStorageChange change = new IntegerStorageChange(oldValue, this);
			// Notify all registered observers
			if(observers != null) {
				observers.forEach( o -> o.valueChanged(change));
			}
		}
	}

}
