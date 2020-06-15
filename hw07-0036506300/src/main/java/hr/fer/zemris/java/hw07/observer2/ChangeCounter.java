package hr.fer.zemris.java.hw07.observer2;

public class ChangeCounter implements IntegerStorageObserver {
	
	/**
	 * Counter of changes since this object has been registered.
	 */
	private int changes;

	@Override
	public void valueChanged(IntegerStorageChange iStorageChange) {
		System.out.println("Number of value changes since tracking: " + ++changes);
	}

}
