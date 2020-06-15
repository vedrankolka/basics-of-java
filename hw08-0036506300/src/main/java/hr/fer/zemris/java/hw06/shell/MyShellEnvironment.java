package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;

public class MyShellEnvironment implements Environment {
	/**
	 * The environments actual input.
	 */
	private Scanner sc;
	/**
	 * The prompt character that is printed when the shell is ready for a new command
	 */
	private Character PROMPT = '>';
	/**
	 * The character indicating the arguments are continued in the next line.
	 */
	private Character MORELINES = '\\';
	/**
	 * The character printed to indicate the line is not a new command,
	 * but the one from the previous line stretches through more lines.
	 */
	private Character MULTILINE = '|';
	/**
	 * An immutable SortedMap of commands supported by this shell.
	 */
	private SortedMap<String, ShellCommand> commands;
	/**
	 * Absolute path to the current directory.
	 */
	private Path currentDirectory;
	
	private Map<String, Object> sharedData;
	
	/**
	 * Constructs a MyShell environment with the input from the Scanner <code>sc</code>
	 * and with the commands given in the sorted map <code>commands</code>.
	 * @param sc
	 * @param commands
	 */
	public MyShellEnvironment(Scanner sc, SortedMap<String, ShellCommand> commands) {
		this.sc = sc;
		this.commands = Collections.unmodifiableSortedMap(commands);
		this.currentDirectory = Paths.get(".").toAbsolutePath().normalize();
		this.sharedData = new HashMap<>();
	}

	@Override
	public String readLine() throws ShellIOException {
		if(sc.hasNext()) {
			return readInput();
		}
		throw new ShellIOException("Reading failed.");
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINE;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINE = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPT;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPT = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINES;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINES = symbol;
	}
	
	/**
	 * The method reads from the standard input as long as the lines are ended
	 * with a MORELINES symbol, concatenates them into one string,
	 * removing the MORELINES symbol and returns it.
	 * @return string representing the whole input
	 */
	private String readInput() {

		StringBuilder sb = new StringBuilder();
		String line = sc.nextLine();
		line = line.trim();
		if (line == null || line.length() == 0) {
			return sb.toString();
		}
		char lastCharacter = line.charAt(line.length() - 1);
		while (lastCharacter == getMorelinesSymbol()) {
			System.out.print(MULTILINE + " ");
			//remove the MORELINES symbol and append
			line = line.substring(0, line.length()-1);
			sb.append(line).append(' ');
			line = sc.nextLine();
			if (line == null || line.length() == 0) {
				return sb.toString();
			}
			lastCharacter = line.charAt(line.length() - 1);
		}
		// append what is left without a MORELINES symbol
		sb.append(line);
		return sb.toString();
	}

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		Path p = path.toAbsolutePath().normalize();
		if(!Files.isDirectory(p)) {
			throw new IllegalArgumentException("The given path is not a directory: " + path);
		}
		currentDirectory = p;
	}

	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
	}

}
