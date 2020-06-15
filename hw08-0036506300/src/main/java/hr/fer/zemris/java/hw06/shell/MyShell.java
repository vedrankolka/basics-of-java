package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * A shell program which offers simple shell operations such as creating directories,
 * copying files, listing directory content and similar operations. 
 * @author Vedran Kolka
 *
 */
public class MyShell { //massrename src/test/resources/read src/test/resources/write filter test.*\..*

	private static final String WELCOME_MESSAGE = "Welcome to MyShell v 1.0";

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			// set up the environment for the shell
			Environment env = new MyShellEnvironment(sc, createCommands());
			System.out.println(WELCOME_MESSAGE);
			ShellStatus status = ShellStatus.CONTINUE;
			while(status != ShellStatus.TERMINATE) {
				env.write(env.getPromptSymbol() + " ");
				String input = env.readLine();
				String[] argComponents = input.trim().split("\\s+", 2);
				String commandName = argComponents[0];
				String arguments = argComponents.length > 1 ? argComponents[1] : "" ;
				ShellCommand command = env.commands().get(commandName);
				if(command == null) {
					env.writeln("Unknown command: " + commandName);
					continue;
				}
				status = command.executeCommand(env, arguments);
			}

		} catch (ShellIOException e) {
			System.err.println("The shell cannot comunicate with the environment, terminating the shell.");
		}

	}

	/**
	 * Creates a SortedMap and fills it with all the supported commands.
	 * 
	 * @return the created map
	 */
	private static SortedMap<String, ShellCommand> createCommands() {

		SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();

		commands.put("help", new HelpShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("massrename", new MassrenameShellCommand());

		return commands;
	}

}
