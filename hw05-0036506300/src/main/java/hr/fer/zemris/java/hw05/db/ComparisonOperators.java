package hr.fer.zemris.java.hw05.db;
/**
 * Concrete implementations of IComparisonOperators for common queries.
 * @author Vedran Kolka
 *
 */
public class ComparisonOperators {

	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0 ;
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0 ;
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0 ;
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0 ;
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0 ;
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0 ;
	public static final IComparisonOperator LIKE;
	
	static {
		LIKE = new IComparisonOperator() {
			private static final char WILDCARD = '*';
			@Override
			public boolean satisfied(String value1, String value2) {
				
				int firstIndexOfWildcard = value2.indexOf(WILDCARD);
				int lastIndexOfWildcard = value2.lastIndexOf(WILDCARD);
				if(firstIndexOfWildcard != lastIndexOfWildcard) {
					throw new IllegalArgumentException("The literal cannot contain more than one wildcard.");
				}
				//if the literal does not contain the wildcard
				if(firstIndexOfWildcard == -1) {
					return value1.contains(value2);
				}
				//if the literal without the wildcard is longer than the value, it cannot match
				if(value2.length()-1 > value1.length()) {
					return false;
				}
				//if the literal starts with a wildcard the end must match
				if(firstIndexOfWildcard == 0) {
					return value1.endsWith(value2.substring(1));
				}
				//if the literal ends with a wildcard the start must match
				if(firstIndexOfWildcard == value2.length()-1) {
					return value1.startsWith(value2.substring(0, value2.length()-1));
				}
				/*
				 * then it is in the middle so the literal is separated with the wildcard
				 * to a prefix and a suffix and both must match to satisfy the comparison
				 */
				String[] s = value2.split("\\*");
				String prefix = s[0];
				String suffix = s[1];
				return value1.startsWith(prefix) && value1.endsWith(suffix);
			}
		};
	}
}
