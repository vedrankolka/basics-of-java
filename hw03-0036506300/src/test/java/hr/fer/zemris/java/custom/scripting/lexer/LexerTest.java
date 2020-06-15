package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw03.prob1.LexerException;

public class LexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		
		// We expect the following stream of tokens
		Token correctData[] = {
				new Token(TokenType.TEXT, "   \r\n\t    "),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
	}

	@Test
	public void testText() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "  Štefanija\r\n\t Automobil   "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testInvalidEscapeEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\");  // this is three spaces and a single backslash -- 4 letters string

		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testInvalidEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");

		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testSingleEscapeSign() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \\{$ovo nije pocetak taga  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "  {$ovo nije pocetak taga  "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testMultipleEscapeSigns() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \\{$ovo nije pocetak taga. 2\\\\3=1.5  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "  {$ovo nije pocetak taga. 2\\3=1.5  "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testIncompleteForTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("   FO");
		lexer.setState(LexerState.TAG);
		checkToken(new Token(TokenType.VARIABLE_NAME, "FO"), lexer.nextToken());
	}
	
	
	@Test
	public void testIncompleteEndTag() {
		SmartScriptLexer lexer = new SmartScriptLexer(" en");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.VARIABLE_NAME, "en"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testNormalEchoTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("= =SkrSkr ");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.VARIABLE_NAME, "="),
				new Token(TokenType.VARIABLE_NAME, "="),
				new Token(TokenType.VARIABLE_NAME, "SkrSkr"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testThreeNumbers() {
		// Lets check for several numbers...
		SmartScriptLexer lexer = new SmartScriptLexer(" 3.14 9000 0   ");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.NUMBER, Double.valueOf(3.14)),
				new Token(TokenType.NUMBER, Double.valueOf(9000)),
				new Token(TokenType.NUMBER, Double.valueOf(0)),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);

	}
	
	@Test
	public void testNumberWithSign() {
		// Lets check for a number with a + or -
		SmartScriptLexer lexer = new SmartScriptLexer("-3.14 +50 +0.00");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.NUMBER, Double.valueOf(-3.14)),
				new Token(TokenType.NUMBER, Double.valueOf(50)),
				new Token(TokenType.NUMBER, Double.valueOf(0)),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);

	}
	
	@Test
	public void testIncorrectNumberFormat() {
		SmartScriptLexer lexer = new SmartScriptLexer("-31.47.4");
		lexer.setState(LexerState.TAG);
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testSingleoperator() {
		SmartScriptLexer lexer = new SmartScriptLexer("-");
		lexer.setState(LexerState.TAG);
		checkToken(new Token(TokenType.OPERATOR, "-"), lexer.nextToken());
	}
	
	@Test
	public void testMultipleOperators() {
		SmartScriptLexer lexer = new SmartScriptLexer("-*+ /   ^");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.OPERATOR, "-"),
				new Token(TokenType.OPERATOR, "*"),
				new Token(TokenType.OPERATOR, "+"),
				new Token(TokenType.OPERATOR, "/"),
				new Token(TokenType.OPERATOR, "^"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);

	}
	
	@Test
	public void testOperatorsAndNumber() {
		SmartScriptLexer lexer = new SmartScriptLexer(" +3*-6.0/2 - 90");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.NUMBER, Double.valueOf(3)),
				new Token(TokenType.OPERATOR, "*"),
				new Token(TokenType.NUMBER, Double.valueOf(-6)),
				new Token(TokenType.OPERATOR, "/"),
				new Token(TokenType.NUMBER, Double.valueOf(2)),
				new Token(TokenType.OPERATOR, "-"),
				new Token(TokenType.NUMBER, Double.valueOf(90)),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagBeginning() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ {${$");
		
		Token correctData[] = {
				new Token(TokenType.TAG_BEGINNING_SEQUENCE, "{$"),
				new Token(TokenType.TEXT, " "),
				new Token(TokenType.TAG_BEGINNING_SEQUENCE, "{$"),
				new Token(TokenType.TAG_BEGINNING_SEQUENCE, "{$"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testIncompleteTagBeginning() {
		SmartScriptLexer lexer = new SmartScriptLexer("   {");
		Token correctData[] = {
				new Token(TokenType.TEXT, "   {"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("$} $}$}");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.TAG_END_SEQUENCE, "$}"),
				new Token(TokenType.TAG_END_SEQUENCE, "$}"),
				new Token(TokenType.TAG_END_SEQUENCE, "$}"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testIncompleteTagEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("   $");
		lexer.setState(LexerState.TAG);
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testMultipleFunctions() {
		SmartScriptLexer lexer = new SmartScriptLexer("@f1@f2 @sin_2_cos @operation");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.FUNCTION, "@f1"),
				new Token(TokenType.FUNCTION, "@f2"),
				new Token(TokenType.FUNCTION, "@sin_2_cos"),
				new Token(TokenType.FUNCTION, "@operation"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testInvalidFunctionName1() {
		SmartScriptLexer lexer = new SmartScriptLexer("@2pac");
		lexer.setState(LexerState.TAG);
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testInvalidFunctionName2() {
		SmartScriptLexer lexer = new SmartScriptLexer("@_6ix9ine");
		lexer.setState(LexerState.TAG);
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testInvalidFunction() {
		SmartScriptLexer lexer = new SmartScriptLexer("   @");
		lexer.setState(LexerState.TAG);
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(SmartScriptLexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

	// ----------------------------------------------------------------------------------------------------------
	// --------------------- Second part of tests; uncomment when everything above works ------------------------
	// ----------------------------------------------------------------------------------------------------------


	@Test
	public void testNullState() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}
	
	@Test
	public void testNotNullInTAG() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testEmptyInTAG() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNextInTAG() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOFInTAG() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNoActualContentInTAG() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
	@Test
	public void testMultipartInput() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("Janko {$= 123.5 -6$}   Leonarda {$ @sin_2_cos $}");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "Janko "));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_BEGINNING_SEQUENCE, "{$"));
		
		lexer.setState(LexerState.TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, Double.valueOf(123.5)));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, Double.valueOf(-6)));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END_SEQUENCE, "$}"));
		
		lexer.setState(LexerState.BASIC);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "   Leonarda "));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_BEGINNING_SEQUENCE, "{$"));
		
		lexer.setState(LexerState.TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "@sin_2_cos"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END_SEQUENCE, "$}"));
		
		lexer.setState(LexerState.BASIC);
		
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
		
	}
	
	@Test
	public void testEmptyStringInTAG() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
	@Test
	public void testStringWithValidEscapeInTAG() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \"Leonora \\\"mala\\\" Princeza\"  ");
		lexer.setState(LexerState.TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Leonora \"mala\" Princeza"));
	}
	
	@Test
	public void testMultipleEscapesString() {
		SmartScriptLexer lexer = new SmartScriptLexer(" \" Leonora \\\"mala\\\" Princeza {$nije tag$} \\t\\n  \" ");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.STRING, " Leonora \"mala\" Princeza {$nije tag$} \t\n  "),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testNotEnclosedString() {
		SmartScriptLexer lexer = new SmartScriptLexer(" \"I am just a normal string");
		lexer.setState(LexerState.TAG);
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNormalVariableNames() {
		SmartScriptLexer lexer = new SmartScriptLexer("var1 var2_orMaybe3   notAVariable__hahaYesaVariable");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.VARIABLE_NAME, "var1"),
				new Token(TokenType.VARIABLE_NAME, "var2_orMaybe3"),
				new Token(TokenType.VARIABLE_NAME, "notAVariable__hahaYesaVariable"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testInvalidVariableName() {
		SmartScriptLexer lexer = new SmartScriptLexer(" _var1");
		lexer.setState(LexerState.TAG);
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNumberAndVariableWithNoSpace() {
		SmartScriptLexer lexer = new SmartScriptLexer(" 2pacVariable");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.NUMBER, Double.valueOf(2)),
				new Token(TokenType.VARIABLE_NAME, "pacVariable"),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testStringThenNumber() {
		SmartScriptLexer lexer = new SmartScriptLexer(" \"1\"10");
		lexer.setState(LexerState.TAG);
		
		Token correctData[] = {
				new Token(TokenType.STRING, "1"),
				new Token(TokenType.NUMBER, Double.valueOf(10)),
				new Token(TokenType.EOF, null)
			};

		checkTokenStream(lexer, correctData);
	}
	
	
	private void checkToken(Token actual, Token expected) {
			String msg = "Token are not equal.";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
	}
	
}
