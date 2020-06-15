package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A command that rotates the direction vector of the TurtleState on top of the stack.
 * @author Vedran Kolka
 *
 */
public class RotateCommand implements Command {
	
	/**
	 * Angle in <b>degrees</b>.
	 */
	private double angle;
	
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.direction.rotate(Math.toRadians(angle));
	}

}
