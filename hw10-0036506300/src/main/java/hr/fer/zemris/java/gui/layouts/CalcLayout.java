package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

/**
 * <p>
 * A layout manager for managing components of a simple calculator. The layout
 * is fixed with 5 rows and 7 columns, as defined in NUMBER_OF_ROWS and NUMBER_OF_COLUMNS.
 * 
 * <p>
 * The component at position (1, 1) takes up 5 places, so positions (1, x), x
 * from 2 to 5 are not valid positions.
 * 
 * @author Vedran Kolka
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	public static final int NUMBER_OF_ROWS = 5;
	public static final int NUMBER_OF_COLUMNS = 7;
	// a map of components in this layout
	private Map<Component, RCPosition> components;
	// a map of components mapped by their RCPosition
	private Map<RCPosition, Component> positions;
	// space between two components next to each other
	private int space;

	/**
	 * Creates a LayoutManager that adds <code>space</code> pixels between
	 * components which are next to each other.
	 * 
	 * @param space
	 */
	public CalcLayout(int space) {
		this.space = space;
		components = new HashMap<>();
		positions = new HashMap<>();
	}

	/**
	 * Creates a LayoutManager that adds 0 pixels between components which are next
	 * to each other.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// free the position in the map of positions
		RCPosition positionToFree = components.get(comp);
		positions.put(positionToFree, null);
		// remove the component from the map
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return requestedLayoutSize(parent, c -> c.getPreferredSize());
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return requestedLayoutSize(target, c -> c.getMaximumSize());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return requestedLayoutSize(parent, c -> c.getMinimumSize());
	}

	private Dimension requestedLayoutSize(Container container, Function<Component, Dimension> f) {
		Dimension dim = null;
		for (Component c : components.keySet()) {
			// if no dimension was found yet, set this as the requested dimension
			if (dim == null) {
				if (isFirst(c)) {
					// the component on position (1, 1) stretches across 5 squares and 4 spaces
					int w = (f.apply(c).width - 4 * space) / 5;
					int h = f.apply(c).height;
					dim = new Dimension(w, h);
				} else {
					dim = f.apply(c);
				}
			} else {
				// else check if c.width is the new maximum or if c.height is the new maximum
				if (isFirst(c)) {
					// the component on position (1, 1) stretches across 5 squares and 4 spaces
					int w = (f.apply(c).width - 4 * space) / 5;
					dim.width = w > dim.width ? w : dim.width;
				} else {
					dim.width = f.apply(c).width > dim.width ? f.apply(c).width : dim.width;
				}
				dim.height = f.apply(c).height > dim.height ? f.apply(c).height : dim.height;
			}
		}
		if (dim != null) {
			dim.width = 7 * dim.width + 6 * space;
			dim.height = 5 * dim.height + 4 * space;
		}
		return dim;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		// calculate the ideal dimensions of a single square on the grid
		double squareWidth = (parent.getWidth() - insets.left - insets.right - 6 * space) / 7.0;
		double squareHeight = (parent.getHeight() - insets.top - insets.bottom - 4 * space) / 5.0;
		// calculate widths of components by columns
		int[] widths = calculateWithRest(squareWidth, NUMBER_OF_COLUMNS);
		// calculate heights of components by rows
		int[] heights = calculateWithRest(squareHeight, NUMBER_OF_ROWS);
		// calculate the starting x coordinate of each component 
		int[] startingXs = new int[NUMBER_OF_COLUMNS];
		startingXs[0] = insets.left;
		for(int i = 1; i < NUMBER_OF_COLUMNS; ++i) {
			// this components starts where the previous ends + space
			startingXs[i] = startingXs[i-1] + widths[i-1] + space; 
		}
		// calculate the starting y coordinate of each component
		int[] startingYs = new int[NUMBER_OF_ROWS];
		startingYs[0] = insets.top;
		for(int i = 1; i < NUMBER_OF_ROWS; ++i) {
			// this components starts where the previous ends + space
			startingYs[i] = startingYs[i-1] + heights[i-1] + space; 
		}

		for (Entry<Component, RCPosition> e : components.entrySet()) {
			RCPosition position = e.getValue();
			int rowIndex = position.getRow() - 1;
			int columnIndex = position.getColumn() - 1;
			int thisWidth;
			// the width for component at position (1, 1) is calculated differently
			if(isFirst(position)) {
				thisWidth = startingXs[5] - space - insets.left;
			} else {
				thisWidth = widths[columnIndex];
			}
			int thisHeight = heights[rowIndex];
			Rectangle r = new Rectangle(startingXs[columnIndex], startingYs[rowIndex], thisWidth, thisHeight);
			e.getKey().setBounds(r);
		}
	}

	/**
	 * Calculates n integer values that are closest to n * wantedValue with uniform
	 * distribution of values.
	 * <p>
	 * e.g. for given<br>
	 * wantedValue = 28.57<br>
	 * n = 7<br>
	 * An array {29, 28, 29, 28, 29, 28, 29} is returned.
	 * 
	 * @param wantedValue - the value around which the integer values will be
	 *                    distributed
	 * @param n           - number of values to return
	 * @return array of integers
	 */
	public static int[] calculateWithRest(double wantedValue, int n) {
		double rest = 0.0;
		int[] values = new int[n];
		for (int i = 0; i < n; ++i) {
			// calculate the height of this row
			values[i] = (int) Math.round(wantedValue);
			// calculate the error of rounding the height
			rest = wantedValue - values[i];
			// adjust the square height for the next height to be rounded correctly
			wantedValue += rest;
		}
		return values;
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof String || constraints instanceof RCPosition) {

			RCPosition position = constraints instanceof String ? parsePosition(comp, (String) constraints)
					: (RCPosition) constraints;
			checkPosition(position, comp);
			positions.put(position, comp);
			components.put(comp, position);
		} else {
			throw new UnsupportedOperationException("constraints must be an instance of RCPosition.");
		}
	}

	/**
	 * Parses the given String into an RCPosition.
	 * <p>
	 * The RCPosition is expected to be two integers separated with a single comma.
	 * 
	 * @param comp
	 * @param string
	 * @return RCPosition
	 * @throws CalcLayoutException if the string cannot be parsed into an RCPosition
	 */
	public RCPosition parsePosition(Component comp, String string) {
		String[] numbers = string.split(",");
		try {
			if (numbers.length != 2) {
				throw new CalcLayoutException("Two numbers separated with a comma were expected.");
			}
			int row = Integer.parseInt(numbers[0]);
			int column = Integer.parseInt(numbers[1]);
			return new RCPosition(row, column);
		} catch (NumberFormatException e) {
			throw new CalcLayoutException("Invalid arguments for row and column: " + string);
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

	/**
	 * Checks if the given position is legal.
	 * 
	 * @param position
	 * @throws CalsLayoutException if the row is not between 1 and 5 or if the
	 *                             column is not between 1 and 7 or if the row is 1
	 *                             and the column is any of 2, 3, 4, or 5 or if the
	 *                             position is already taken by a component
	 */
	private void checkPosition(RCPosition position, Component comp) {
		Objects.requireNonNull(position);
		int row = position.getRow();
		int column = position.getColumn();
		if (row < 1 || row > 5)
			throw new CalcLayoutException("Legal rows are from 1 to 5. Row was: " + row);
		if (column < 1 || column > 7)
			throw new CalcLayoutException("Legal columns are from 1 to 7. Column was: " + column);
		if (row == 1 && column > 1 && column < 6)
			throw new CalcLayoutException("The first row cannot have a component in clumns from 2 to 5");
		Component c = positions.get(position);
		if (c != null && c != comp)
			throw new CalcLayoutException("There already exists a component with the given constraints.");
	}

	/**
	 * Checks if Component <code>c</code> is at position (1, 1).
	 * @param c - component to check
	 * @return <code>true</code> if it is, <code>false</code> otherwise
	 */
	private boolean isFirst(Component c) {
		return isFirst(components.get(c));
	}

	/**
	 * CHecks if given Position <code>p</code> is position (1, 1)
	 * @param p - position to check
	 * @return <code>true</code> if it is, <code>false</code> otherwise
	 */
	private boolean isFirst(RCPosition p) {
		return p.getRow() == 1 && p.getColumn() == 1;
	}

}
