package hr.fer.zemris.java.hw17.trazilica.parser;

import java.util.ArrayList;
import java.util.List;
/**
 * Simple parser for the text.
 * @author Vedran Kolka
 *
 */
public class TextParser {
	/** Simple lexer that the parser uses */
	private TextLexer lexer;
	/** List of parsed words */
	private List<String> words = new ArrayList<>();
	
	/**
	 * Constructor
	 * @param text to parse
	 */
	public TextParser(String text) {
		lexer = new TextLexer(text);
		parse();
	}
	
	/**
	 * Parses the text given through the constructor
	 */
	private void parse() {
		for(String word = lexer.nextWord(); word != null; word = lexer.nextWord()) {
			words.add(word);
		}
	}
	
	/**
	 * Returns a list of parsed words
	 * @return a list of parsed words
	 */
	public List<String> getWords() {
		return words;
	}
	
}
