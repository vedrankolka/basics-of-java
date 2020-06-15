package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw05.db.FieldValueGetters.*;

public class FieldValueGettersTest {

	@Test
	public void FIRST_NAMEtest() {
		StudentRecord r = new StudentRecord("0123456789", "Mala Princeza", "Leonora", 5);
		assertEquals("Leonora", FIRST_NAME.get(r));
	}
	
	@Test
	public void LAST_NAMEtest() {
		StudentRecord r = new StudentRecord("0123456789", "Mala Princeza", "Leonora", 5);
		assertEquals("Mala Princeza", LAST_NAME.get(r));
	}
	
	@Test
	public void JMBAGtest() {
		StudentRecord r = new StudentRecord("0123456789", "Mala Princeza", "Leonora", 5);
		assertEquals("0123456789", JMBAG.get(r));
	}
	
}
