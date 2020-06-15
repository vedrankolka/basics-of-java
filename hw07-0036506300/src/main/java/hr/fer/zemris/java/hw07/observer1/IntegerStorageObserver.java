package hr.fer.zemris.java.hw07.observer1;
/**
 * Models an observer which can be registered to an IntegerStorage
 * so that it can perform a desired action when ItnegerStorage notifies
 * the value has changed (calls valueChanged upon the IntegerStorageObserver).
 * @author Vedran Kolka
 *
 */
public interface IntegerStorageObserver {

	/**
	 * The method that the Subject (integerStorage) calls when it has been changed.
	 * @param istorage that has changed
	 */
	public void valueChanged(IntegerStorage istorage);
	
}
