package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.GridLayout;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.Circle;

/**
 * A JPanel that has a layout set for editing a {@link Circle}.
 * 
 * @author Vedran Kolka
 *
 */
public class CircleEditor extends AbstractCircleEditor {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param circle to edit
	 */
	public CircleEditor(Circle circle) {
		super(circle);
		initGUI();
	}

	@Override
	protected void initGUI() {
		setLayout(new GridLayout(0, 2));
		super.initGUI();
	}

}
