package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Models a command for the turtle to execute.
 * @author Vedran Kolka
 *
 */
public interface Command {

	/**
	 * Models the action of the command.
	 * @param ctx - context of the turtle's states
	 * @param painter - the object used to draw on the screen
	 */
	void execute(Context ctx, Painter painter);
	
}
