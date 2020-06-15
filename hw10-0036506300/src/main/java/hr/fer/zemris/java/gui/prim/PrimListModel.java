package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * A {@link ListModel} of prime numbers that generates them one by one by
 * calling the next() method.<br>
 * The list starts with the number 1.
 * 
 * @author Vedran Kolka
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/** list of prime numbers */
	private List<Integer> list;
	/** list of registered listeners */
	private List<ListDataListener> listeners;

	/**
	 * Constructor.
	 */
	public PrimListModel() {
		list = new ArrayList<>();
		// add the first 'prime' number to the list
		list.add(1);
		listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return list.get(index);
	}

	/**
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	/**
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}

	/**
	 * Adds the next prime number to the list.
	 * 
	 * @throws NoSuchElementException if there are no more prime number that can by
	 *                                shown by an integer
	 */
	public void next() {
		// start calculating from the last prime number + 1
		int lastPrime = list.get(list.size() - 1);
		int prime = lastPrime + 1;
		// find the next prime number
		while (!isPrime(prime))
			prime++;
		// if overflow happened, there are no more prime numbers that can be shown by an
		// integer
		if (prime < 0) {
			throw new NoSuchElementException("No more prime numbers avaliable.");
		}
		// add it to the list
		list.add(prime);
		notifyListeners(getSize() - 2, getSize() - 1);
	}

	/**
	 * Notifies all listeners registered to this model that a change has occurred.
	 * @param intervalStart start of the interval in the list where the change occurred
	 * @param intervalEnd end of the interval in the list where the change occurred
	 */
	private void notifyListeners(int intervalStart, int intervalEnd) {
		ListDataEvent e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, intervalStart, intervalEnd);
		listeners.forEach(l -> l.contentsChanged(e));
	}

	/**
	 * Check if given <code>n</code> is a prime number or not.
	 * 
	 * @param n
	 * @return <code>true</code> if it is, <code>false</code> otherwise
	 */
	private static boolean isPrime(int n) {
		if (n == 2)
			return true;
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

}
