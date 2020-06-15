package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleBinaryOperator;
import java.util.function.Supplier;

public class CalcBinaryInvertableButton extends CalcBinaryOperationButton implements Invertible {

	private static final long serialVersionUID = 1L;
	/** The basic operation and its label for the button */
	private String basicLabel;
	private DoubleBinaryOperator basicOperation;
	/** The inverted operation and its label for the button */
	private String invertedLabel;
	private DoubleBinaryOperator invertedOperation;
	/** A supplier that states if the current operation is inverted or not */
	private Supplier<Boolean> inverted;
	
	public CalcBinaryInvertableButton(String basicLabel, DoubleBinaryOperator basicOperation,
			String invertedLabel, DoubleBinaryOperator invertedOperation, CalcModel calc, Supplier<Boolean> inverted) {
		super(basicLabel, basicOperation, calc);
		this.basicLabel = basicLabel;
		this.basicOperation = basicOperation;
		this.invertedLabel = invertedLabel;
		this.invertedOperation = invertedOperation;
		this.inverted = inverted;
		invert();
	}
	
	/**
	 * Changes the label to the one that should be set.
	 */
	@Override
	public void invert() {
		String text = inverted.get() ? invertedLabel : basicLabel;
		super.setText(text);
	}
	
	@Override
	public DoubleBinaryOperator getOperation() {
		return inverted.get() ? invertedOperation : basicOperation;
	}

	
}
