package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * A component that shows the bar chart with data given through the constructor
 * in a BarChart object.
 * 
 * @author Vedran Kolka
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	/** a fixed number of pixels for spaces between elements in the graph and margins */
	private static final int MARGIN_SPACE = 8;
	/** a fixed number of pixels for small spaces between elements on the graph */
	private static final int SMALL_SPACE = 4;
	/** bar chart with data to show */
	private BarChart bc;

	/**
	 * Constructor.
	 * @param barChart whose data is to be drawn on the component
	 */
	public BarChartComponent(BarChart barChart) {
		this.bc = barChart;
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		// calculate some coordinates
		Metrics m = new Metrics(g2);
		// paint yLabel and xLabel
		paintYLabel(g2, m);
		paintXLabel(g2, m);
		// paint axes
		painAxes(g2, m);
		// paint the marks on the y axis and marks and squares on x axis
		markYAxis(g2, m);
		markXAxisAndBars(g2, m);

	}

	private void paintYLabel(Graphics2D g2, Metrics m) {
		// rotate the coordinates
		AffineTransform saveAt = g2.getTransform();
		AffineTransform t = AffineTransform.getQuadrantRotateInstance(3);
		g2.transform(t);
		// x is now actually y because of the rotation
		int yLabelXCoordinateOfRotated = -m.origin.y + m.graphHeight / 2 - m.fm.stringWidth(bc.getyLabel()) / 2;
		int yLabelYCoordinateOfRotated = MARGIN_SPACE + m.fm.getAscent();
		g2.drawString(bc.getyLabel(), yLabelXCoordinateOfRotated, yLabelYCoordinateOfRotated);
		// return the saved transform
		g2.setTransform(saveAt);

	}

	private void paintXLabel(Graphics2D g2, Metrics m) {
		int xLabelXCoordinate = m.origin.x + m.graphWidth / 2 - m.fm.stringWidth(bc.getxLabel()) / 2;
		int xLabelYCoordinate = m.origin.y + SMALL_SPACE + m.stringHeight() + SMALL_SPACE + m.fm.getAscent();
		g2.drawString(bc.getxLabel(), xLabelXCoordinate, xLabelYCoordinate);
	}

	private void painAxes(Graphics2D g2, Metrics m) {
		int x0 = m.origin.x;
		int y0 = m.origin.y;
		// paint the y axis
		// first save the current stroke, then set a thicker stroke
		Stroke savedStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		
		g2.drawLine(x0, y0 + SMALL_SPACE, x0, y0 - m.graphHeight - SMALL_SPACE);
		g2.drawLine(x0 - SMALL_SPACE, y0, x0 + m.graphWidth + SMALL_SPACE, y0);
		// restore the saved stroke
		g2.setStroke(savedStroke);

		// calculate triangle points on the y axis and fill it
		int[] yAxisTriangleXs = new int[] { x0 - SMALL_SPACE, x0 + SMALL_SPACE, x0 };
		int[] yAxisTriangleYs = new int[] { y0 - m.graphHeight - SMALL_SPACE, y0 - m.graphHeight - SMALL_SPACE,
				y0 - m.graphHeight - MARGIN_SPACE - SMALL_SPACE };
		g2.fillPolygon(yAxisTriangleXs, yAxisTriangleYs, 3);

		// calculate triangle points on the x axis and fill it
		int[] xAxisTriangleXs = new int[] { x0 + m.graphWidth + SMALL_SPACE,
				x0 + m.graphWidth + MARGIN_SPACE + SMALL_SPACE, x0 + m.graphWidth + SMALL_SPACE };
		int[] xAxisTriangleYs = new int[] { y0 - SMALL_SPACE, y0, y0 + SMALL_SPACE };
		g2.fillPolygon(xAxisTriangleXs, xAxisTriangleYs, 3);
	}

	private void markYAxis(Graphics2D g2, Metrics m) {

		int x0 = m.origin.x;
		int y0 = m.origin.y;

		// where the widest number starts
		int xStart = x0 - 2 * SMALL_SPACE - m.yValueMaxWidth;

		int heightRemaining = m.graphHeight;
		int numberOfMarks = (bc.getyMax() - bc.getyMin()) / bc.getDeltaY();

		for (int y = y0, yValue = bc.getyMin(), i = numberOfMarks; yValue <= bc.getyMax(); yValue += bc
				.getDeltaY(), --i) {
			// pad to align right with the y axis
			String value = Integer.toString(yValue);
			int xNumber = xStart + (m.yValueMaxWidth - m.fm.stringWidth(value));
			// shift for half an ascent to line up with the mark
			g2.drawString(value, xNumber, y + m.fm.getAscent() / 2 - 2);
			// paint the mark
			g2.drawLine(x0, y, x0 - SMALL_SPACE, y);
			int realYstep = i == 0 ? heightRemaining : heightRemaining / i;
			heightRemaining -= realYstep;
			// increase y for the real step that can be taken
			y -= realYstep;
		}

	}

	private void markXAxisAndBars(Graphics2D g2, Metrics m) {

		int widthRemaining = m.graphWidth;
		int xMin = m.xMin;
		int xMax = m.xMax;

		int x0 = m.origin.x;
		int y0 = m.origin.y;

		List<XYValue> values = bc.getXyValues();
		int thisWidth = 0;
		int index = 0;
		for (int i = xMin, xLeft = x0; i <= xMax; ++i, xLeft += thisWidth) {
			// calculate width of this bar and subtract it from the remaining width
			thisWidth = (int) (Math.round((widthRemaining * 1.0) / (xMax + 1 - i)));
			widthRemaining -= thisWidth;
			// draw the number below the x axis
			String number = Integer.toString(i);
			g2.drawString(number, xLeft + thisWidth / 2 - m.fm.stringWidth(number) / 2,
					y0 + SMALL_SPACE + m.fm.getAscent());

			// draw the mark
			g2.drawLine(xLeft + thisWidth, y0, xLeft + thisWidth, y0 + SMALL_SPACE);

			// draw the bar if there is a XYValue with that x
			if (i == values.get(index).getX()) {
				g2.setColor(Color.ORANGE);
				int yValue = m.graphHeight * (values.get(index).getY() - bc.getyMin()) / (bc.getyMax() - bc.getyMin());
				int[] xCoordinates = new int[] { xLeft + 2, xLeft + thisWidth - 1, xLeft + thisWidth - 1, xLeft + 2 };
				int[] yCoordinates = new int[] { y0, y0, y0 - yValue, y0 - yValue };
				g2.fillPolygon(xCoordinates, yCoordinates, 4);
				g2.setColor(Color.BLACK);
				index++;
			}
		}

	}

	/**
	 * A private class that calculates and hold all relevant information about the
	 * dimensions and relevant points in the BarChartComponent
	 * 
	 * @author Vedran Kolka
	 *
	 */
	private class Metrics {
		// origin of the coordinate system on the graph
		private Point origin;
		// height of the available space on the graph
		private int graphHeight;
		// width of the available space on the graph
		private int graphWidth;
		// font metrics of this component
		private FontMetrics fm;
		// maximum width of a number on the y axis
		private int yValueMaxWidth;
		// minimum x in the values
		private int xMin;
		// maximum x in the values
		private int xMax;

		private Metrics(Graphics2D g2) {
			// initialize the easy ones
			fm = g2.getFontMetrics();
			yValueMaxWidth = getMaxWidth(fm);
			// initialize the origin
			int x0 = MARGIN_SPACE + stringHeight() + SMALL_SPACE + yValueMaxWidth + MARGIN_SPACE;
			int y0 = getSize().height - (MARGIN_SPACE + stringHeight() + SMALL_SPACE + stringHeight() + SMALL_SPACE);
			origin = new Point(x0, y0);
			// initialize graph dimensions
			graphWidth = getSize().width - x0 - 2 * MARGIN_SPACE;
			graphHeight = getSize().height - 3 * MARGIN_SPACE - 2 * SMALL_SPACE - 2 * stringHeight();
			
			xMax = bc.getXyValues().get(bc.getXyValues().size() - 1).getX();
			xMin = bc.getXyValues().get(0).getX();

		}

		private int stringHeight() {
			return fm.getAscent() + fm.getDescent();
		}

		/**
		 * Returns the maximum width of the y values for this barChart.
		 * 
		 * @return width of the column of y values
		 */
		private int getMaxWidth(FontMetrics fm) {
			int lenYMax = fm.stringWidth(Integer.toString(bc.getyMax()));
			int lenYMin = fm.stringWidth(Integer.toString(bc.getyMin()));
			return lenYMax > lenYMin ? lenYMax : lenYMin;
		}

	}

}
