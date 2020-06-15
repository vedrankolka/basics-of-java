package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.StudentRecord;
import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;

public class ConditionalExpressionDemo {

	public static void main(String[] args) {
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Mala*",
				 ComparisonOperators.LIKE
				);
		
		StudentRecord record = new StudentRecord("0123456789", "Mala Princeza", "Leonora", 5);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Mala*"
				);
		System.out.println(recordSatisfies);
	}
	
}
