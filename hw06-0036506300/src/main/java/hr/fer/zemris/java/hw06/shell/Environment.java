package hr.fer.zemris.java.hw06.shell;

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

}
