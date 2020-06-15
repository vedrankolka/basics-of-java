package coloring.algorithms;

import java.util.Objects;
/**
 * A class that represents a pixel with coordinates x and y
 * @author Vedran Kolka
 *
 */
public class Pixel {
	/**
	 * x coordinate of the pixel.
	 */
	public int x;
	/**
	 * y coordinate of the pixel.
	 */
	public int y;
	
	
	
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}
	
}
