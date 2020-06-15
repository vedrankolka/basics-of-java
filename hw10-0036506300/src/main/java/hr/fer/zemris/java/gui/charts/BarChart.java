package hr.fer.zemris.java.gui.charts;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A simple bar chart that stores data modeled by XYValues.
 * 
 * @author Vedran Kolka
 *
 */
public class BarChart {
	/** Values of this bar chart */
	private List<XYValue> xyValues;
	/** The text that labels the xAxis */
	private String xLabel;
	/** The text that labels the yAxis */
	private String yLabel;
	/** The minimum y value this bar chart can show */
	private int yMin;
	/** The maximum y value this bar chart can show */
	private int yMax;
	/** The difference between two y values on the chart */
	private int deltaY;

	/**
	 * Creates a BarChart with the given arguments.
	 * 
	 * @param xyValues
	 * @param xLabel
	 * @param yLabel
	 * @param yMin
	 * @param yMax
	 * @param deltaY
	 * @throws IllegalArgumentException if yMin is negative or if yMax is less than
	 *                                  yMin or if deltaY is less than yMax - yMin
	 *                                  or if a y component in the given XYValues is
	 *                                  less than yMin
	 * @throws NullPointerException     if <code>xyVaules</code> is
	 *                                  <code>null</code>
	 */
	public BarChart(List<XYValue> xyValues, String xLabel, String yLabel, int yMin, int yMax, int deltaY) {
		this.xyValues = Objects.requireNonNull(xyValues);
		Comparator<XYValue> comparator = (v1, v2) -> Integer.compare(v1.getX(), v2.getX());
		xyValues.sort(comparator);
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		if (yMin < 0) {
			throw new IllegalArgumentException("yMin cannot be negative.");
		}
		this.yMin = yMin;
		if (yMax <= yMin) {
			throw new IllegalArgumentException("yMax must be greater than yMin.");
		}
		this.yMax = yMax;
		this.deltaY = determineDeltaY(deltaY);
		// check the given y of all the elements in the list
		for (XYValue xyv : xyValues) {
			if (xyv.getY() < yMin) {
				throw new IllegalArgumentException("A given XYValue in the list has a y component lower than yMin.");
			}
		}
	}

	/**
	 * Determines the first deltaY equal to or greater than the given
	 * <code>deltaY</code> that divides the difference between yMax and yMin.
	 * 
	 * @param deltaY
	 * @return first found divisor of yMax - yMin starting from <code>deltaY</code>
	 * @throws IllegalArgumentException if <code>deltaY</code> is less than yMax -
	 *                                  yMin
	 */
	private int determineDeltaY(int deltaY) {
		if (yMax - yMin < deltaY) {
			throw new IllegalArgumentException("deltaY must be less that the difference between yMax and yMin.");
		}
		while ((yMax - yMin) % deltaY != 0) {
			deltaY++;
		}
		return deltaY;
	}

	public List<XYValue> getXyValues() {
		return xyValues;
	}

	public String getxLabel() {
		return xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getDeltaY() {
		return deltaY;
	}

}
