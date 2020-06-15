package hr.fer.zemris.java.hw07.observer2;
/**
 * An object that holds all relevant data of the change of an IntegerStorage
 * @author Vedran Kolka
 *
 */
public class IntegerStorageChange {

	/**
	 * The value stored before the change.
	 */
	private int oldValue;
	/**
	 * The value stored after the change.
	 */
	private int newValue;
	/**
	 * The changed IntegerStorage.
	 */
	private IntegerStorage istorage;
	
	public IntegerStorageChange(int oldValue, IntegerStorage istorage) {
		this.oldValue = oldValue;
		this.newValue = istorage.getValue();
		this.istorage = istorage;
	}
	
	/**
	 * Getter for istorage.
	 * @return istorage
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}
	
	/**
	 * Getter for newValue.
	 * @return newValue
	 */
	public int getNewValue() {
		return newValue;
	}
	/**
	 * Getter for <code>oldValue</code>.
	 * @return oldValue
	 */
	public int getOldValue() {
		return oldValue;
	}
	
}
