package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * A display label for a calculator that registers itself to the given
 * calculator model to update its content.
 * 
 * @author Vedran Kolka
 *
 */
public class CalcDisplayLabel extends JLabel implements CalcValueListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a label and registers it to the given CalcModel <code>calc</code>.
	 * 
	 * @param calc
	 */
	public CalcDisplayLabel(CalcModel calc) {
		calc.addCalcValueListener(this);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setBackground(Color.WHITE);
		setOpaque(true);
		setFont(getFont().deriveFont(30f));
		setForeground(Color.BLACK);
		setHorizontalAlignment(RIGHT);
	}

	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
	}

}
