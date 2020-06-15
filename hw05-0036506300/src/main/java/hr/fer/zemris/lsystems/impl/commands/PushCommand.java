package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A command that copies the state from the top of the stack and pushes it on the stack.
 * @author Vedran Kolka
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copy = ctx.getCurrentState().copy();
		ctx.pushState(copy);
	}

}
