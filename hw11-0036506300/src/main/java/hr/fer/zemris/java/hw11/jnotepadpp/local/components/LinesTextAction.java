package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.UnaryOperator;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument.MultipleDocumentModel;

/**
 * A TextAction that operates with whole selected lines.<br>
 * The selected text will be expanded to cover the start of the line where
 * selection started and the end of the line where selection ended.
 * 
 * @author Vedran Kolka
 *
 */
public class LinesTextAction extends TextAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param key of the name of this action
	 * @param lp {@link ILocalizationProvider} that provides the translation of the name
	 * @param m {@link MultipleDocumentModel} that provides the current document o which this action operates
	 * @param op operator which transforms the selected text
	 */
	public LinesTextAction(String key, ILocalizationProvider lp, MultipleDocumentModel m, UnaryOperator<String> op) {
		super(key, lp, m, op);
	}

	@Override
	public int getStart(int dot, int mark) {
		// start from the lower and find the start of a line
		int start = dot < mark ? dot : mark;
		String text = m.getCurrentDocument().getTextComponent().getText();
		while (start > 0) {
			if (text.charAt(start - 1) == '\n')
				break;
			start--;
		}

		return start;
	}

	@Override
	public int getEnd(int dot, int mark) {
		// start from the bigger and find the end of the line inclusive
		int end = mark > dot ? mark : dot;
		String text = m.getCurrentDocument().getTextComponent().getText();
		while (end < text.length()) {
			if (text.charAt(end) == '\n')
				break;
			end++;
		}

		return end;
	}

	/**
	 * Breaks the text by lines, sorts them ascending using a localized collator,
	 * appends them and returns the resulting text.
	 * 
	 * @param text to sort
	 * @return text sorted ascending by lines
	 */
	public static String sortLinesAscending(String text, ILocalizationProvider provider) {
		return sortLines(text, provider, true);
	}

	/**
	 * Breaks the text by lines, sorts them descending using a localized collator,
	 * appends them and returns the resulting text.
	 * 
	 * @param text to sort
	 * @return text sorted descending by lines
	 */
	public static String sortLinesDescending(String text, ILocalizationProvider provider) {
		return sortLines(text, provider, false);
	}

	/**
	 * Breaks the given <code>text</code> to lines by separating over '\n', sorts the lines
	 * alphabetically with a collator with a language obtained from the given <code>provider</code>
	 * and returns the result as one String
	 * @param text
	 * @param provider
	 * @param ascending
	 * @return
	 */
	private static String sortLines(String text, ILocalizationProvider provider, boolean ascending) {

		if (text == null || text.length() == 0)
			return text;

		StringBuilder sb = new StringBuilder();
		String[] lines = text.split("\\n");
		// get the language and sort with the appropriate collator
		Locale l = new Locale(provider.getCurrentLanguage());
		Collator c = Collator.getInstance(l);
		Arrays.sort(lines, c);
		// append ascending or descending
		if (ascending) {
			for (int i = 0; i < lines.length; ++i) {
				sb.append(lines[i]).append('\n');
			}
		} else {
			for (int i = lines.length - 1; i >= 0; --i) {
				sb.append(lines[i]).append('\n');
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Breaks the given <code>text</code> to lines, removes all duplicate lines
	 * leaving only the first occurrence of each unique line and returns the result.
	 * 
	 * @param text
	 * @return text with no duplicate lines
	 */
	public static String removeDuplicateLines(String text) {
		if (text == null || text.length() == 0)
			return text;

		String[] lines = text.split("\\n");
		StringBuilder sb = new StringBuilder();
		Set<String> setOfLines = new TreeSet<>();

		for (String line : lines) {
			if (setOfLines.add(line))
				sb.append(line).append('\n');
		}

		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

}
