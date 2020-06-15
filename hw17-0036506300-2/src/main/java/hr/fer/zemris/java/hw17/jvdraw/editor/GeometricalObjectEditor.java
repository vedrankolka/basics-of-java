package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.GeometricalObject;

/**
 * A JPanel that has a layout set for editing a {@link GeometricalObject}.
 * 
 * @author Vedran Kolka
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	/** Object to edit */
	private GeometricalObject go;
	/** COlor components of the object */
	private short r;
	private short g;
	private short b;
	/** Text fields for the color components */
	private JTextField rField = new JTextField();
	private JTextField gField = new JTextField();
	private JTextField bField = new JTextField();

	/**
	 * COnstructor.
	 * 
	 * @param go geometrical object to edit
	 */
	public GeometricalObjectEditor(GeometricalObject go) {
		this.go = go;

		rField.setText("" + go.getColor().getRed());
		gField.setText("" + go.getColor().getGreen());
		bField.setText("" + go.getColor().getBlue());
	}

	/**
	 * Initializes all the GUI components of the panel.
	 */
	protected void initGUI() {
		add(new JLabel("r: "));
		add(rField);
		add(new JLabel("g: "));
		add(gField);
		add(new JLabel("b: "));
		add(bField);
	}

	/**
	 * Checks if all the text fields are filled correctly.
	 */
	public void checkEditing() {
		r = Short.parseShort(rField.getText());
		g = Short.parseShort(gField.getText());
		b = Short.parseShort(bField.getText());
	}

	/**
	 * Makes the edit permanent.
	 * <p>
	 * Should be called only <b>after</b> the
	 * {@link #checkEditing()} method.
	 */
	public void acceptEditing() {
		go.setColor(new Color(r, g, b));
	}
}
