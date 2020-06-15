package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;
/**
 * A command that moves the turtle but does not leave a mark.
 * @author Vedran Kolka
 *
 */
public class SkipCommand implements Command {
	
	/**
	 * The factor to scale the delta of the turtle.
	 */
	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		//get the state to change
		TurtleState state = ctx.getCurrentState();
		//calculate the offset by scaling the direction
		Vector2D offset = state.direction.scaled(state.delta*step);
		//then translate the position vector
		state.position.translate(offset);
	}

}
