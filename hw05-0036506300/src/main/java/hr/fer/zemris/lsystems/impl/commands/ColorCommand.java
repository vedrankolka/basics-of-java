package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A command that sets the color of the state on top of the stack to the given <code>color</code>.
 * @author Vedran Kolka
 *
 */
public class ColorCommand implements Command {
	
	/**
	 * The color to which the state is set.
	 */
	private Color color;
	
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.color = color;
	}

}
