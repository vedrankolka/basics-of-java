package hr.fer.zemris.java.hw06.shell.lexer;

public class ArgumentsLexerToken {

	private TokenType type;
	
	private String value;

	public ArgumentsLexerToken(TokenType type, String value) {
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
