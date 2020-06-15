package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * A shell command that prints the working directory.
 * @author Vedran Kolka
 *
 */
public class PwdShellCommand extends AbstractShellCommand {
	
	public PwdShellCommand() {
		super("pwd", "Prints the working directory.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

}
