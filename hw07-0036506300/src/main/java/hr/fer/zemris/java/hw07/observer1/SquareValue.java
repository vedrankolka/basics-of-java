package hr.fer.zemris.java.hw07.observer1;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int i = istorage.getValue();
		System.out.printf("Provided new value: %d, square is %d%n", i, i*i);
	}

}
