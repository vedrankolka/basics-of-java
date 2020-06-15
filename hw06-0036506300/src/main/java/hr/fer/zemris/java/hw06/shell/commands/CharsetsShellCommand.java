package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * Command takes no arguments and lists names of supported charsets for your Java platform.
 * @author Vedran Kolka
 *
 */
public class CharsetsShellCommand extends AbstractShellCommand {
	
	public CharsetsShellCommand() {
		super("charsets",
			  "Command takes no arguments and lists names of supported charsets for your Java platform.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//only write the message, but execute ignoring the arguments
		if(ArgumentsParser.checkArguments(arguments)) {
			env.writeln("Command charsets takes no arguments.");
		}
		env.writeln("Available charsets:");
		Charset.availableCharsets().forEach((name, charset) -> env.writeln(name));
		return ShellStatus.CONTINUE;
		
	}

}
