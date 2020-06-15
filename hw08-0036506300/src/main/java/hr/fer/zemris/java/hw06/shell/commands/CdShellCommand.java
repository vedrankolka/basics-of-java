package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexer;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerException;

/**
 * A shell command that changes the working directory to the directory given
 * through arguments
 * 
 * @author Vedran Kolka
 *
 */
public class CdShellCommand extends AbstractShellCommand {

	public CdShellCommand() {
		super("cd", "Changes the working directory to the given directory.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}

		try {
			ArgumentsLexer lexer = new ArgumentsLexer(arguments);
			Path newDirectory = Paths.get(lexer.nextToken().getValue());
			newDirectory = resolvePath(env, newDirectory).normalize();
			env.setCurrentDirectory(newDirectory);
			env.writeln("Working directory set to: " + newDirectory);

		} catch (ArgumentsLexerException | IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

}
