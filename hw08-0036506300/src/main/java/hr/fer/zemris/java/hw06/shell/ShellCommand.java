package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * An interface that models a shell command to be executed in an Environment
 * @author Vedran Kolka
 *
 */
public interface ShellCommand {

	/**
	 * Executes the command in the given Environment <code>env</code>.
	 * @param env
	 * @param arguments
	 * @return ShellStatus after the execution of the command
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * @return name of the command
	 */
	String getCommandName();
	/**
	 * @return a read-only List of Strings - 
	 * description of the command in the form of usage instructions
	 */
	List<String> getCommandDescription(); 
	
}
