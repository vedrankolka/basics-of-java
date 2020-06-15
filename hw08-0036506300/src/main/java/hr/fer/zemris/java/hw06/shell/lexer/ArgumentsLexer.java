package hr.fer.zemris.java.hw06.shell.lexer;

import static hr.fer.zemris.java.hw06.shell.lexer.TokenType.*;

import java.util.Objects;

/**
 * A simple lexer for the arguments of a ShellCommand.
 * 
 * @author Vedran Kolka
 *
 */
public class ArgumentsLexer {
	/**
	 * Defined escape character for a string in quotation marks.
	 */
	public static final char ESCAPE_SIGN = '\\';
	/**
	 * Lexer's data for extracting tokens.
	 */
	private char[] data;
	/**
	 * Current index in the data.
	 */
	private int currentIndex;
	/**
	 * The last extracted String token.
	 */
	private ArgumentsLexerToken token;
	/**
	 * State of the lexer. Determines lexing rules.
	 */
	private ArgumentsLexerState state;

	/**
	 * 
	 * @param text
	 * @throws NullPointerException if text is <code>null</code>
	 */
	public ArgumentsLexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = ArgumentsLexerState.BASIC;
	}

	/**
	 * Sets the lexer state to the given state
	 * 
	 * @param state
	 * @throws NullPointerException if state is <code>null</code>
	 */
	public void setState(ArgumentsLexerState state) {
		this.state = Objects.requireNonNull(state);
	}

	/**
	 * Returns the last extracted token and <b>does not</b> extract a new token. an
	 * be called multiple times and no changes are made.
	 * 
	 * @return last extracted token
	 */
	public ArgumentsLexerToken getToken() {
		return token;
	}

	/**
	 * Extracts the next token from data and returns it.
	 * 
	 * @return a new extracted token
	 * @throws ArgumentsLexerException if called after the end of data was reached
	 */
	public ArgumentsLexerToken nextToken() {

		skipWhitespaces();

		if (token != null && token.getType() == EOF) {
			throw new ArgumentsLexerException("Cannot read after EOF");
		}

		if (currentIndex >= data.length) {
			token = new ArgumentsLexerToken(EOF, null);
			return token;
		}

		if (state == ArgumentsLexerState.EXPRESSION && data[currentIndex] == '$') {
			if (isSubstitutionStart()) {
				token = new ArgumentsLexerToken(SUBSTITUTION_START, "${");
				currentIndex += 2;
				return token;
			}

		}

		if (state == ArgumentsLexerState.BASIC || state == ArgumentsLexerState.EXPRESSION) {
			if (data[currentIndex] == '"') {
				token = extractString();
			} else {
				token = extractArgument();
			}
			return token;
		} else if (state == ArgumentsLexerState.SUBSTITUTION) {

			if (Character.isDigit(data[currentIndex])) {
				token = extractNumber();
			} else if (data[currentIndex] == ',') {
				token = new ArgumentsLexerToken(SYMBOL, ",");
				currentIndex++;
			} else if (data[currentIndex] == '}') {
				token = new ArgumentsLexerToken(SUBSTITUTION_END, "}");
				currentIndex++;
			}

			return token;
		}
		throw new IllegalStateException("Unknown lexer state.");
	}

	/**
	 * @return
	 */
	private ArgumentsLexerToken extractNumber() {
		StringBuilder sb = new StringBuilder();
		while (Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}
		return new ArgumentsLexerToken(NUMBER, sb.toString());
	}

	/**
	 * Extracts a string enclosed in quotation marks. Supports two escape sequences:
	 * \\ and \"
	 * 
	 * @return extracted string
	 */
	private ArgumentsLexerToken extractString() {

		StringBuilder sb = new StringBuilder();
		// skip the first quotation mark
		currentIndex++;

		while (currentIndex < data.length && data[currentIndex] != '"') {
			// if it is the escape sign
			if (data[currentIndex] == ESCAPE_SIGN) {
				// check if it is a legal escape sequence
				if (currentIndex + 1 < data.length
						&& (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '"')) {
					// skip the escape sign
					currentIndex++;
				}
			}
			sb.append(data[currentIndex++]);
		}
		// if the loop ended because the end of data was reached, the quotation mark was
		// missing
		if (currentIndex == data.length) {
			throw new ArgumentsLexerException("The quotation marks are never closed.");
			// if not, the loop was ended because of the quotation mark, so skip it
		} else {
			currentIndex++;
		}
		return new ArgumentsLexerToken(STRING, sb.toString());
	}

	/**
	 * Extracts a token that is separated by a whitespace from the rest of the data.
	 * 
	 * @return extracted string
	 */
	private ArgumentsLexerToken extractArgument() {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length && !Character.isWhitespace(data[currentIndex])) {
			// if the lexer is in expression state, it must recognize the start of a
			// substitution
			if (state == ArgumentsLexerState.EXPRESSION && isSubstitutionStart())
				break;
			sb.append(data[currentIndex++]);
		}
		return new ArgumentsLexerToken(STRING, sb.toString());
	}

	/**
	 * Increases the currentIndex to skip all whitespaces in a row.
	 */
	private void skipWhitespaces() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex]))
			currentIndex++;
	}

	/**
	 * Checks if the current character and the next character form a sequence to
	 * start a substitute expression.
	 * 
	 * @return true if they do, false otherwise
	 */
	private boolean isSubstitutionStart() {
		return data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '{';
	}

}
