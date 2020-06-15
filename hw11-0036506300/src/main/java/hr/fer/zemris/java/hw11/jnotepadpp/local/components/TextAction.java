package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import java.awt.event.ActionEvent;
import java.util.function.UnaryOperator;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument.SimpleMultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument.SingleDocumentModel;

/**
 * A LocalizedAction that operates over selected text on the current document in
 * the {@link MultipleDocumentModel} it is given through the constructor.
 * 
 * @author Vedran Kolka
 *
 */
public class TextAction extends LocalizedAction {

	private static final long serialVersionUID = 1L;
	/** MultipleDocumentListener that this action is listening to */
	protected MultipleDocumentModel m;
	/** text selected with the caret */
	private String selected;
	/** last read position of the minimum of dot and mark */
	protected int start;
	/** last read position of the maximum of dot and mark */
	protected int end;
	/** The operation to transform the selected text */
	private UnaryOperator<String> op;

	/**
	 * Constructor
	 * 
	 * @param key for translating the name of the action
	 * @param lp  provider of the translation
	 * @param m   MultipleDocumentModel on which the action operates
	 * @param op  UnaryOperator used to transform the selected text
	 */
	public TextAction(String key, ILocalizationProvider lp, MultipleDocumentModel m, UnaryOperator<String> op) {
		super(key, lp);
		this.m = m;
		this.op = op;
		CaretListener listener = e -> {
			start = getStart(e.getDot(), e.getMark());
			end = getEnd(e.getDot(), e.getMark());
			setEnabled(isSelected(e));
		};

		m.addMultipleDocumentListener(new SimpleMultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null)
					previousModel.getTextComponent().removeCaretListener(listener);
				if (currentModel != null)
					currentModel.getTextComponent().addCaretListener(listener);
				else
					setEnabled(false);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (start == end)
			return;

		try {
			String transformed = op.apply(getSelected());
			Document doc = m.getCurrentDocument().getTextComponent().getDocument();

			doc.remove(start, end - start);
			doc.insertString(start, transformed, null);
		} catch (BadLocationException ignorable) {
			// should not happen
		}
	}
	/**
	 * Returns the start of selected text
	 * @param dot of caret
	 * @param mark of caret
	 * @return start
	 */
	public int getStart(int dot, int mark) {
		return dot < mark ? dot : mark;
	}
	
	/**
	 * Returns the end of selected text
	 * @param dot of caret
	 * @param mark of caret
	 * @return end
	 */
	public int getEnd(int dot, int mark) {
		return mark > dot ? mark : dot;
	}
	
	/**
	 * Checks if any part of text is selected.
	 * @return <code>true</code> if it is, <code>false</code> otherwise
	 */
	public boolean isSelected(CaretEvent e) {
		return e.getDot() != e.getMark();
	}

	/**
	 * Returns the selected text in the current document.
	 * 
	 * @return selected text
	 * @throws NullPointerException if current document of the model is
	 *                              <code>null</code>
	 */
	public String getSelected() {
		if (m.getCurrentDocument() == null)
			throw new NullPointerException("Current model is not set.");
		try {
			selected = m.getCurrentDocument().getTextComponent().getText(start, end - start);
		} catch (BadLocationException ignorable) {
			// should not happen
		}
		return selected;
	}

	/**
	 * Switches all lower case letters in the given <code>text</code> to upper case.
	 * @param text to transform
	 * @return text with all upper case letters
	 */
	public static String toUpper(String text) {
		return toggleCase(text, true, false);
	}

	/**
	 * Switches all upper case letters in the given <code>text</code> to lower case.
	 * @param text to transform
	 * @return text with all lower case letters
	 */
	public static String toLower(String text) {
		return toggleCase(text, false, true);
	}
	
	/**
	 * Switches all lower case letters in the given <code>text</code> to upper case and vice versa.
	 * @param text to transform
	 * @return text with all letters case inverted
	 */
	public static String invertCase(String text) {
		return toggleCase(text, true, true);
	}

	/**
	 * Changes upper case letters to lower if the flag <code>toUpper</code> is <code>true</code>
	 * and lower case letters to upper if flag <code>toLower</code> and returns the transformed text
	 * @param text to transform
	 * @param toUpper flag to indicate if lower case letters are switched to upper
	 * @param toLower flag to indicate if upper case letters are switched to lower
	 * @return transformed text
	 */
	private static String toggleCase(String text, boolean toUpper, boolean toLower) {
		if (text == null || text.length() == 0)
			return text;
		StringBuilder sb = new StringBuilder();
		char[] chars = text.toCharArray();

		for (int i = 0; i < chars.length; ++i) {

			if (toUpper && Character.isLowerCase(chars[i])) {
				sb.append(Character.toUpperCase(chars[i]));
			} else if (toLower && Character.isUpperCase(chars[i])) {
				sb.append(Character.toLowerCase(chars[i]));
			} else {
				sb.append(chars[i]);
			}

		}
		
		return sb.toString();
	}

}
