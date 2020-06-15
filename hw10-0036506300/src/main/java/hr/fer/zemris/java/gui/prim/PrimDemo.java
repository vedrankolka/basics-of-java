package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A program that shows two views of the same list of prime numbers, generating
 * numbers on the click of the button sljedeći.
 * 
 * @author Vedran Kolka
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public PrimDemo() {
		setSize(300, 200);
		setTitle("PrimDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel listModel = new PrimListModel();
		JList<Integer> leftList = new JList<>(listModel);
		JList<Integer> rightList = new JList<>(listModel);

		JPanel p = new JPanel(new GridLayout(1, 0));

		p.add(new JScrollPane(leftList), JSplitPane.LEFT);
		p.add(new JScrollPane(rightList), JSplitPane.RIGHT);
		cp.add(p, BorderLayout.CENTER);
		JButton b = new JButton("sljedeći");
		b.addActionListener(e -> {
			listModel.next();
		});
		cp.add(b, BorderLayout.PAGE_END);
	}

	/**
	 * Creates a new PrimDemo frame and sets it to visible.
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

}
