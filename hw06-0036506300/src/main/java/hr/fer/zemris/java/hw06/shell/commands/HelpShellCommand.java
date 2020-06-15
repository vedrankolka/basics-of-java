package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *  If started with no arguments, it lists names of all supported commands.
 *  If started with single argument,
 *  it prints name and the description of selected command.
 * @author Vedran Kolka
 *
 */
public class HelpShellCommand extends AbstractShellCommand {
	
	public HelpShellCommand() {
		super("help",
			  "If started with no arguments, it lists names of all supported commands.",
			  "If started with single argument,"
						+ "it prints name and the description of selected command."
			  );
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments == null || arguments.length() == 0) {
			env.writeln("All supported commands:");
			for(String s : env.commands().keySet()) {
				env.writeln(s);
			}
			env.writeln("For more information about a command, write 'help command_name'");
		} else {
			String[] args = arguments.split("\\s+");
			if(args.length > 1) {
				env.writeln("Help command cannot be called with more than one command_name");
				return ShellStatus.CONTINUE;
			}
			ShellCommand command = env.commands().get(args[0]);
			if(command == null) {
				env.writeln("Command '" + args[0] + "' does not exist.");
			} else {
				env.writeln(command.getCommandName());
				command.getCommandDescription().forEach(env::writeln);
			}
			
		}
		return ShellStatus.CONTINUE;
	}

}
