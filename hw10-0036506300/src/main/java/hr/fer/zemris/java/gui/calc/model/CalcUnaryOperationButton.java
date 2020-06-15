package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;

import javax.swing.JButton;

/**
 * A button of the calculator for unary operations. An unary operation has its
 * inverted operation and this can be toggled with the method invert.
 * 
 * @author Vedran Kolka
 *
 */
public class CalcUnaryOperationButton extends JButton implements Invertible {

	private static final long serialVersionUID = 1L;
	/** The basic operation and its label for the button */
	private String basicLabel;
	private DoubleUnaryOperator basicOperation;
	/** The inverted operation and its label for the button */
	private String invertedLabel;
	private DoubleUnaryOperator invertedOperation;
	/** A supplier to state which operation is set */
	private Supplier<Boolean> inverted;

	/**
	 * Creates a button with the given operations and labels and the default
	 * operation set to <code>basicOperation</code> and the text set to
	 * <code>basicLabel</code>. Also adds an ActionListener to the given button to
	 * calculate when the button is clicked.
	 * 
	 * @param basicLabel
	 * @param basicOperation
	 * @param invertedLabel
	 * @param invertedOperation
	 * @param calc              - the CalcModel for which this buttons action
	 *                          listener calculates
	 */
	public CalcUnaryOperationButton(String basicLabel, DoubleUnaryOperator basicOperation, String invertedLabel,
			DoubleUnaryOperator invertedOperation, CalcModel calc, Supplier<Boolean> inverted) {
		this.basicLabel = basicLabel;
		this.basicOperation = basicOperation;
		this.invertedLabel = invertedLabel;
		this.invertedOperation = invertedOperation;
		this.inverted = inverted;
		invert();
		addActionListener(e -> {
			calc.setValue(getOperation().applyAsDouble(calc.getValue()));
		});
	}

	/**
	 * Changes the label to the one that should be set.
	 */
	@Override
	public void invert() {
		String text = inverted.get() ? invertedLabel : basicLabel;
		setText(text);
	}

	/**
	 * Getter for operation.
	 * 
	 * @return basic or inverted operation, based on the supplier <code>inverted</code>.
	 */
	public DoubleUnaryOperator getOperation() {
		return inverted.get() ? invertedOperation : basicOperation;
	}
}
