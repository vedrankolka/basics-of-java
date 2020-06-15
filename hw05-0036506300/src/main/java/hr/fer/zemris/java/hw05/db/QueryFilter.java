package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * An implementation of the IFilter that filters by checking all the
 * conditional expressions given through the constructor with a logical and
 * between the conditions.
 * @author Vedran Kolka
 *
 */
public class QueryFilter implements IFilter {
	
	/**
	 * A list of conditional expressions that the StudentRecord must satisfy
	 * to be accepted.
	 */
	private List<ConditionalExpression> expressions;
	
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		//true is a neutral element for logical and, so it is the assumed value
		boolean accept = true;
		
		for(ConditionalExpression e : expressions) {
			IComparisonOperator operator = e.getComparisonOperator();
			IFieldValueGetter getter = e.getFieldGetter();
			String literal = e.getStringLiteral();
			accept = accept && operator.satisfied(getter.get(record), literal);
		}
		
		return accept;
	}

}
