package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class DemoFrame1 extends JFrame {
	
	public static final int MIN = 0;
	
	public static final int MAX = 1;

	private static final long serialVersionUID = 1L;

	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}
	
	public DemoFrame1(int width, int height) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(width, height);
		initGUI();
	}
	
	public DemoFrame1(int windowSizeConstant) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		switch(windowSizeConstant) {
		case 0:
			setSize(getMinimumSize());
			break;
		case 1:
			setSize(getMaximumSize());
			break;
		default:
			setSize(getPreferredSize());
		}
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
		JLabel l1 = l("Moja labela");
		l1.setMinimumSize(new Dimension(30, 10));
		cp.add(l1, new RCPosition(1, 6));
	}

	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame1(0).setVisible(true);
		});
	}
}
