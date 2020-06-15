package hr.fer.zemris.java.gui.layouts.demo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

public class DemoFrame2 extends JFrame{

	private static final long serialVersionUID = 1L;

	public DemoFrame2() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}
	
	private void initGUI() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("x"), "1,1");
		p.add(new JLabel("y"), "2,3");
		p.add(new JLabel("z"), "2,7");
		p.add(new JLabel("w"), "4,2");
		p.add(new JLabel("a"), "4,5");
		p.add(new JLabel("b"), "4,7");
		getContentPane().add(p);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame2().setVisible(true);
		});
	}
	
}
