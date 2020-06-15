package hr.fer.zemris.java.hw05.db;

import static hr.fer.zemris.java.hw05.db.ComparisonOperators.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

	@Test
	public void LESSTest() {
		assertTrue(LESS.satisfied("Ana", "Banana"));
		assertTrue(LESS.satisfied("Ana", "Anja"));
		assertTrue(LESS.satisfied("Leonarda", "Vedran"));
		assertTrue(LESS.satisfied("", "Ana"));
		assertFalse(LESS.satisfied("Grana", "Ana"));
		assertFalse(LESS.satisfied("Banana", "Ana"));
		assertFalse(LESS.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void LESS_OR_EQUALSTest() {
		assertTrue(LESS_OR_EQUALS.satisfied("Ana", "Banana"));
		assertTrue(LESS_OR_EQUALS.satisfied("Ana", "Anja"));
		assertTrue(LESS_OR_EQUALS.satisfied("Leonarda", "Vedran"));
		assertTrue(LESS_OR_EQUALS.satisfied("", "Ana"));
		assertTrue(LESS_OR_EQUALS.satisfied("Ana", "Ana"));
		assertFalse(LESS_OR_EQUALS.satisfied("Grana", "Ana"));
		assertFalse(LESS_OR_EQUALS.satisfied("Banana", "Ana"));
	}
	
	@Test
	public void LIKETets() {
		assertTrue(LIKE.satisfied("Ana", "A"));
		assertTrue(LIKE.satisfied("Banana", "ana"));
		assertTrue(LIKE.satisfied("Aaaana", "A*a"));
		assertTrue(LIKE.satisfied("Leonarda", "L*arda"));
		assertTrue(LIKE.satisfied("Leonarda", "ard"));
		assertTrue(LIKE.satisfied("Leonarda", "Leona*"));
		assertTrue(LIKE.satisfied("Leonarda", "Leonarda*"));
		assertFalse(LIKE.satisfied("AAA", "AA*AA"));
		assertFalse(LIKE.satisfied("AAA", "AAAA*"));
		assertFalse(LIKE.satisfied("AAA", "*AAAA"));
		assertThrows(IllegalArgumentException.class, () -> LIKE.satisfied("Banana", "B*na*a"));
		assertThrows(IllegalArgumentException.class, () -> LIKE.satisfied("Banana", "B**"));
		assertThrows(IllegalArgumentException.class, () -> LIKE.satisfied("Banana", "*Banana*"));
	}
	
}
