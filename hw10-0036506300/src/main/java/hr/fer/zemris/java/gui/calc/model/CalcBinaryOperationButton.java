package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * A button of the calculator for binary operations.
 * 
 * @author Vedran Kolka
 *
 */
public class CalcBinaryOperationButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private DoubleBinaryOperator operation;

	public CalcBinaryOperationButton(String name, DoubleBinaryOperator operation, CalcModel calc) {
		super(name);
		this.operation = operation;
		addActionListener( e -> {
			if(calc.isActiveOperandSet()) {
				double left = calc.getActiveOperand();
				double right = calc.getValue();
				double result = calc.getPendingBinaryOperation().applyAsDouble(left, right);
				calc.setValue(result);
			}
			calc.setActiveOperand(calc.getValue());
			calc.setPendingBinaryOperation(getOperation());
			calc.clear();
		});
	}
	
	

	/**
	 * Getter for operation.
	 * 
	 * @return operation
	 */
	public DoubleBinaryOperator getOperation() {
		return operation;
	}

	/**
	 * Returns the result of this buttons <code>operation</code>.
	 * 
	 * @param d1 - first operand
	 * @param d2 - second operand
	 * @return result of the <code>operation</code>
	 */
	public double operate(double d1, double d2) {
		return getOperation().applyAsDouble(d1, d2);
	}

}
