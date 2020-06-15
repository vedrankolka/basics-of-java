package hr.fer.zemris.java.hw05.db.simplelexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class QueryLexerTest {

	@Test
	public void testNotNull() {
		QueryLexer lexer = new QueryLexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new QueryLexer(null));
	}

	@Test
	public void testEmpty() {
		QueryLexer lexer = new QueryLexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		QueryLexer lexer = new QueryLexer("");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		QueryLexer lexer = new QueryLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		QueryLexer lexer = new QueryLexer("   \r\n\t    ");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
	@Test
	public void wholeQueryTest() {
		QueryLexer ql = new QueryLexer("lastName>= \"Mala Princeza\"and JMBAG liKe \"*1\" and firstName =\"Leonora\"");
		Token correctData[] = {
				new Token(TokenType.KEYWORD, "lastName"),
				new Token(TokenType.OPERATOR, ">="),
				new Token(TokenType.STRING, "Mala Princeza"),
				new Token(TokenType.KEYWORD, "and"),
				new Token(TokenType.KEYWORD, "JMBAG"),
				new Token(TokenType.OPERATOR, "LIKE"),
				new Token(TokenType.STRING, "*1"),
				new Token(TokenType.KEYWORD, "and"),
				new Token(TokenType.KEYWORD, "firstName"),
				new Token(TokenType.OPERATOR, "="),
				new Token(TokenType.STRING, "Leonora"),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(ql, correctData);
	}
	
	private void checkTokenStream(QueryLexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}
	
//	private void checkToken(Token actual, Token expected) {
//		String msg = "Token are not equal.";
//		assertEquals(expected.getType(), actual.getType(), msg);
//		assertEquals(expected.getValue(), actual.getValue(), msg);
//	}
}
