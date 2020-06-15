package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {

	public static StudentDatabase db;
	
	@BeforeAll
	public static void readTextForDataBase() {
		try {
			List<String> text = Files.readAllLines(Paths.get("src\\test\\resources\\database.txt"),
													StandardCharsets.UTF_8);
			db = new StudentDatabase(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void forJMBAGtest() {
		assertEquals("Kristijan" , db.forJMBAG("0000000015").getFirstName());
		assertEquals("Željko" , db.forJMBAG("0000000063").getFirstName());
		assertEquals("Marin" , db.forJMBAG("0000000001").getFirstName());
	}
	
	@Test
	public void filterNoneTest() {
		List<StudentRecord> filtered = db.filter( r -> true);
		assertEquals(db.size(), filtered.size());
	}
		
	@Test
	public void filterAllTest() {
		List<StudentRecord> filtered = db.filter( r -> false);
		assertEquals(0, filtered.size());
	}
	
}
