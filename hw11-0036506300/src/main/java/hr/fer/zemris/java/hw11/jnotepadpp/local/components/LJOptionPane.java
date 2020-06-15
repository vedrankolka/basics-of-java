package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that offers showing localized dialogs to the user.
 * 
 * @author Vedran Kolka
 *
 */
public class LJOptionPane {

	/**
	 * Translates everything there is to translate and and calls
	 * {@link JOptionPane#showOptionDialog(Component, Object, String, int, int, Icon, Object[], Object)}
	 * with the two messages concatenated.
	 * 
	 * @param parent             frame to center the dialog
	 * @param messageToTranslate key of translation that will be provided by the
	 *                           given <code>provider</code>
	 * @param messageNormal      message that will not be translated
	 * @param title              that will be translated by the given
	 *                           <code>provider</code>
	 * @param optionType         an integer designating the options available on the
	 *                           dialog: DEFAULT_OPTION, YES_NO_OPTION,
	 *                           YES_NO_CANCEL_OPTION,or OK_CANCEL_OPTION
	 * @param messageType        an integer designating the kind of message this
	 *                           is,primarily used to determine the icon from the
	 *                           pluggable Look and Feel: ERROR_MESSAGE,
	 *                           INFORMATION_MESSAGE, WARNING_MESSAGE,
	 *                           QUESTION_MESSAGE,or PLAIN_MESSAGE
	 * @param icon               the icon to display in the dialog
	 * @param options            that will be translated by the
	 *                           <code>provider</code>
	 * @param initialValue       the initial option that is selected
	 * @param provider           of the translations
	 * @return index of the chosen option from <code>options</code>
	 */
	public static int showOptionDialog(Component parent, String messageToTranslate, String messageNormal, String title,
			int optionType, int messageType, ImageIcon icon, String[] options, String initialValue,
			ILocalizationProvider provider) {

		String message = messageToTranslate == null ? "" : provider.getString(messageToTranslate);
		message += messageNormal == null ? "" : messageNormal;

		title = provider.getString(title);

		String[] optionsTranslated = new String[options.length];

		for (int i = 0; i < options.length; ++i) {
			optionsTranslated[i] = provider.getString(options[i]);
		}

		if (initialValue != null)
			initialValue = provider.getString(initialValue);

		return JOptionPane.showOptionDialog(parent, message, title, optionType, messageType, icon, optionsTranslated,
				initialValue);
	}

	/**
	 * Translates everything and calls
	 * {@link JOptionPane#showMessageDialog(Component, Object, String, int, Icon)}
	 * 
	 * @param parent      parent frame to center the dialog
	 * @param message     to translate
	 * @param title       to translate
	 * @param messageType an integer designating the kind of message this
	 *                    is,primarily used to determine the icon from the pluggable
	 *                    Look and Feel: ERROR_MESSAGE, INFORMATION_MESSAGE,
	 *                    WARNING_MESSAGE, QUESTION_MESSAGE,or PLAIN_MESSAGE
	 * @param icon        the icon to display in the dialog
	 * @param provider    to translate
	 */
	public static void showMessageDialog(Component parent, String message, String title, int messageType, Icon icon,
			ILocalizationProvider provider) {

		message = provider.getString(message);
		title = provider.getString(title);

		JOptionPane.showMessageDialog(parent, message, title, messageType, icon);
	}

}
