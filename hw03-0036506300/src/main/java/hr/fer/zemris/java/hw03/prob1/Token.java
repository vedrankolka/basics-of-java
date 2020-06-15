package hr.fer.zemris.java.hw03.prob1;

public class Token {
	/**
	 * Type of token.
	 */
	private TokenType tokenType;
	/**
	 * Value of token.
	 */
	private Object value;
	
	public Token(TokenType tokenType, Object value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	/**
	 * Returns type of token.
	 * @return tokenType
	 */
	public TokenType getType() {
		return tokenType;
	}

	/**
	 * Returns value of token
	 * @return value
	 */
	public Object getValue() {
		return value;
	}
	
}
