package hr.fer.zemris.java.gui.layouts;
/**
 * An exception thrown by the CalcLayout manager.
 * @author Vedran Kolka
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CalcLayoutException() {
	}
	
	public CalcLayoutException(String message) {
		super(message);
	}
	
}
