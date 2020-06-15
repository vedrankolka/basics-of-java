package hr.fer.zemris.java.gui.calc.model;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * An implementation of a simple CalcModel that offers basic mathematical
 * operations.
 * 
 * @author Vedran Kolka
 *
 */
public class CalcModelImpl implements CalcModel {

	/** States if the content in the calculator is editable */
	private boolean editable;
	/** States if the current value in the calculator is positive */
	private boolean positive;
	/** Value in the calculator represented by a string */
	private String valueString;
	/** Value in the calculator */
	private double value;
	/** First operand if the operation is binary */
	private Double activeOperand;
	/** Binary operation that is waiting for the second operand to be executed */
	private DoubleBinaryOperator pendingBinaryOperation;
	/** A set of listeners interested in the changes of the value of this model */
	private Set<CalcValueListener> listeners;

	public CalcModelImpl() {
		editable = true;
		positive = true;
		valueString = "";
		value = 0.0;
		activeOperand = null;
		pendingBinaryOperation = null;
		listeners = new HashSet<>();
	}
	/**
	 * Notifies all listeners registered to this CalcModel that a value change .
	 */
	private void notifyListeners() {
		listeners.forEach(l -> l.valueChanged(this));
	}

	/**
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	/**
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		valueString = Double.toString(value);
		editable = false;
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		value = 0.0;
		valueString = "";
		editable = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		setPendingBinaryOperation(null);
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		checkEditable();
		positive = !positive;
		value *= -1;
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		checkEditable();
		if (valueString.length() == 0)
			throw new CalculatorInputException("There cannot be a decimal point with no digits before it.");
		if (valueString.contains(".")) {
			throw new CalculatorInputException("A decimal point already exists.");
		}
		valueString += ".";
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		checkEditable();
		// extra zeros before a decimal point are ignored
		if (value == 0 && digit == 0 && valueString.length() > 0 && !valueString.contains("."))
			return;
		// if the number is zero and a digit other than zero occurs, remove the leading
		// zero
		if (value == 0 && digit != 0 && valueString.equals("0")) {
			valueString = "";
		}
		String newValueString = valueString + digit;
		try {
			double newValue = Double.parseDouble(newValueString);
			if (!Double.isFinite(newValue))
				throw new CalculatorInputException("Too many digits.");
			value = positive ? newValue : -newValue;
			valueString = newValueString;
		} catch (NumberFormatException e) {
			throw new CalculatorInputException();
		}
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand == null) {
			throw new IllegalStateException("No active operand is set.");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingBinaryOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingBinaryOperation = op;
	}

	@Override
	public String toString() {
		if (valueString.length() == 0) {
			return (positive ? "0" : "-0");
		}
		return (positive ? valueString : "-" + valueString);
	}

	/**
	 * Checks if the model is editable
	 * 
	 * @throws CalculatorInputException if it is not
	 */
	private void checkEditable() {
		if (!editable)
			throw new CalculatorInputException("The model is not editable.");
	}

}
