package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcBinaryInvertableButton;
import hr.fer.zemris.java.gui.calc.model.CalcBinaryOperationButton;
import hr.fer.zemris.java.gui.calc.model.CalcDigitButton;
import hr.fer.zemris.java.gui.calc.model.CalcDisplayLabel;
import hr.fer.zemris.java.gui.calc.model.CalcInvCheckBox;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalcUnaryOperationButton;
import hr.fer.zemris.java.gui.calc.model.Invertible;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * <p>
 * A simple calculator with the basic operations of your everyday calculator.
 * </p>
 * <p>
 * The calculator does not take into consideration priority of operations. It
 * calculates in the given order and cannot calculate expressions at once.
 * </p>
 * <p>
 * The calculator offers inverse operations of trigonometric functions,
 * logarithmic function and power function.
 * </p>
 * 
 * @author Vedran Kolka
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	/** The calculator model which this Calculator's uses for calculation */
	private CalcModel calc;
	/** A stack for storing values in the calculator */
	private Stack<Double> stack;

	public Calculator() {
		this.calc = new CalcModelImpl();
		stack = new Stack<>();
		initGUI();
		pack();
	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(15));
		cp.setBackground(Color.LIGHT_GRAY);
		// display
		add(new CalcDisplayLabel(calc), new RCPosition(1, 1));
		// inv checkbox
		CalcInvCheckBox inv = new CalcInvCheckBox("inv");
		add(inv, new RCPosition(5, 7));
		// 1/x button
		JButton b = new CalcUnaryOperationButton("1/x", x -> 1 / x, "1/x", x -> 1 / x, calc, () -> inv.isSelected());
		add(b, new RCPosition(2, 1));
		inv.addInvertible((Invertible) b);
		// sin button
		b = new CalcUnaryOperationButton("sin", Math::sin, "arcsin", Math::asin, calc, () -> inv.isSelected());
		add(b, new RCPosition(2, 2));
		inv.addInvertible((Invertible) b);
		// log button
		b = new CalcUnaryOperationButton("log", Math::log10, "10^x", d -> Math.pow(10, d), calc,
				() -> inv.isSelected());
		add(b, new RCPosition(3, 1));
		inv.addInvertible((Invertible) b);
		// cos button
		b = new CalcUnaryOperationButton("cos", Math::cos, "arccos", Math::acos, calc, () -> inv.isSelected());
		add(b, new RCPosition(3, 2));
		inv.addInvertible((Invertible) b);
		// ln button
		b = new CalcUnaryOperationButton("ln", Math::log, "e^x", Math::exp, calc, () -> inv.isSelected());
		add(b, new RCPosition(4, 1));
		inv.addInvertible((Invertible) b);
		// tan button
		b = new CalcUnaryOperationButton("tan", Math::tan, "arctan", Math::atan, calc, () -> inv.isSelected());
		add(b, new RCPosition(4, 2));
		inv.addInvertible((Invertible) b);
		// x^n button
		b = new CalcBinaryInvertableButton("x^n", Math::pow, "x^1/n", (x, n) -> Math.pow(x, 1.0 / n), calc,
				() -> inv.isSelected());
		add(b, new RCPosition(5, 1));
		inv.addInvertible((Invertible) b);
		// ctg button
		b = new CalcUnaryOperationButton("ctg", x -> 1 / Math.tan(x), "acrctg", x -> 1 / Math.atan(x), calc,
				() -> inv.isSelected());
		add(b, new RCPosition(5, 2));
		inv.addInvertible((Invertible) b);
		// all digits
		add(new CalcDigitButton("0", 0, calc), new RCPosition(5, 3));
		add(new CalcDigitButton("1", 1, calc), new RCPosition(4, 3));
		add(new CalcDigitButton("2", 2, calc), new RCPosition(4, 4));
		add(new CalcDigitButton("3", 3, calc), new RCPosition(4, 5));
		add(new CalcDigitButton("4", 4, calc), new RCPosition(3, 3));
		add(new CalcDigitButton("5", 5, calc), new RCPosition(3, 4));
		add(new CalcDigitButton("6", 6, calc), new RCPosition(3, 5));
		add(new CalcDigitButton("7", 7, calc), new RCPosition(2, 3));
		add(new CalcDigitButton("8", 8, calc), new RCPosition(2, 4));
		add(new CalcDigitButton("9", 9, calc), new RCPosition(2, 5));
		// +/- button
		b = new JButton("+/-");
		b.addActionListener(e -> calc.swapSign());
		add(b, new RCPosition(5, 4));
		// . button
		b = new JButton(".");
		b.addActionListener(e -> calc.insertDecimalPoint());
		add(b, new RCPosition(5, 5));
		// equals button
		add(getEqualsButton(), new RCPosition(1, 6));
		// division button
		b = new CalcBinaryOperationButton("/", (d1, d2) -> d1 / d2, calc);
		add(b, new RCPosition(2, 6));
		// multiplication button
		b = new CalcBinaryOperationButton("*", (d1, d2) -> d1 * d2, calc);
		add(b, new RCPosition(3, 6));
		// minus button
		b = new CalcBinaryOperationButton("-", (d1, d2) -> d1 - d2, calc);
		add(b, new RCPosition(4, 6));
		// plus button
		b = new CalcBinaryOperationButton("+", Double::sum, calc);
		add(b, new RCPosition(5, 6));
		// clr button
		b = new JButton("clr");
		b.addActionListener(e -> calc.clear());
		add(b, new RCPosition(1, 7));
		// res button
		b = new JButton("res");
		b.addActionListener(e -> calc.clearAll());
		add(b, new RCPosition(2, 7));
		// push button
		b = new JButton("push");
		b.addActionListener(e -> stack.push(calc.getValue()));
		add(b, new RCPosition(3, 7));
		// pop
		b = new JButton("pop");
		b.addActionListener(e -> calc.setValue(stack.pop()));
		add(b, new RCPosition(4, 7));

	}

	/**
	 * Creates and initializes a JButton with an action listener that calculates the
	 * result if needed and sets it as the value.
	 * 
	 * @return initialized equals JButton
	 */
	private JButton getEqualsButton() {
		JButton equalsButton = new JButton("=");
		ActionListener al = e -> {
			double result;
			// if there is a pending operation, calculate the result
			if (calc.getPendingBinaryOperation() != null) {
				result = calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue());
				// else the result is already written in the value
			} else {
				result = calc.getValue();
			}
			calc.setValue(result);
			calc.setPendingBinaryOperation(null);
			calc.clearActiveOperand();
		};
		equalsButton.addActionListener(al);

		return equalsButton;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

}
