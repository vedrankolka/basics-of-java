package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import static hr.fer.zemris.java.hw05.db.FieldValueGetters.*;
import static hr.fer.zemris.java.hw05.db.ComparisonOperators.*;

public class QueryFilterTest {

	public static List<StudentRecord> testRecords;
	
	@BeforeAll
	public static void initializeRecords() {
		testRecords = new ArrayList<StudentRecord>();
		testRecords.add(new StudentRecord("01", "Perić", "Ante", 5));
		testRecords.add(new StudentRecord("02", "Vuić", "Adem", 5));
		testRecords.add(new StudentRecord("03", "Vuić", "Badem", 5));
		testRecords.add(new StudentRecord("04", "Vuić", "Cadem", 5));
		testRecords.add(new StudentRecord("05", "Vuić", "Dadem", 5));
		testRecords.add(new StudentRecord("06", "Kolka", "Vedran", 2));
	}
	
	@Test
	public void oneConditionalExpressionTest() {
		List<ConditionalExpression> exs = new ArrayList<>();
		exs.add(new ConditionalExpression(FIRST_NAME, "*dem", LIKE));
		IFilter filter = new QueryFilter(exs);
		assertFalse(filter.accepts(testRecords.get(0)));
		for(int i = 1 ; i<5 ; ++i) {
			assertTrue(filter.accepts(testRecords.get(i)));
		}
		assertFalse(filter.accepts(testRecords.get(5)));
	}
	
	@Test
	public void noConditionalExpressionsTest() {
		IFilter acceptAllFilter = new QueryFilter(new ArrayList<>());
		for(StudentRecord r : testRecords) {
			assertTrue(acceptAllFilter.accepts(r));
		}
	}
	
	@Test
	public void threeConditionalExpressionsTest() {
		List<ConditionalExpression> exs = new ArrayList<>();
		exs.add(new ConditionalExpression(FIRST_NAME, "*dem", LIKE));
		exs.add(new ConditionalExpression(JMBAG, "04", LESS_OR_EQUALS));
		exs.add(new ConditionalExpression(FIRST_NAME, "Adem", GREATER));
		IFilter filter = new QueryFilter(exs);
		assertFalse(filter.accepts(testRecords.get(0)));
		assertFalse(filter.accepts(testRecords.get(1)));
		assertTrue(filter.accepts(testRecords.get(2)));
		assertTrue(filter.accepts(testRecords.get(3)));
		assertFalse(filter.accepts(testRecords.get(4)));
		assertFalse(filter.accepts(testRecords.get(5)));
	}
	
}
