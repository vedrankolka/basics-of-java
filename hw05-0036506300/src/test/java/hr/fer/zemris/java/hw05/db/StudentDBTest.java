package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StudentDBTest {

	private static StudentDatabase db;
	
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
	public void getAllRecordsTest() {
		QueryParser qp = new QueryParser("");
		List<StudentRecord> records = StudentDB.getQueriedRecords(qp, db);
		assertEquals(63, records.size());
	}
	
	@Test
	public void getFirstTenRecords() {
		QueryParser qp = new QueryParser("jmbag <= \"0000000010\" ");
		List<StudentRecord> records = StudentDB.getQueriedRecords(qp, db);
		assertEquals(10, records.size());
	}
	
	@Test
	public void getLastThreeRecords() {
		QueryParser qp = new QueryParser("jmbag>\"0000000060\"");
		List<StudentRecord> records = StudentDB.getQueriedRecords(qp, db);
		assertEquals(3, records.size());
	}
	
	@Test
	public void getOneRecordByDirectQueryTest() {
		QueryParser qp = new QueryParser("jmbag=\"0000000015\"");
		List<StudentRecord> records = StudentDB.getQueriedRecords(qp, db);
		assertEquals(1, records.size());
		StudentRecord r = records.get(0);
		assertEquals("0000000015", r.getJmbag());
	}
	
	@Test
	public void getOneRecordByFIlteringTest() {
		QueryParser qp = new QueryParser("jmbag like \"0*5\" AND firstName like \"K*\" ");
		List<StudentRecord> records = StudentDB.getQueriedRecords(qp, db);
		assertEquals(1, records.size());
		StudentRecord r = records.get(0);
		//0000000015 Glavinić Pecotić Kristijan is the expected record
		assertEquals("0000000015", r.getJmbag());
	}
	
}
