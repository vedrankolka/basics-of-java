package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
/**
 * A localized action that sets the current language to the one given through the constructor.
 * @author Vedran Kolka
 *
 */
public class SetLanguageAction extends LocalizedAction {

	private static final long serialVersionUID = 1L;
	/** language to set */
	private String language;

	/**
	 * Constructor.
	 * 
	 * @param language to set
	 * @param lp {@link ILocalizationProvider} that provides the translation
	 */
	public SetLanguageAction(String language, ILocalizationProvider lp) {
		super(language, lp);
		this.language = language;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(language);
	}

}
