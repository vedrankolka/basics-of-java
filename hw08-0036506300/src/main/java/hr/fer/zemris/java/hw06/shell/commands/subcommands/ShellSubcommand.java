package hr.fer.zemris.java.hw06.shell.commands.subcommands;

import java.nio.file.Path;

import hr.fer.zemris.java.hw06.shell.Environment;
/**
 * A functional interface for defining subcommands for the massrename command.
 * @param env
 * @param source
 * @param destination
 * @param regex
 */
@FunctionalInterface
public interface ShellSubcommand {
	/**
	 * Executes the command.
	 * @param env
	 * @param source
	 * @param destination
	 * @param regex
	 * @param other
	 */
	void executeCommand(Environment env, Path source, Path destination, String regex, String ...other);
	
}
