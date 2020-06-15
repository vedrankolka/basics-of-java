package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
/**
 * A {@link LocalizationProviderBridge} that is also a {@link WindowListener}
 * that connects the bridge upon window opening and disconnects it when the
 * window is closed
 * @author Vedran Kolka
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge implements WindowListener {

	/**
	 * Constructor
	 * @param provider to wrap
	 * @param parent - the window to which this listener registers itself
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame parent) {
		super(provider);
		parent.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		connect();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		disconnect();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

}
