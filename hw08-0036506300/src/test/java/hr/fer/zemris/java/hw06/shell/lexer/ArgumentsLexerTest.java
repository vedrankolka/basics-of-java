package hr.fer.zemris.java.hw06.shell.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArgumentsLexerTest {

	@Test
	public void emptyStringTest() {
		ArgumentsLexer l = new ArgumentsLexer("");
		assertNull(l.nextToken());
	}
	
	@Test
	public void simpletextTest() {
		ArgumentsLexer l = new ArgumentsLexer("ovo je normalan 		tekst.");
		assertEquals("ovo", l.nextToken());
		assertEquals("je", l.nextToken());
		assertEquals("normalan", l.nextToken());
		assertEquals("tekst.", l.nextToken());
		assertNull(l.nextToken());
		assertThrows(ArgumentsLexerException.class, () -> l.nextToken());
	}
	
	@Test
	public void simpleStringTest() {
		ArgumentsLexer l = new ArgumentsLexer(" \"ovo je \"\"normalan\" 		tekst.");
		assertEquals("ovo je ", l.nextToken());
		assertEquals("normalan", l.nextToken());
		assertEquals("tekst.", l.nextToken());
		assertNull(l.nextToken());
		assertThrows(ArgumentsLexerException.class, () -> l.nextToken());
	}
	
	@Test
	public void escapeSequenceTest() {
		ArgumentsLexer l = new ArgumentsLexer("\"Leonora \\\"mala Princeza\\\"\" i \"\\\\druzina\\\\\"");
		assertEquals("Leonora \"mala Princeza\"", l.nextToken());
		assertEquals("i", l.nextToken());
		assertEquals("\\druzina\\", l.nextToken());
		assertNull(l.nextToken());
		assertThrows(ArgumentsLexerException.class, () -> l.nextToken());
	}
	
}
