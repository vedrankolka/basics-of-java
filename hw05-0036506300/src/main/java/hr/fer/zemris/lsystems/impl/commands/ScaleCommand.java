package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Scales the delta of the state on top of the stack.
 * @author Vedran Kolka
 *
 */
public class ScaleCommand implements Command {

	/**
	 * The factor to scale the delta of the state on top of the stack.
	 */
	private double factor;
	
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.delta *= state.delta * factor;
	}

}
