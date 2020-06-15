package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;

/**
 * A Listener interested in the change of {@link Locale} in an {@link ILocalizationProvider}
 * @author Vedran Kolka
 *
 */
public interface ILocalizationListener {
	/**
	 * Method which is called by a {@link ILocalizationProvider} to which this listener
	 * is registered when the {@link Locale} changes.
	 */
	void localizationChanged();
	
}
