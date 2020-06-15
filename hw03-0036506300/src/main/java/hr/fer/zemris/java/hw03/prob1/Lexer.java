package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Class for extracting tokens from text.
 * Supports TokenTypes WORD, NUMBER, SYMBOL.
 * @author Vedran Kolka
 *
 */
public class Lexer {
	/**
	 * Source text converted to an array of characters.
	 */
	private char[] data;

	/**
	 * Current Token.
	 */
	private Token token;
	/**
	 * Index of the next character to be processed.
	 */
	private int currentIndex;
	
	private LexerState state;
	
	/**
	 * Constructor which converts the given <code>text</code> to a character array
	 * and generates the first token.
	 * @param text
	 * @throws LexerException if a token cannot be generated
	 */
	public Lexer(String text) {
		if(text==null) {
			throw new NullPointerException("text cannot be null.");
		}
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
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
		
		if(state==LexerState.BASIC) {
			//if character is a letter, return a WORD token
			if(Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\') {
				token = extractWord();
				return token;
			}
			//if character is a digit, return a NUMBER token
			if(Character.isDigit(data[currentIndex])) {
				token = extractNumber();
				return token;
			}
			//extract the symbol and return a SYMBOL token
			return new Token(TokenType.SYMBOL, data[currentIndex++]);
			
		} else if(state==LexerState.EXTENDED) {
			//if the character is #, return a SYBOL token
			if(data[currentIndex]=='#') {
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			//else extract it by the extended state rules
			} else {
				token = extractToken();
			}
			return token;
		}
		
		throw new RuntimeException("Unexpected lexer state.");
		
	}
	
	/**
	 * Returns the last generated token. Does not generate a new token.
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
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
	 * Extract a word from the text and returns it as a Token
	 * @return token(WORD, value)
	 * @throws LexerException
	 */
	private Token extractWord() {
		//create a char array for the word
		char[] newWord = new char[data.length-currentIndex];
		int count = 0;
		
		while(currentIndex<data.length &&
				(Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\')) {
			
			if(data[currentIndex]=='\\') {
				//check if escape is legal, then add the next character to newWord
				checkEscape();
				newWord[count++] = data[++currentIndex];
			} else {
				//if it is a letter, add it to newWord
				newWord[count++] = data[currentIndex];
			}
			//move to the next character
			currentIndex++;
		}
		String word = new String(newWord, 0, count);
		return new Token(TokenType.WORD, word);
	}
	
	
	/**
	 * Checks if the escape sign is used correctly.
	 * @throws LexerException if it is not used correctly
	 */
	private void checkEscape() {
		//if the escape sign is at the last position, throw exception
		if(currentIndex==data.length-1) {
			throw new LexerException("Escape sign cannot be at the last position.");
		}
		//if there is a letter after the escape sign, throw exception
		if(Character.isLetter(data[currentIndex+1])) {
			throw new LexerException("An escape sign cannot be followed by a letter");
		}
	}
	
	/**
	 * Extract a number from the text and returns it as a Token.
	 * Only long numbers are supported.
	 * @return token(NUMBER, Long)
	 * @throws LexerException if number cannot be parsed to long
	 */
	private Token extractNumber() {
		int start = currentIndex;
		while(currentIndex<data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}
		String number = new String(data, start, currentIndex-start);
		try {
			Long longNumber = Long.parseLong(number);
			return new Token(TokenType.NUMBER, longNumber);
		} catch(NumberFormatException e) {
			throw new LexerException("Number is invalid.");
		}
	}
	
	/**
	 * Sets the Lexer to state <code>state</code>.
	 * @param state
	 * @throws NullPointerException if <code>state</code> is <code>null</code>
	 */
	public void setState(LexerState state) {
		this.state = Objects.requireNonNull(state);
	}
	
	/**
	 * Extracts the token with the rules of extended lexer state.
	 * @return token(WORD, value)
	 */
	private Token extractToken() {
		int count = 0;
		char[] newToken = new char[data.length-currentIndex];
		while(currentIndex<data.length && notBlank() &&
				data[currentIndex]!='#') {
			newToken[count++] = data[currentIndex++];
		}
		String token = new String(newToken, 0, count);
		return new Token(TokenType.WORD, token);
		
	}
	
	/**
	 * @return <code>true</code> if character at <code>currentIndex</code>
	 * is not a blank, <code>false</code> otherwise
	 */
	private boolean notBlank() {
		return data[currentIndex]!=' ' && data[currentIndex]!='\t'
				&& data[currentIndex]!='\r' && data[currentIndex]!='\n';
	}
	
}

