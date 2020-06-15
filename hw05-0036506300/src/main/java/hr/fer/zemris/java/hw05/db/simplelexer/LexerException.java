package hr.fer.zemris.java.hw05.db.simplelexer;

/**
 * An exception thrown be the QueryLexer
 * @author Vedran Kolka
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public LexerException() {
		
	}

	public LexerException(String message) {
		super(message);
	}
}
