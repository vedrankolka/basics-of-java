package hr.fer.zemris.java.hw17.jvdraw.model.visitors;

import hr.fer.zemris.java.hw17.jvdraw.model.objects.AbstractCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Line;

public class GeometricalObjectFormatter implements GeometricalObjectVisitor {

	private String formatted = "";

	@Override
	public void visit(Line line) {
		formatted += new StringBuilder().append("LINE")
				.append(' ').append(line.getX0())
				.append(' ').append(line.getY0())
				.append(' ').append(line.getX1())
				.append(' ').append(line.getY1())
				.append(' ').append(line.getColor().getRed())
				.append(' ').append(line.getColor().getGreen())
				.append(' ').append(line.getColor().getBlue())
				.toString();
		formatted += "\n";
	}

	@Override
	public void visit(Circle circle) {
		visit((AbstractCircle) circle);
		formatted += "\n";
	}

	@Override
	public void visit(FilledCircle filledCircle) {

		formatted += "F";
		visit((AbstractCircle) filledCircle);
		formatted += " " + filledCircle.getFillColor().getRed() + " " + filledCircle.getFillColor().getGreen() + " "
				+ filledCircle.getFillColor().getBlue();
		formatted += "\n";
	}

	private void visit(AbstractCircle circle) {
		formatted += new StringBuilder().append("CIRCLE").append(' ').append(circle.getX0()).append(' ')
				.append(circle.getY0()).append(' ').append(circle.getRadius()).append(' ')
				.append(circle.getColor().getRed()).append(' ').append(circle.getColor().getGreen()).append(' ')
				.append(circle.getColor().getBlue()).toString();
	}

	public String getFormatted() {
		return formatted;
	}

}
