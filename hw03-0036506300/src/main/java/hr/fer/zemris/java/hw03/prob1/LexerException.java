package hr.fer.zemris.java.hw03.prob1;

/**
 *TODO kak dokumentirat exception?
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
