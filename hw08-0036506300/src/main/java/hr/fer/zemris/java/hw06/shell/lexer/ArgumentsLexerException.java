package hr.fer.zemris.java.hw06.shell.lexer;
/**
 * An exception thrown by the ArgumentsLexer.
 * @author Vedran Kolka
 *
 */
public class ArgumentsLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ArgumentsLexerException() {
	
	}
	
	public ArgumentsLexerException(String message) {
		super(message);
	}
	
}
