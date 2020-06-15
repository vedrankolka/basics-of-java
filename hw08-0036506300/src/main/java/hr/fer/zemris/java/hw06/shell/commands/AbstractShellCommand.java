package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
/**
 * An abstract representation of a ShellCommand for implementing only
 * methods that are equal in all commands.
 * @author Vedran Kolka
 *
 */
public abstract class AbstractShellCommand implements ShellCommand {
	
	public static final String CD_STACK_KEY = "cdstack";
	/**
	 * Name of the command.
	 */
	private String name;
	/**
	 * Unmodifiable List of strings describing the command.
	 */
	private List<String> description;
	
	/**
	 * Sets the command name to given <code>name</code> and
	 * creates an unmodifiable List of strings from the given <code>descriptionLines</code>
	 * and sets it as <code>description</code>.
	 * @param name
	 * @param descriptionLines
	 */
	public AbstractShellCommand(String name, String ...descriptionLines ) {
		this.name = name;
		this.description = Collections.unmodifiableList(Arrays.asList(descriptionLines));
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}
	
	/**
	 * Checks if the given <code>arguments</code> are not null and not an empty
	 * string.
	 * @param arguments to check
	 * @return <code>true</code> if they are not null and not an empty string,
	 * <code>false</code> otherwise
	 */
	public static boolean checkArguments(String arguments) {
		return arguments != null && arguments.length() > 0;
	}
	
	/**
	 * If the given path is not absolute, it is resolved relatively to the
	 * current directory of the environment and returned.
	 * If it is absolute, it is returned.
	 * @param env
	 * @param path
	 * @return resolved path
	 */
	public static Path resolvePath(Environment env, Path path) {
		if(!path.isAbsolute()) {
			path = env.getCurrentDirectory().resolve(path);
		}
		return path;
	}

}
