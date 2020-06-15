package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * A localized JMenu that changes its name accordingly to the active language.
 * 
 * @author Vedran Kolka
 *
 */
public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	/** key of the text to display */
	private String key;
	/** provider of translation */
	private ILocalizationProvider lp;

	/**
	 * Constructor.
	 * @param key of the name of this menu
	 * @param lp {@link ILocalizationProvider} that provides the translation of the key
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		setText(key);
		this.lp.addLocalizationListener(() -> setText(this.key));
	}

	/**
	 * Sets the translation of given <code>text</code> provided by the
	 * {@link ILocalizationProvider} <code>lp</code>. {@inheritDoc}
	 */
	@Override
	public void setText(String text) {
		if (text.length() == 0)
			super.setText(text);
		else
			super.setText(lp.getString(text));
	}

}
