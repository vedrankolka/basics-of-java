package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.Line;

public class LineEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;
	
	private Line line;
	
	private int x0;
	private int y0;
	private int x1;
	private int y1;
//	private short r;
//	private short g;
//	private short b;
	
	private JTextField x0field = new JTextField();
	private JTextField y0field = new JTextField();
	private JTextField x1field = new JTextField();
	private JTextField y1field = new JTextField();
//	private JTextField rField = new JTextField();
//	private JTextField gField = new JTextField();
//	private JTextField bField = new JTextField();

	public LineEditor(Line line) {
		super(line);
		this.line = line;
		
		x0field.setText("" + line.getX0());
		y0field.setText("" + line.getY0());
		x1field.setText("" + line.getX1());
		y1field.setText("" + line.getY1());
//		rField.setText("" + line.getColor().getRed());
//		gField.setText("" + line.getColor().getGreen());
//		bField.setText("" + line.getColor().getBlue());
		
		initGUI();
	}
	
	protected void initGUI() {
		setLayout(new GridLayout(0, 2));
		add(new JLabel("x0: "));
		add(x0field);
		add(new JLabel("y0: "));
		add(y0field);
		add(new JLabel("x1: "));
		add(x1field);
		add(new JLabel("y1: "));
		add(y1field);
//		add(new JLabel("r: "));
//		add(rField);
//		add(new JLabel("g: "));
//		add(gField);
//		add(new JLabel("b: "));
//		add(bField);
		super.initGUI();
	}
	
	@Override
	public void checkEditing() {
		x0 = Integer.parseInt(x0field.getText());
		y0 = Integer.parseInt(y0field.getText());
		x1 = Integer.parseInt(x1field.getText());
		y1 = Integer.parseInt(y1field.getText());
//		r  = Short.parseShort(rField.getText());
//		g  = Short.parseShort(gField.getText());
//		b  = Short.parseShort(bField.getText());
		super.checkEditing();
	}

	@Override
	public void acceptEditing() {
		line.setX0(x0);
		line.setY0(y0);
		line.setX1(x1);
		line.setY1(y1);
		super.acceptEditing();
//		line.setColor(new Color(r, g, b));
	}

}
