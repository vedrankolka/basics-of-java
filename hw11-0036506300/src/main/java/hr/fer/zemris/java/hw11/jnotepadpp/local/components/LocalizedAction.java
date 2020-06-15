package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * A localized Action that changes its name accordingly to the active language.
 * 
 * @author Vedran Kolka
 *
 */
public abstract class LocalizedAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	/** key of the name to display */
	private String key;
	/** provider of translation */
	protected ILocalizationProvider lp;

	/**
	 * Constructor
	 * 
	 * @param key of the name of this Action
	 * @param lp  {@link ILocalizationProvider} to provide translation of the key
	 */
	public LocalizedAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		putValue(Action.NAME, lp.getString(key));
		// add a listener to update the name accordingly
		this.lp.addLocalizationListener(() -> {
			putValue(Action.NAME, this.lp.getString(this.key));
		});
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

}
