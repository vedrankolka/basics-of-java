package hr.fer.zemris.java.hw17.jvdraw.model.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.AbstractCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Line;

public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	private int xMin;
	
	private int yMin;
	
	private int xMax;
	
	private int yMax;
	
	private boolean first = true;
	
	@Override
	public void visit(Line line) {
		
		int xMin = Math.min(line.getX0(), line.getX1());
		int xMax = line.getX0() + line.getX1() - xMin;
		int yMin = Math.min(line.getY0(), line.getY1());
		int yMax = line.getY0() + line.getY1() - yMin;
		
		setExtrems(xMin, xMax, yMin, yMax);
	}

	@Override
	public void visit(Circle circle) {
		visitAbstractCircle(circle);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visitAbstractCircle(filledCircle);
	}

	public Rectangle getBoundingBox() {
		return new Rectangle(xMin, yMin, xMax - xMin + 1, yMax - yMin + 1);
	}
	
	private void visitAbstractCircle(AbstractCircle circle) {
		
		int xMin = circle.getX0() - circle.getRadius();
		int xMax = circle.getX0() + circle.getRadius();
		int yMin = circle.getY0() - circle.getRadius();
		int yMax = circle.getY0() + circle.getRadius();
		
		setExtrems(xMin, xMax, yMin, yMax);
	}
	
	private void setExtrems(int xMin, int xMax, int yMin, int yMax) {
		
		if (first) {
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			first = false;
		} else {
			if (xMin < this.xMin)
				this.xMin = xMin;
			if (xMax > this.xMax)
				this.xMax = xMax;
			if (yMin < this.yMin)
				this.yMin = yMin;
			if (yMax > this.yMax)
				this.yMax = yMax;
		}
	}

}
