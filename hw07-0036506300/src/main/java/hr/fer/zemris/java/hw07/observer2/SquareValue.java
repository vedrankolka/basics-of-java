package hr.fer.zemris.java.hw07.observer2;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange iStorageChange) {
		int i = iStorageChange.getNewValue();
		System.out.printf("Provided new value: %d, square is %d%n", i, i*i);
	}

}
