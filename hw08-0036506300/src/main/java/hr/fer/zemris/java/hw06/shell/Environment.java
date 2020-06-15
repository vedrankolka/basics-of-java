package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * An interface that models an environment for ShellCommands
 * communicating with the user through the standard input/output. 
 * @author Vedran Kolka
 *
 */
public interface Environment {
	
	/**
	 * Reads a line from the standard input.
	 * @return read line
	 * @throws ShellIOException
	 */
	 String readLine() throws ShellIOException;
	 /**
	  * Writes the given <code>text</code> to the standard output.
	  * @param text
	  * @throws ShellIOException
	  */
	 void write(String text) throws ShellIOException;
	 /**
	  * Writes the given <code>text</code> to the standard output and ends
	  * the line with '\n'.
	  * @param text
	  * @throws ShellIOException
	  */
	 void writeln(String text) throws ShellIOException;
	 /**
	  * @return Immutable SortedMap of commands registered in the shell,
	  * mapped by the names of commands.
	  */
	 SortedMap<String, ShellCommand> commands();
	 /**
	  * @return {@link MULTILINE_SYMBOL}
	  */
	 Character getMultilineSymbol();
	 /**
	  * Sets the {@link MULTILINE_SYMBOL} to the given <code>symbol</code>.
	  * @param symbol
	  */
	 void setMultilineSymbol(Character symbol);
	 /**
	  * @return {@link PROMPT_SYMBOL}
	  */
	 Character getPromptSymbol();
	 /**
	  * Sets the {@link PROMPT_SYMBOL} to the given <code>value</code>.
	  * @param symbol
	  */
	 void setPromptSymbol(Character symbol);
	 /**
	  * @return {@link MORE_LINES_SYMBOL}
	  */
	 Character getMorelinesSymbol();
	 /**
	  * Sets the {@link MORE_LINES_SYMBOL} to the given <code>symbol</code>.
	  * @param symbol
	  */
	 void setMorelinesSymbol(Character symbol);
	 /**
	  * Returns an absolute path to the currentDirectory.
	  * @return absolute path to the current directory
	  */
	 Path getCurrentDirectory();
	 /**
	  * Sets the current directory to the given path.
	  * @param path to set
	  */
	 void setCurrentDirectory(Path path);
	 /**
	  * Returns the shared value associated with the given key.
	  * @param key
	  * @return value associated with the given key, or <code>null</code>
	  * if the key is not contained in the map
	  */
	 Object getSharedData(String key);
	 /**
	  * Adds the given value to the given key to the sharedData.
	  * @param key
	  * @param value
	  */
	 void setSharedData(String key, Object value);

}
