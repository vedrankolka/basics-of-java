package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hr.fer.zemris.java.hw17.trazilica.baza.Database;
import hr.fer.zemris.java.hw17.trazilica.naredbe.Command;
import hr.fer.zemris.java.hw17.trazilica.naredbe.ExitCommand;
import hr.fer.zemris.java.hw17.trazilica.naredbe.QueryCommand;
import hr.fer.zemris.java.hw17.trazilica.naredbe.ResultsCommand;
import hr.fer.zemris.java.hw17.trazilica.naredbe.TypeCommand;

/**
 * A shell-like application that offers simple search algorithms on a set of
 * data in the directory whose path is provided through the arguments.
 * <p>
 * The "shell" offers 4 commands:
 * <ul>
 * <li>query - {@link QueryCommand}
 * <li>type - {@link TypeCommand}
 * <li>results - {@link ResultsCommand}
 * <li>exit - {@link ExitCommand}
 * 
 * @author Vedran Kolka
 *
 */
public class Konzola {
	/**
	 * A map of commands supported by this shell, mapped by their names of
	 * invocation
	 */
	private static Map<String, Command> commands;
	/** The message to print when ready to take a command */
	private static final String PROMPT_MESSAGE = "Enter command > ";

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Program očekuje točno jedan argument: stazu do direktorija s člancima.");
			return;
		}

		Path clanciDir = Paths.get(args[0]);
		if (!Files.isDirectory(clanciDir)) {
			System.err.println("Staza \"" + clanciDir + "\" nije staza do direktorija.");
			return;
		}

		try {
			Database.getDatabase().load(clanciDir);
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
			return;
		}
		List<String> vocabulary = Database.getDatabase().getVocabulary();
		System.out.println("Veličina rječnika je " + vocabulary.size() + " riječi.");
		loadCommands();

		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.print(PROMPT_MESSAGE);
				String[] entered = sc.nextLine().split("\\s+", 2);
				Command command = commands.get(entered[0]);
				if (command == null) {
					System.out.println("Nepoznata naredba.");
					continue;
				}
				String arguments = entered.length > 1 ? entered[1] : null;
				try {
					if (!command.execute(arguments)) {
						break;
					}
				} catch (Exception e) {
					System.err.println(e.getClass() + ": " + e.getMessage());
				}

			}
		}

		System.out.println("Doviđenja!");
	}

	/**
	 * Creates a map of commands and fills it with all supported commands.
	 */
	private static void loadCommands() {
		commands = new HashMap<>();

		commands.put("query", new QueryCommand());
		commands.put("type", new TypeCommand());
		commands.put("results", new ResultsCommand());
		commands.put("exit", new ExitCommand());
	}

}
