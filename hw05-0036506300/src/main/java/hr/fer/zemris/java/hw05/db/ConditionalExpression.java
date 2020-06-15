package hr.fer.zemris.java.hw05.db;

/**
 * A class that models a conditional expression of a query over a database
 * of student records.
 * @author Vedran Kolka
 *
 */
public class ConditionalExpression {

	/**
	 * A getter for the value of the record to compare.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * The literal to which the record field is compared to.
	 */
	private String stringLiteral;
	/**
	 * An operator of comparison to do.
	 */
	private IComparisonOperator comparisonOperator;
	
	
	public ConditionalExpression(IFieldValueGetter getter,
			String literal, IComparisonOperator comparisonOperator) {
		this.fieldGetter = getter;
		this.comparisonOperator = comparisonOperator;
		this.stringLiteral = literal;
	}
	
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	public String getStringLiteral() {
		return stringLiteral;
	}
	
}
