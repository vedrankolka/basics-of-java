package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexer;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerException;

/**
 * The mkdir command takes a single argument: directory name,
 * and creates the appropriate directory structure.
 * @author Vedran Kolka
 *
 */
public class MkdirShellCommand extends AbstractShellCommand {
	
	public MkdirShellCommand() {
		super("mkdir",
			  "The mkdir command takes a single argument: directory name,"
			  + " and creates the appropriate directory structure.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(!checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			ArgumentsLexer lexer = new ArgumentsLexer(arguments);
			Path path = resolvePath(env, Paths.get(lexer.nextToken().getValue()));
			Files.createDirectories(path);
			env.writeln("Directory " + path.getFileName() + " created.");
		} catch (InvalidPathException | IOException | ArgumentsLexerException e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

}
