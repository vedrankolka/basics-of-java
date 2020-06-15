package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * <p>
 * The program takes a path to a file as a command line argument. The file
 * should have at least 5 lines to define a bar chart to draw. The lines should
 * go as follows:
 * </p>
 * <ol>
 * <li>xAxis label
 * <li>yAxis label
 * <li>x1,y1 x2,y2 ... (data)
 * <li>yMin
 * <li>yMax
 * <li>deltaY
 * </ol>
 * 
 * @author Vedran Kolka
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public BarChartDemo(String path, BarChart barChart) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(415, 415));
		JPanel pathPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pathPanel.add(new JLabel(path));
		add(pathPanel, BorderLayout.PAGE_START);
		add(new BarChartComponent(barChart), BorderLayout.CENTER);
		pack();
	}

	/**
	 * Creates a frame with a BarChartComponent that reads data from a file whose
	 * path is given through the command line.
	 * 
	 * @param args a single argument: path to a file with data for the bar chart
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("The program takes exactly one argument - a path to a file.");
			return;
		}
		BarChart barChart;
		try (BufferedReader is = Files.newBufferedReader(Paths.get(args[0]))) {
			String xLabel = is.readLine();
			String yLabel = is.readLine();
			List<XYValue> xyValues = readXYValues(is.readLine().split("\\s+"));
			int yMin = Integer.parseInt(is.readLine());
			int yMax = Integer.parseInt(is.readLine());
			int deltaY = Integer.parseInt(is.readLine());
			barChart = new BarChart(xyValues, xLabel, yLabel, yMin, yMax, deltaY);
		} catch (IOException | IllegalArgumentException e) {
			System.err.println("Reading failed: " + e.getMessage());
			return;
		}

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(args[0], barChart).setVisible(true);
		});

	}

	/**
	 * Returns a list of XYValues that are written in the given array of strings,
	 * where the x and y are expected to be separated by a comma.
	 * 
	 * @param values
	 * @return
	 */
	private static List<XYValue> readXYValues(String[] values) {
		List<XYValue> xyValues = new ArrayList<>();
		for (String s : values) {
			String[] pair = s.split(",");
			int x = Integer.parseInt(pair[0]);
			int y = Integer.parseInt(pair[1]);
			xyValues.add(new XYValue(x, y));
		}
		return xyValues;
	}

}
