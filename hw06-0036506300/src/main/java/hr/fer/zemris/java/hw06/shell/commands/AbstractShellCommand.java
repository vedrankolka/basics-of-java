package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellCommand;
/**
 * An abstract representation of a ShellCommand for implementing only
 * methods that are equal in all commands.
 * @author Vedran Kolka
 *
 */
public abstract class AbstractShellCommand implements ShellCommand {
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

}
