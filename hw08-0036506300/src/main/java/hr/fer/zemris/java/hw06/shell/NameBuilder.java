package hr.fer.zemris.java.hw06.shell;
/**
 * Models an object that knows how to build a filename for massrename command.
 * @author Vedran Kolka
 *
 */
public interface NameBuilder {
	/**
	 * Appends the part of the name from <code>result</code>
	 * to the <code>sb</code>.
	 * @param result
	 * @param sb
	 */
	void execute(FilterResult result, StringBuilder sb);
	
}
