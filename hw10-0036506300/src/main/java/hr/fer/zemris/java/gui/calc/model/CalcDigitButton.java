package hr.fer.zemris.java.gui.calc.model;

import javax.swing.JButton;
/**
 * A digit button on the calculator
 * @author Vedran Kolka
 *
 */
public class CalcDigitButton extends JButton {

	private static final long serialVersionUID = 1L;
	/** The digit of this button */
	private int digit;
	
	public CalcDigitButton(String name, int digit, CalcModel calc) {
		super(name);
		this.digit = checkDigit(digit);
		addActionListener( e -> calc.insertDigit(digit));
		setFont(getFont().deriveFont(30f));
	}
	
	
	private int checkDigit(int digit) {
		if(digit < 0 || digit > 9)
			throw new IllegalArgumentException("Digit must be a number between 0 and 9 included. It was: " + digit);
		return digit;
	}


	/**
	 * Getter for digit.
	 * @return digit
	 */
	public int getDigit() {
		return digit;
	}

}
