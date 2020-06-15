package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.swing.JCheckBox;

/**
 * A checkbox that has a set of invertible buttons registered to it.
 * 
 * @author Vedran Kolka
 *
 */
public class CalcInvCheckBox extends JCheckBox {

	private static final long serialVersionUID = 1L;
	/** A set of invertibles interested in the state of this checkbox */
	private Set<Invertible> invertibles;

	public CalcInvCheckBox(String name) {
		super(name);
		this.invertibles = new HashSet<>();
		addActionListener(e -> invertibles.forEach(i -> i.invert()));
		setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Adds the given Invertible <code>i</code> to the set of invertibles.
	 * 
	 * @param i
	 * @throws NullPointerException if <code>i</code> is <code>null</code>
	 */
	public void addInvertible(Invertible i) {
		invertibles.add(Objects.requireNonNull(i));
	}

	/**
	 * Removes the given Invertible <code>i</code> from the set of invertibles.
	 * 
	 * @param i
	 * @throws NullPointerException if <code>i</code> is <code>null</code>
	 */
	public void removeInvertible(Invertible i) {
		invertibles.remove(Objects.requireNonNull(i));
	}

}
