package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw05.db.FieldValueGetters.*;
import static hr.fer.zemris.java.hw05.db.ComparisonOperators.*;

public class QueryParserTest {

	@Test
	public void nullTextTest() {
		assertThrows(NullPointerException.class, () -> new QueryParser(null));
	}
	
	@Test
	public void emptySttringTest() {
		QueryParser qp = new QueryParser("");
		assertEquals(0, qp.getQuery().size());
		assertFalse(qp.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
	}
	
	@Test
	public void oneExpressionTest() {
		QueryParser qp = new QueryParser(" lastName <= \"Perić\" ");
		
		assertEquals(1, qp.getQuery().size());
		
		ConditionalExpression e = new ConditionalExpression(LAST_NAME, "Perić", LESS_OR_EQUALS);
		checkExpression(e, qp.getQuery().get(0));
	}
	
	@Test
	public void twoExpressionsTest() {
		QueryParser qp = new QueryParser(" lastName Like \"*ić\" AND firstName=\"Ante\" ");
		
		assertEquals(2, qp.getQuery().size());
		
		ConditionalExpression e1 = new ConditionalExpression(LAST_NAME, "*ić", LIKE);
		checkExpression(e1, qp.getQuery().get(0));
		
		ConditionalExpression e2 = new ConditionalExpression(FIRST_NAME, "Ante", EQUALS);
		checkExpression(e2, qp.getQuery().get(1));
	}
	          //whoa
	public void threeExpressions() {
		QueryParser qp = new QueryParser(" lastName Like \"*ić\" AND firstName=\"Ante\" anD "
				+ "jmbag != \"0000000005\"");
		
		assertEquals(3, qp.getQuery().size());
		
		ConditionalExpression e1 = new ConditionalExpression(LAST_NAME, "*ić", LIKE);
		checkExpression(e1, qp.getQuery().get(0));
		
		ConditionalExpression e2 = new ConditionalExpression(FIRST_NAME, "Ante", EQUALS);
		checkExpression(e2, qp.getQuery().get(1));
		
		ConditionalExpression e3 = new ConditionalExpression(JMBAG, "0000000005", NOT_EQUALS);
		checkExpression(e3, qp.getQuery().get(2));
		
	}
	
	@Test
	public void directQueryTest() {
		QueryParser qp = new QueryParser(" jmbag = \"0000000022\" ");
		assertTrue(qp.isDirectQuery());
		assertEquals("0000000022", qp.getQueriedJMBAG());
	}
	
	private void checkExpression(ConditionalExpression expected, ConditionalExpression actual) {
		assertEquals(expected.getFieldGetter(), actual.getFieldGetter());
		assertEquals(expected.getComparisonOperator(), actual.getComparisonOperator());
		assertEquals(expected.getStringLiteral(), actual.getStringLiteral());
	}
	
}
