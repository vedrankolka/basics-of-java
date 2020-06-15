package hr.fer.zemris.java.hw07.observer2;

public class ObserverExample {

	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(20);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(5));
		istorage.addObserver(new SquareValue());
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
		istorage.setValue(2);
		
	}
	
}
