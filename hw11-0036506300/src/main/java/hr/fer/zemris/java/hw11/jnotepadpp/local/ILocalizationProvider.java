package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;

/**
 * A provider of translated strings.
 * @author Vedran Kolka
 *
 */
public interface ILocalizationProvider {
	/**
	 * Registers the {@link ILocalizationListener} <code>l</code> to this
	 * ILocalizationProvider
	 * @param l - {@link ILocalizationListener} to register
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	void addLocalizationListener(ILocalizationListener l);
	/**
	 * Removes the {@link ILocalizationListener} <code>l</code> from the listeners registered to this
	 * ILocalizationProvider
	 * @param l - {@link ILocalizationListener} to register
	 */
	void removeLocalizationListener(ILocalizationListener l);
	/**
	 * Returns a translation of the given <code>key</code> with this providers {@link Locale}.
	 * @param key of the requested translation
	 * @return translation of the <code>key</code>
	 */
	String getString(String key);
	/**
	 * Returns the current language.
	 * @return the current language
	 */
	String getCurrentLanguage();
	
}
