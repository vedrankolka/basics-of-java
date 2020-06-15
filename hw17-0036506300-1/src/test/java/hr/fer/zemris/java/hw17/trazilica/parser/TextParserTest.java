package hr.fer.zemris.java.hw17.trazilica.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TextParserTest {

	@Test
	public void nullTest() {
		assertThrows(NullPointerException.class, () -> new TextParser(null));
	}
	
	@Test
	public void emptyStringTest() {
		List<String> words = new TextParser("").getWords();
		assertTrue(words.isEmpty());
	}
	
	@Test
	public void oneWordTest1() {
		List<String> words = new TextParser("word").getWords();
		assertEquals(1, words.size());
		assertEquals("word", words.get(0));
	}
	
	@Test
	public void oneWordTest2() {
		List<String> words = new TextParser(" word-").getWords();
		assertEquals(1, words.size());
		assertEquals("word", words.get(0));
	}
	
	@Test
	public void oneWordTest3() {
		List<String> words = new TextParser("%5word123- ").getWords();
		assertEquals(1, words.size());
		assertEquals("word", words.get(0));
	}
	
	@Test
	public void twoWordsTest1() {
		List<String> words = new TextParser("amo-tamo\r\n").getWords();
		assertEquals(2, words.size());
		assertEquals("amo", words.get(0));
		assertEquals("tamo", words.get(1));
	}
	
	@Test
	public void wordsTest() {
		List<String> words = new TextParser("amo-tamo\r\nskrSkr belgijanči").getWords();
		assertEquals(4, words.size());
		assertEquals("amo", words.get(0));
		assertEquals("tamo", words.get(1));
		assertEquals("skrSkr", words.get(2));
		assertEquals("belgijanči", words.get(3));
	}
	
}
