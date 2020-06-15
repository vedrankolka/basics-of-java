package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

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
		
		if(!ArgumentsParser.checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Path path = ArgumentsParser.parsePath(arguments);
			Files.createDirectories(path);
		} catch (InvalidPathException | IOException e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

}
