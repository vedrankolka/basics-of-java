package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.FilterResult;
import hr.fer.zemris.java.hw06.shell.NameBuilder;
import hr.fer.zemris.java.hw06.shell.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.subcommands.ShellSubcommand;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexer;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerException;
/**
 * Renames all the files from the source folder and moves them to the destination folder.
   The files are selected with a regular expression and the name is generated with another expression.
 * @author Vedran Kolka
 *
 */
public class MassrenameShellCommand extends AbstractShellCommand {

	private Map<String, ShellSubcommand> subcommands;

	public MassrenameShellCommand() {
		super("massrename", "Renames all the files from the source folder and moves them to the destination folder.",
				"The files are selected with a regular expression and the name is generated with another expression.");
		subcommands = new HashMap<>();
		subcommands.put("filter", (env, src, dest, regex, other) -> filter(env, src, regex));
		subcommands.put("groups", (env, src, dest, regex, other) -> groups(env, src, regex));
		subcommands.put("show", (env, src, dest, regex, other) -> show(env, src, regex, other));
		subcommands.put("execute", (env, src, dest, regex, other) -> execute(env, src, dest, regex, other));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!checkArguments(arguments)) {
			env.writeln("The command must take four or more arguments.");
			return ShellStatus.CONTINUE;
		}

		try {
			ArgumentsLexer lexer = new ArgumentsLexer(arguments);
			Path source = Paths.get(lexer.nextToken().getValue());
			Path destination = Paths.get(lexer.nextToken().getValue());
			String cmd = lexer.nextToken().getValue();
			String regex = lexer.nextToken().getValue();
			String other = lexer.nextToken().getValue(); // can be null

			ShellSubcommand command = subcommands.get(cmd);
			if (command == null) {
				env.writeln("CMD '" + cmd + "' is not a valid command for massrename");
				return ShellStatus.CONTINUE;
			}
			command.executeCommand(env, source, destination, regex, other);

		} catch (IllegalArgumentException | ArgumentsLexerException e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	private void filter(Environment env, Path source, String regex) {
		try {
			List<FilterResult> results = FilterResult.filter(source, regex);
			results.forEach(r -> env.writeln(r.toString()));

		} catch (IOException e) {
			env.writeln(e.getMessage());
		}
	}

	private void groups(Environment env, Path src, String regex) {
		try {
			List<FilterResult> results = FilterResult.filter(src, regex);
			for (FilterResult r : results) {
				env.write(r + " ");
				for (int i = 0; i <= r.numberOfGroups(); ++i) {
					env.write(i + ": " + r.group(i) + " ");
				}
				env.writeln("");
			}

		} catch (IOException e) {
			env.writeln(e.getMessage());
		}
	}

	private void show(Environment env, Path src, String regex, String[] other) {
		BiConsumer<SourceAndDestination, OldNameNewName> formatter = (sad, names) -> {
			env.writeln(names.getOldName() + " => " + names.getNewName());
		};
		renameAndDo(env, src, null, regex, other, formatter);
	}

	private void execute(Environment env, Path src, Path dest, String regex, String[] other) {
		BiConsumer<SourceAndDestination, OldNameNewName> copyConsumer = (sad, names) -> {
			Path source = sad.getSource().resolve(names.getOldName());
			Path destination = sad.getDestination().resolve(names.getNewName());
			try {
				Files.move(source, destination);
				env.writeln(source + " => " + destination);

			} catch (IOException e) {
				env.writeln("Moving of " + source + " to " + destination + " failed.");
			}
		};
		renameAndDo(env, src, dest, regex, other, copyConsumer);
	}

	private void renameAndDo(Environment env, Path src, Path dest, String regex, String[] other,
			BiConsumer<SourceAndDestination, OldNameNewName> consumer) {
		if (other == null || other.length == 0) {
			env.writeln("The command must take an EXPRESSION as a fifth argument.");
			return;
		}
		try {
			List<FilterResult> results = FilterResult.filter(src, regex);
			NameBuilderParser parser = new NameBuilderParser(other[0]);
			NameBuilder builder = parser.getNameBuilder();
			for (FilterResult file : results) {
				StringBuilder sb = new StringBuilder();
				builder.execute(file, sb);
				String newName = sb.toString();
				SourceAndDestination sad = new SourceAndDestination(src, dest);
				OldNameNewName names = new OldNameNewName(file.toString(), newName);
				consumer.accept(sad, names);
			}

		} catch (IOException e) {
			env.writeln("Could not read from " + src.toString());
		}
	}

	/**
	 * A private class that is used as a structure for holding a path of a source
	 * and a destination for e.g. copying files.
	 * 
	 * @author Vedran Kolka
	 *
	 */
	private static class SourceAndDestination {
		private Path source;
		private Path destination;

		public SourceAndDestination(Path source, Path destination) {
			this.source = source;
			this.destination = destination;
		}

		public Path getDestination() {
			return destination;
		}

		public Path getSource() {
			return source;
		}

	}

	/**
	 * A class used to hold two strings for the renameAndDo command
	 * @author Vedran Kolka
	 *
	 */
	private static class OldNameNewName {
		private String oldName;
		private String newName;

		public OldNameNewName(String oldName, String newName) {
			this.oldName = oldName;
			this.newName = newName;
		}

		public String getNewName() {
			return newName;
		}

		public String getOldName() {
			return oldName;
		}

	}

}
