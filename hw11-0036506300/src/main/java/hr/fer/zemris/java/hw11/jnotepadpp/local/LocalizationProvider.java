package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * An implementation of a {@link ILocalizationProvider} which language
 * is set to English by default.
 * @author Vedran Kolka
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/** The single instance of this provider */
	private static final LocalizationProvider provider = new LocalizationProvider();
	/** Current language */
	private String language;
	/** ResourceBundle used by this provider to get translations */
	private ResourceBundle bundle;
	/** Base name of the path to the dictionaries for this providers bundle */
	private static final String BASE_NAME = "hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi";
	/**
	 * Returns <b>the single</b> instance of this LocalizationProvider.
	 * @return LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		return provider;
	}
	
	/**
	 * Constructor. Sets the current language to English.
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BASE_NAME, locale);
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	/**
	 * Sets this providers language to the given <code>language</code>
	 * and notifies listeners that the language has changed.
	 * @param language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale l = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BASE_NAME, l);
		fire();
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
