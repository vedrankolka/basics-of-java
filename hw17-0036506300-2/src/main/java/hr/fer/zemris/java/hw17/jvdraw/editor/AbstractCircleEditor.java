package hr.fer.zemris.java.hw17.jvdraw.editor;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.AbstractCircle;
/**
 * JPanel that has a set layout for editing an {@link AbstractCircle}.
 * @author Vedran Kolka
 *
 */
public abstract class AbstractCircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;
	/** Abstract circle on which the editing is done */
	private AbstractCircle circle;
	/** properties of the circle */
	private int x0;
	private int y0;
	private int radius;
	/** text fields for the circles properties */
	private JTextField x0field = new JTextField();
	private JTextField y0field = new JTextField();
	private JTextField radiusField = new JTextField();
	
	/**
	 * Constructor.
	 * @param circle to edit
	 */
	public AbstractCircleEditor(AbstractCircle circle) {
		super(circle);
		this.circle = circle;
		
		x0field.setText("" + circle.getX0());
		y0field.setText("" + circle.getY0());
		radiusField.setText("" + circle.getRadius());
	}
	
	@Override
	protected void initGUI() {
		add(new JLabel("x0: "));
		add(x0field);
		add(new JLabel("y0: "));
		add(y0field);
		add(new JLabel("radius: "));
		add(radiusField);
		
		super.initGUI();
	}
	
	@Override
	public void checkEditing() {
		super.checkEditing();
		x0 = Integer.parseInt(x0field.getText());
		y0 = Integer.parseInt(y0field.getText());
		radius = Integer.parseInt(radiusField.getText());
	}

	@Override
	public void acceptEditing() {
		super.acceptEditing();
		circle.setX0(x0);
		circle.setY0(y0);
		circle.setRadius(radius);
	}

}
