package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexer;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerException;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerState;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerToken;

import static hr.fer.zemris.java.hw06.shell.lexer.TokenType.*;

public class NameBuilderParser {
	/**
	 * The NameBuilder that is composed to build the whole name.
	 */
	private CompositeNameBuilder compositeNameBuilder;
	/**
	 * The lexer that the parser uses to parse the expression.
	 */
	private ArgumentsLexer lexer;

	public NameBuilderParser(String expression) {
		compositeNameBuilder = new CompositeNameBuilder();
		lexer = new ArgumentsLexer(expression);
		lexer.setState(ArgumentsLexerState.EXPRESSION);
		try {
			parse();
		} catch (ArgumentsLexerException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	private void parse() {
		while (true) {
			ArgumentsLexerToken token = lexer.nextToken();
			
			if(token.getType() == EOF) break;
			
			if (token.getType() == STRING) {
				addBuilder(text(token.getValue()));
				continue;
			}
			
			if (token.getType() == SUBSTITUTION_START) {
				lexer.setState(ArgumentsLexerState.SUBSTITUTION);
				parseSubstitution();
				continue;
			}
			
		}
	}
	
	/**
	 * Parses the whole substitution expression and adds a
	 * NameBuilder for the parsed expression if it is valid.
	 */
	private void parseSubstitution() {
		ArgumentsLexerToken token = lexer.nextToken();
		if(token.getType() != NUMBER) {
			throw new IllegalArgumentException("The substitution expression must have a number.");
		}
		int index = Integer.parseInt(token.getValue());
		
		token = lexer.nextToken();
		
		if(token.getType() == SUBSTITUTION_END) {
			addBuilder(group(index));
			lexer.setState(ArgumentsLexerState.EXPRESSION);
			return;
		}
		// if the token was not the end of the substitution expression
		// and it is not a ',' , then it is not a valid expression
		if(!(token.getType() == SYMBOL && token.getValue().equals(","))) {
			throw new IllegalArgumentException("A comma was expected, but the value was: "
												+ token.getValue());
		}
		
		token = lexer.nextToken();
		if(token.getType() != NUMBER) {
			throw new IllegalArgumentException("The second argument in the substitution"
					+ " expression must also be a number.");
		}
		
		String number = token.getValue();
		if(number.length() > 1 && number.startsWith("0")) {
			int minWidth = Integer.parseInt(number.substring(1));
			addBuilder(group(index, '0', minWidth));
		} else {
			int minWidth = Integer.parseInt(number);
			addBuilder(group(index, ' ', minWidth));
		}
		// now the next token must be the end of the substitution expression
		token = lexer.nextToken();
		if(token.getType() != SUBSTITUTION_END) {
			throw new IllegalArgumentException("End of substitution was expected, but value was: "
					+ token.getValue());
		}
		// if it was, change the state and return
		lexer.setState(ArgumentsLexerState.BASIC);
		
	}

	/**
	 * Returns the composite NameBuilder.
	 * 
	 * @return
	 */
	public NameBuilder getNameBuilder() {
		return (NameBuilder) compositeNameBuilder;
	}

	/**
	 * Adds the given builder to the compositeNameBuilder and returns the
	 * compositeNameBuilder.
	 * 
	 * @param nb
	 * @return compositeNameBuilder with the added nb
	 */
	public NameBuilder addBuilder(NameBuilder nb) {
		compositeNameBuilder.nameBuilders.add(nb);
		return compositeNameBuilder;
	}

	/**
	 * Returns a NameBuilder that builds the name by appending the given text to the
	 * StringBuilder.
	 * 
	 * @param t
	 * @return NameBuilder that builds the name by appending the given text to the
	 *         StringBuilder
	 */
	public static NameBuilder text(String t) {
		return (r, sb) -> sb.append(t);
	}

	/**
	 * Returns a NameBuilder that appends the indexed group to the given
	 * StringBuilder.
	 * 
	 * @param index
	 * @return NameBuilder that appends the indexed group to the given StringBuilder
	 */
	public static NameBuilder group(int index) {
		return (r, sb) -> sb.append(r.group(index));
	}

	/**
	 * Returns a NameBuilder that appends the <code>padding</code> to the
	 * StringBuilder so that the appended indexed group is at least
	 * <code>minWidth</code> long. If the indexed group is equal to or longer than
	 * the <code>minWidth</code> no padding is appended.
	 * 
	 * @param index
	 * @param padding
	 * @param minWidth
	 * @return a NameBuilder that adds padding to the StringBuilder if necessary and
	 *         then the indexed group
	 */
	public static NameBuilder group(int index, char padding, int minWidth) {
		return (r, sb) -> {
			String s = r.group(index);
			int lenOfPadding = minWidth - s.length() > 0 ? minWidth - s.length() : 0;
			String stringPadding = Character.toString(padding).repeat(lenOfPadding);
			sb.append(stringPadding);
			sb.append(s);
		};
	}

	private static class CompositeNameBuilder implements NameBuilder {

		private List<NameBuilder> nameBuilders;

		public CompositeNameBuilder() {
			nameBuilders = new ArrayList<>();
		}

		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			nameBuilders.forEach(nb -> nb.execute(result, sb));
		}

	}

}
