package hr.fer.zemris.java.hw07.observer2;

/**
 * Instances of DoubleValue class write to the standard output double value
 * (i.e. “value * 2”) of the current value which is stored in subject, but only
 * first n times since its registration with the subject (n is given in
 * constructor); after writing the double value for the n-th time, the observer
 * automatically de-registers itself from the subject.
 * 
 * @author Vedran Kolka
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * The maximum number of changes that this observer is interested in.
	 */
	private int n;

	public DoubleValue(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be positive. It was " + n);
		}
		this.n = n;
	}

	@Override
	public void valueChanged(IntegerStorageChange iStorageChange) {
		if (n == 0) {
			iStorageChange.getIstorage().removeObserver(this);
		} else {
			n--;
			System.out.println("Double value: " + 2 * iStorageChange.getNewValue());
		}
	}

}
