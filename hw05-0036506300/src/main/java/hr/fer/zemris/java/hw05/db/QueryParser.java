package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.simplelexer.LexerException;
import hr.fer.zemris.java.hw05.db.simplelexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.simplelexer.Token;
import hr.fer.zemris.java.hw05.db.simplelexer.TokenType;

/**
 * A simple parser for the query command called upon a student database.
 * @author Vedran Kolka
 *
 */
public class QueryParser {

	private static final String LOGICAL_AND = "AND";
	/**
	 * A flag that says if a query was a direct one.
	 */
	private boolean directQuery;
	/**
	 * The JMBAG that was queried if the query was a direct one.
	 */
	private String queriedJMBAG;
	/**
	 * A list of the conditional expression of the query.
	 */
	private List<ConditionalExpression> expressions;
	/**
	 * A lexer that the parser uses to get tokens of the query.
	 */
	private QueryLexer lexer;
	
	public QueryParser(String text) {
		expressions = new ArrayList<ConditionalExpression>();
		/*
		 * The algorithm is not designed for a query with no expressions
		 * so that case is checked before actually parsing the text.
		 */
		if(text.length() == 0) {
			return;
		}
		lexer = new QueryLexer(text);
		parse();
		//if there is only one expression, see if it is a direct query and initialize if necessary
		if(expressions.size() == 1) {
			ConditionalExpression e = expressions.get(0);
			directQuery = e.getFieldGetter() == FieldValueGetters.JMBAG &&
					e.getComparisonOperator() == ComparisonOperators.EQUALS;
			if(directQuery) {
				queriedJMBAG = e.getStringLiteral();
			}
		}
		
	}
	
	/**
	 * Checks if the parsed query is a direct query.
	 * @return <code>true</code> if it is, <code>false</code> otherwise
	 */
	public boolean isDirectQuery() {
		return directQuery;
	}
	
	/**
	 * Returns the queried JMBAG if the query was a direct query.
	 * @return queried JMBAG
	 * @throws IllegalStateException if the query is not direct query
	 */
	public String getQueriedJMBAG() {
		if(!directQuery) {
			throw new IllegalStateException("Cannot get queried jmbag. The query is not direct.");
		}
		return queriedJMBAG;
	}
	
	/**
	 * Returns a list of conditional expressions of the query.
	 * @return list of ConditionalExpressions
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}
	
	/**
	 * Reads the query and fills the list <code>expressions</code>
	 * with read conditional expressions.
	 */
	private void parse() {
		
		while(true) {
			
			ConditionalExpression newExpression = getNextExpression();
			expressions.add(newExpression);
			
			Token token = lexer.nextToken();
			//if the end of the query is reached, end the loop
			if(token.getType() == TokenType.EOF) {
				break;
			}
			//if it is not, a logical operator AND must be the next token
			if( !(token.getType() == TokenType.KEYWORD &&
				  token.getValue().toUpperCase().equals(LOGICAL_AND)) ) {
				throw new IllegalArgumentException("A logical and was expected between"
						+ " conditional expressions.");
			}
			//if it was, continue to parse the next expression
		}	
	}
	
	/**
	 * Reads three tokens that should make a conditional expression,
	 * creates the expression and returns it 
	 * @return ConditionalExpression that is read from the next three tokens.
	 */
	private ConditionalExpression getNextExpression() {
		try {
			Token propertyName = lexer.nextToken();
			Token comparisonOperatorToken = lexer.nextToken();
			Token literalToken = lexer.nextToken();
		
			if(propertyName.getType() != TokenType.KEYWORD) {
				throw new IllegalArgumentException("A conditional expression must start with a property name.");
			}
			if(comparisonOperatorToken.getType() != TokenType.OPERATOR) {
				throw new IllegalArgumentException("A property name must be followed by a comparison operator.");
			}
			if(literalToken.getType() != TokenType.STRING) {
				throw new IllegalArgumentException("Right side of the expression must be a string literal.");
			}
			
			IFieldValueGetter getter = getGetter(propertyName.getValue());
			String literal = literalToken.getValue();
			IComparisonOperator comparisonOperator = getComparisonOperator(comparisonOperatorToken.getValue());
		
			return new ConditionalExpression(getter, literal, comparisonOperator);
		} catch(LexerException e) {
			throw new IllegalArgumentException("Invalid query.");
		}
	}
	
	/**
	 * The method identifies which property getter is needed and returns it.
	 * @param propertyName
	 * @return getter for the propertyName
	 * @throws IllegalArgumentException if there is no getter
	 * for the given <code>propertyName</code>
	 */
	private IFieldValueGetter getGetter(String propertyName) {
		if(propertyName.equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
		}
		if(propertyName.equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		}
		if(propertyName.equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		}
		//if it was none of the above, throw hands
		throw new IllegalArgumentException("Unknown property name.");
	}
	
	/**
	 * The method identifies the operator and returns the appropriate comparison operator.
	 * @param operation of comparison
	 * @return IComparisonOperator
	 * @throws IllegalArgumentException if the operator is not supported
	 */
	private IComparisonOperator getComparisonOperator(String operation) {
		switch(operation) {
		case ">":
			return ComparisonOperators.GREATER;
		case "<":
			return ComparisonOperators.LESS;
		case "=":
			return ComparisonOperators.EQUALS;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			//if it is not a recognizable operator, throw hands
			throw new IllegalArgumentException("Unknown operator: " + operation);
		}
	}
}
