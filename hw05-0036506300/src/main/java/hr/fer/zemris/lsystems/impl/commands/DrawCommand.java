package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A command that moves the turtle and leaves a mark.
 * @author Vedran Kolka
 *
 */
public class DrawCommand implements Command {
	
	/**
	 * The factor to scale the delta of the turtle.
	 */
	private double step;
	
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		//get the state to change
		TurtleState state = ctx.getCurrentState();
		//save the current position for drawing
		double oldX = state.position.getX();
		double oldY = state.position.getY();
		//calculate the offset by scaling the direction
		Vector2D offset = state.direction.scaled(state.delta*step);
		//then translate the position vector
		state.position.translate(offset);
		//draw the line
		double newX = state.position.getX();
		double newY = state.position.getY();
		painter.drawLine(oldX, oldY, newX, newY, state.color, 1f);
	}

}
