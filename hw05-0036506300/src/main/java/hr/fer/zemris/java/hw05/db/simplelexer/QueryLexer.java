package hr.fer.zemris.java.hw05.db.simplelexer;

/**
 * A simple lexer for a query command.
 * @author Vedran Kolka
 *
 */
public class QueryLexer {
	
	private static final char[] BASIC_OPERATORS = { '=', '<', '>', '!' };

	private char[] data;
	
	private int currentIndex;
	
	private Token token;
	
	public QueryLexer(String text) {
		data = text.toCharArray();
	}
	
	/**
	 * Returns the last generated token. Does not generate a new token.
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Generates the next token and returns it.
	 * @return next token
	 * @throws LexerException if a token cannot be generated
	 */
	public Token nextToken() {
		//if last token was EOF token, throw LexerException
		if(token != null && token.getType()==TokenType.EOF) {
			throw new LexerException("Cannot read token after EOF");
		}
		skipWhitespaces();
		//if the end of the String is reached, return EOF token
		if(currentIndex>=data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		//check if it is the LIKE operator, so it is not read as a variable
		if(isLIKE()) {
			token = new Token(TokenType.OPERATOR, "LIKE");
			currentIndex += 4;
			return token;
		}
		//check if it is one of the other operators
		if(isOperator()) {
			String value = extractOperator();
			token = new Token(TokenType.OPERATOR, value);
			return token;
		}
		//check if it is a variable, this includes "and" for the lexer
		if(Character.isLetter(data[currentIndex])) {
			String value = extractKeyword();
			token = new Token(TokenType.KEYWORD, value);
			return token;
		}
		//check if it is a string literal
		if(data[currentIndex] == '"') {
			String value = extractString();
			token = new Token(TokenType.STRING, value);
			return token;
		}
		//if it is none of the above, the lexer does not know how to read it
		throw new LexerException("Invalid syntax in the given text.");
		
	}
	
	/**
	 * Skips all blanks in the text.
	 */
	private void skipWhitespaces() {
		while(currentIndex<data.length) {
			char character = data[currentIndex];
			if(character==' ' || character=='\t' || character=='\r' || character=='\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * Extracts a keyword from the <code>data</code> and returns it as a string.
	 * A keyword can only contain letters.
	 * @return string representing the keyword
	 */
	private String extractKeyword() {
		StringBuilder sb = new StringBuilder();
		while(currentIndex<data.length && Character.isLetter(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}
		return sb.toString();
	}
	
	/**
	 * Checks if the following sequence of characters is "like", case insensitive.
	 * @return
	 */
	private boolean isLIKE() {
		if(currentIndex+3>=data.length) {
			return false;
		}
		
		String s = new String(data, currentIndex, 4);
		return s.toUpperCase().equals("LIKE");
		
	}
	
	/**
	 * Checks if the current character is a basic operator.
	 * @return <code>true</code> if it is, <code>false</code> otherwise
	 */
	private boolean isOperator() {
		for(char c : BASIC_OPERATORS) {
			if(c == data[currentIndex]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Extracts an operator from <code>data</code> and returns it as a string.
	 * @return operator
	 * @throws LexerException if it was called upon a character which is not an operator.
	 */
	private String extractOperator() {
		StringBuilder sb = new StringBuilder();
		//if it is not an operator, return null
		if(!isOperator()) {
			return null;
		}
		//if it is append it and see if it is an operator of two characters (e.g. <= )
		sb.append(data[currentIndex++]);
		if(isOperator()) {
			sb.append(data[currentIndex++]);
		}
		return sb.toString();
	}
	
	/**
	 * Extracts a string enclosed in quotation marks. No escape signs are supported.
	 * @return string
	 * @throws LexerException if the quotation marks are not opened or not closed
	 */
	private String extractString() {
		if(data[currentIndex] != '"') {
			throw new LexerException("A string was expected, but there are no quotation marks.");
		}
		StringBuilder sb = new StringBuilder();
		currentIndex++;
		while(currentIndex < data.length && data[currentIndex] != '"') {
			sb.append(data[currentIndex++]);
		}
		/*
		 * if the while loop ended because the end of the text was reached
		 * then the quotation marks were not closed
		 */
		if(currentIndex == data.length) {
			throw new LexerException("The quotation marks are never closed.");
		}
		//to skip the closing quotation mark
		currentIndex++;
		return sb.toString();
	}
	
}
