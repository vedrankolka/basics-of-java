package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

public class Coloring implements Consumer<Pixel>, Supplier<Pixel>, Predicate<Pixel>, Function<Pixel, List<Pixel>> {

	private Pixel reference;
	private Picture picture;
	private int fillColor;
	private int refColor;

	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> neighbours = new LinkedList<Pixel>();
		if (t.x > 0)
			neighbours.add(new Pixel(t.x - 1, t.y));
		if (t.x < picture.getWidth() - 1)
			neighbours.add(new Pixel(t.x + 1, t.y));
		if (t.y > 0)
			neighbours.add(new Pixel(t.x, t.y - 1));
		if (t.y < picture.getHeight() - 1)
			neighbours.add(new Pixel(t.x, t.y + 1));
		return neighbours;
	}

	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.x, t.y) == refColor;
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}

}
