package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.FilledCircle;

/**
 * JPanel that has a set layout for editing a {@link FilledCircle}.
 * 
 * @author Vedran Kolka
 *
 */
public class FilledCircleEditor extends AbstractCircleEditor {

	private static final long serialVersionUID = 1L;
	/** FilledCircle to edit */
	private FilledCircle filledCircle;
	/** fill color components of the circle */
	private short rFill;
	private short gFill;
	private short bFill;
	/** TextFields for the fill color components */
	private JTextField rFillField = new JTextField();
	private JTextField gFillField = new JTextField();
	private JTextField bFillField = new JTextField();

	/**
	 * Constructor.
	 * 
	 * @param filledCircle to edit
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super(filledCircle);
		this.filledCircle = filledCircle;

		rFillField.setText("" + filledCircle.getFillColor().getRed());
		gFillField.setText("" + filledCircle.getFillColor().getGreen());
		bFillField.setText("" + filledCircle.getFillColor().getBlue());

		initGUI();
	}

	@Override
	protected void initGUI() {
		setLayout(new GridLayout(0, 2));
		super.initGUI();
		add(new JLabel("r fill: "));
		add(rFillField);
		add(new JLabel("g fill: "));
		add(gFillField);
		add(new JLabel("b fill: "));
		add(bFillField);
	}

	@Override
	public void checkEditing() {
		super.checkEditing();
		rFill = Short.parseShort(rFillField.getText());
		gFill = Short.parseShort(gFillField.getText());
		bFill = Short.parseShort(bFillField.getText());
	}

	@Override
	public void acceptEditing() {
		super.acceptEditing();
		filledCircle.setFillColor(new Color(rFill, gFill, bFill));
	}

}
