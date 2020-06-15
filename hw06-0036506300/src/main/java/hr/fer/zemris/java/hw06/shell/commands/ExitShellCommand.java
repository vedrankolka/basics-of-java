package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Terminates the shell.
 * @author Vedran Kolka
 *
 */
public class ExitShellCommand extends AbstractShellCommand {

	public ExitShellCommand() {
		super("exit", "Terminates the shell.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.write("Goodbye!");
		return ShellStatus.TERMINATE;
	}

}
