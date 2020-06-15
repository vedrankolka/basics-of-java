package hr.fer.zemris.java.hw17.trazilica.parser;
/**
 * A simple lexer for text.
 * @author Vedran Kolka
 *
 */
public class TextLexer {
	/** Text from which the words are extracted */
	private char[] text;
	
	private int currentIndex;
	/** last extracted word */
	private String word;
	
	public TextLexer(String text) {
		this.text = text.toCharArray();
	}
	
	/**
	 * Returns the last extracted word.
	 * @return the last extracted word
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Returns the next word or <code>null</code> if the end of the text is reached.
	 * @return
	 */
	public String nextWord() {
		
		if (currentIndex >= text.length) {
			return null;
		}
		
		skipNonAlphabetic();
		word = extractWord();
		return word;
	}
	
	/**
	 * Extracts a word from the text.
	 * <p>
	 * A word is considered a series of alphabetic characters.
	 * @return
	 */
	private String extractWord() {
		StringBuilder sb = new StringBuilder();
		while(currentIndex < text.length && Character.isAlphabetic(text[currentIndex]))
			sb.append(text[currentIndex++]);
		
		String word = sb.toString();
		return word.isBlank() ? null : word;
	}

	/**
	 * Skips all non alphabetic characters.
	 */
	private void skipNonAlphabetic() {
		while (currentIndex < text.length && !Character.isAlphabetic(text[currentIndex]))
			currentIndex++;
	}
	
}
