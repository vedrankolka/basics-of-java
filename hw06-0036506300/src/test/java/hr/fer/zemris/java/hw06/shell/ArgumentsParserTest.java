package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw06.shell.ArgumentsParser.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArgumentsParserTest {

	@Test
	public void parsePathTest1() {
		Path parsed = parsePath("src/test2_pet");
		assertEquals("src\\test2_pet", parsed.toString());
	}
	
	@Test
	public void parsePathTest2() {
		Path parsed = parsePath("   \"users/ Documents and Settings/javko\" ");
		assertEquals("users\\ Documents and Settings\\javko", parsed.toString());
	}
	
}
