package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

/**
 * A decorator for a {@link ILocalizationProvider} that can connect and disconnect
 * from the wrapped provider to avoid memory leakage.
 * 
 * @author Vedran Kolka
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**tells if the bridge is currently connected */
	private boolean connected;
	/** the wrapped provider */
	private ILocalizationProvider provider;
	/** listener registered to the wrapped provider */
	private ILocalizationListener listener;
	/** Cached last language that was set */
	private String currentLanguage;

	/**
	 * Constructor
	 * 
	 * @param provider to wrap
	 * @throws NullPointerException if given <code>provider</code> is
	 *                              <code>null</code>
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		super();
		this.provider = Objects.requireNonNull(provider);
		this.listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		};
		this.currentLanguage = provider.getCurrentLanguage();
	}

	/**
	 * Registers an {@link ILocalizationListener} to the wrapped
	 * <code>provider</code> with the action of calling
	 * {@link AbstractLocalizationProvider#fire()} to notify listeners registered to
	 * <code>this</code> LocalizationProviderBridge.
	 */
	public void connect() {
		if (connected)
			return;

		provider.addLocalizationListener(listener);
		connected = true;
		// if the language was changed while the bridge was disconnected update
		// currentLanguage and notify listeners
		if (!currentLanguage.equals(provider.getCurrentLanguage())) {
			currentLanguage = provider.getCurrentLanguage();
			fire();
		}
	}

	/**
	 * Deregisters the {@link ILocalizationListener} <code>listener</code> from the wrapped
	 * <code>provider</code>
	 */
	public void disconnect() {
		if (!connected)
			return;
		provider.removeLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}

}
