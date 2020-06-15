package hr.fer.zemris.java.custom.scripting.parser;


import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	@Test
	public void emptyStringTest() {
		String text = "";
		SmartScriptParser parser = new SmartScriptParser(text);
		assertNotNull(parser.getDocumentNode());
	}
	
	@Test
	public void onlyTextTest() {
		String text = "Just some text.";
		SmartScriptParser parser = new SmartScriptParser(text);
		assertNotNull(parser.getDocumentNode().getChild(0));
		assertEquals("Just some text.", ((TextNode)parser.getDocumentNode().getChild(0)).getText());
	}
	
	@Test
	public void textAndForTest() {
		String text = "Just some text.{$for i in range 5$}Some real text.{$end$}";
		SmartScriptParser parser = new SmartScriptParser(text);
		assertEquals("Just some text.", ((TextNode)parser.getDocumentNode().getChild(0)).getText());
		assertEquals("Some real text.", ((TextNode)parser.getDocumentNode().getChild(1).getChild(0) ).getText());
	}
	
	@Test
	public void differentTextNodesTest() {
		String text1 = "Just some text 1";
		SmartScriptParser parser1 = new SmartScriptParser(text1);
		String document1 = SmartScriptParser.createOriginalDocumentBody(parser1.getDocumentNode());
		String text2 = "Just some text 2";
		SmartScriptParser parser2 = new SmartScriptParser(text2);
		String document2 = SmartScriptParser.createOriginalDocumentBody(parser2.getDocumentNode());
		assertNotEquals(document1, document2);
		assertNotEquals(parser1.getDocumentNode(), parser2.getDocumentNode());
	}
	
	@Test
	public void compareAFewTagsAndSomeTextTest() {
		String text = "Just some text.{$for i in range 5$}Some real text.{$end$}";
		compareDocuments(text);
	}
	
	@Test
	public void echoTagWithStringWithEscapeSignsTest() {
		String text = "{$= \"not the end \\\" of the strnig yet!\" variable1\r\n" + 
				"variable2 \"some more escape \\\\ \\tsequences that should work\"\r\n\r\n$}";
		compareDocuments(text);
	}
	
	@Test
	public void reconstructingTextWithEscapeSignsTest() {
		String text = "This is not a tag beginning \\{$ not a tag \\\\ $}";
		compareDocuments(text);
	}
	
	@Test
	public void document1Test() {
		String text = loader("src\\test\\resources\\document1.txt");
		SmartScriptParser parser1 = new SmartScriptParser(text);
		String document1 = SmartScriptParser.createOriginalDocumentBody(parser1.getDocumentNode());
		SmartScriptParser parser2 = new SmartScriptParser(document1);
		String document2 = SmartScriptParser.createOriginalDocumentBody(parser2.getDocumentNode());
		assertEquals(document1, document2);
		assertEquals(parser1.getDocumentNode(), parser2.getDocumentNode());
	}
	
	@Test
	public void document2Test() {
		String documentText = loader("src\\test\\resources\\document2.txt");
		compareDocuments(documentText);
	}
	
	@Test
	public void document3Test() {
		String documentText = loader("src\\test\\resources\\document3.txt");
		compareDocuments(documentText);
	}
	
	@Test
	public void document4Test() {
		String documentText = loader("src\\test\\resources\\document4.txt");
		compareDocuments(documentText);
	}
	
	/**
	 * A method that compares the whole document structure and the reconstructed strings as well.
	 * @param text
	 */
	private void compareDocuments(String text) {
		SmartScriptParser parser1 = new SmartScriptParser(text);
		String document1 = SmartScriptParser.createOriginalDocumentBody(parser1.getDocumentNode());
		SmartScriptParser parser2 = new SmartScriptParser(document1);
		String document2 = SmartScriptParser.createOriginalDocumentBody(parser2.getDocumentNode());
		assertEquals(document1, document2);
		assertEquals(parser1.getDocumentNode(), parser2.getDocumentNode());
	}
	
	/**
	 * A loader to load text from text files.
	 * @param filename
	 * @return a String of the whole content in the text file
	 */
	private String loader(String filename) {
		try {
			String docBody = new String(
					 Files.readAllBytes(Paths.get(filename)),
					 StandardCharsets.UTF_8
					);
			return docBody;
		} catch (IOException e) {
			throw new RuntimeException("Reading failed.");
		}
	}

}
