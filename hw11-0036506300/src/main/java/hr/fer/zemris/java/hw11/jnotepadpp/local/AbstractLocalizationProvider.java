package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * An abstraction of a {@link ILocalizationProvider} with implemented methods for adding
 * and removing listeners to this providers registered listeners and for notifying all
 * the registered listeners.
 * @author Vedran Kolka
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * List of listeners registered to this providers localization changes.
	 */
	private List<ILocalizationListener> listeners = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 * @throws NullPointerException if <code>l</code> is <code>null</code>.
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		if(l == null)
			return;
		listeners.remove(l);
	}
	/**
	 * Notifies all registered listeners that a change in locale has occurred.
	 */
	public void fire() {
		listeners.forEach(ILocalizationListener::localizationChanged);
	}

}
