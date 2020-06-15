package hr.fer.zemris.java.hw05.db.simplelexer;
/**
 * A simple token for our simple lexer which is used for parsing a query.
 * @author Vedran Kolka
 *
 */
public class Token {

	private TokenType type;
	private String value;

	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
}
