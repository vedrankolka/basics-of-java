package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.MissingResourceException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.JClock;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJOptionPane;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LinesTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LocalizedAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.SetLanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.TextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument.SingleDocumentModel;

/**
 * A fully functional notepad that supports multiple document editing.<br>
 * The application offers three languages:
 * <ul>
 * <li>English
 * <li>German
 * <li>Croatian
 * </ul>
 * <p>
 * Besides the standard text editing tools the application offers tools for
 * changing character casing, sorting lines and removing duplicate lines.
 * <p>
 * All documents are read and saved in UTF-8 encoding.
 * 
 * @author Vedran Kolka
 * @version 1.0.1
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	/** title of the application */
	public static final String TITLE = "JNotepad++";
	/** Default title for a new, unnamed document */
	public static final String UNNAMED_DOCUMENT = "(unnamed)";
	/** Yes No options to be displayed in a dialog with the user */
	public static final String[] YES_NO_CLOSE_OPTIONS = { "yes", "no", "cancel" };
	/** Index of yes option */
	public static final int YES = 0;
	/** Index of no option */
	public static final int NO = 1;
	/** Index of cancel option */
	public static final int CLOSE_CANCEL = 2;
	/** Options when saving a document */
	public static final String[] SAVE_DONT_SAVE_OPTIONS = { "save_all", "save", "dont_save", "cancel" };
	/** Index of save all option */
	public static final int SAVE_ALL = 0;
	/** Index of save option */
	public static final int SAVE = 1;
	/** Index of don't save option */
	public static final int DONT_SAVE = 2;
	/** Index of cancel a save operation option */
	public static final int SAVE_CANCEL = 3;
	/** key for English */
	public static final String EN = "en";
	/** key for German */
	public static final String DE = "de";
	/** key for Croatian */
	public static final String HR = "hr";
	/** icon to indicate a saved document */
	private final ImageIcon SAVED = loadImage("pear.png");
	/** icon to indicate an unsaved document */
	private final ImageIcon UNSAVED = loadImage("apple.png");
	/** a pool of executor threads ready for calculating */
	private ExecutorService pool;
	/**
	 * the model doing the auto-magic work with tabs and documents behind the scenes
	 */
	private MultipleDocumentModel mdm;
	/** a bridge to a ILocalizationProvider */
	private FormLocalizationProvider provider;
	/** opens a new document */
	private Action newDocument;
	/** opens a file chooser to open a document */
	private Action openDocument;
	/** saves the current document */
	private Action saveDocument;
	/** opens a file chooser to save the current file */
	private Action saveDocumentAs;
	/** closes the current document */
	private Action closeDocument;
	/** action that calculates statistics about the current document */
	private Action statistics;
	/** standard copy action */
	private Action copyAction;
	/** standard paste action */
	private Action pasteAction;
	/** standard cut action */
	private Action cutAction;
	/** sets the language to English */
	private Action setEnglish;
	/** sets the language to German */
	private Action setGerman;
	/** sets the language to Croatian */
	private Action setCroatian;
	/** changes all letters to upper case in the selected text */
	private Action toUppercase;
	/** changes all letters to lower case in the selected text */
	private Action toLowercase;
	/** inverts the upper case letters to lower and vice versa */
	private Action invertCase;
	/** sorts the selected lines ascending alphabetically */
	private Action sortAscending;
	/** sorts the selected lines descending alphabetically */
	private Action sortDescending;
	/** removes all duplicate lines from the selected text */
	private Action unique;
	/** starts the exiting process */
	private Action exit;
	/** the window listener responsible for closing the application */
	private WindowListener windowListener;

	/**
	 * Constructor
	 */
	public JNotepadPP() {
		pool = Executors.newFixedThreadPool(1, r -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(600, 300));
		setLocation(50, 50);
		setTitle(TITLE);

		initGUI();
		pack();
	}

	/**
	 * Initializes the whole layout of the frame and all Actions.
	 */
	private void initGUI() {
		// add a windows listener to check unsaved documents before closing and to
		// shutdown the thread pool
		windowListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (checkUnsaved())
					dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				pool.shutdown();
			}

		};
		addWindowListener(windowListener);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		// create a JPanel for the center so that the toolbar remains floatable
		JPanel centerPanel = new JPanel(new BorderLayout());
		cp.add(centerPanel, BorderLayout.CENTER);
		// add a multiple document model to the center panel
		DefaultMultipleDocumentModel mdm = new DefaultMultipleDocumentModel(SAVED, UNSAVED);
		// add a listener to the MultiDocumentModel to update the window title and
		// enabled actions
		mdm.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				// if there are no documents, disable all actions which work on a document
				if (mdm.getNumberOfDocuments() == 0) {
					closeDocument.setEnabled(false);
					saveDocument.setEnabled(false);
					saveDocumentAs.setEnabled(false);
					statistics.setEnabled(false);
					copyAction.setEnabled(false);
					pasteAction.setEnabled(false);
					cutAction.setEnabled(false);
				}
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				// if the added document is the first one, enable all actions which work on a
				// document
				if (mdm.getNumberOfDocuments() == 1) {
					closeDocument.setEnabled(true);
					saveDocument.setEnabled(true);
					saveDocumentAs.setEnabled(true);
					statistics.setEnabled(true);
					copyAction.setEnabled(true);
					pasteAction.setEnabled(true);
					cutAction.setEnabled(true);
				}
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				SingleDocumentModel currentDocument = mdm.getCurrentDocument();
				String title = TITLE;
				// if there is a current document, add its path to the title
				if (currentDocument != null) {
					Path p = currentDocument.getFilePath();
					String s = p == null ? UNNAMED_DOCUMENT : p.toAbsolutePath().toString();
					title = s + " - " + title;
				}
				JNotepadPP.this.setTitle(title);
			}
		});

		centerPanel.add(mdm, BorderLayout.CENTER);

		this.mdm = mdm;
		this.provider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

		createActions();
		createMenus();
		// create a toolbar and add it to the start of the page
		cp.add(createToolbar(), BorderLayout.PAGE_START);
		// add a status bar to the center panel
		centerPanel.add(createStatusBar(), BorderLayout.PAGE_END);
	}

	/**
	 * Configures all the actions.
	 */
	@SuppressWarnings("serial")
	private void createActions() {

		newDocument = new LocalizedAction("new", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				mdm.createNewDocument();
			}
		};

		openDocument = new LocalizedAction("open", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				openDocument();
			}
		};

		saveDocument = new LocalizedAction("save", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveDocument(mdm.getCurrentDocument(), false);
			}
		};

		saveDocumentAs = new LocalizedAction("save_as", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveDocument(mdm.getCurrentDocument(), true);
			}
		};

		closeDocument = new LocalizedAction("close", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel model = mdm.getCurrentDocument();
				if (model.isModified()) {
					int choice = askToSave(model);
					if (choice == SAVE_CANCEL)
						return;
					else if (choice == SAVE || choice == SAVE_ALL)
						saveDocument(model, false);
				}
				mdm.closeDocument(model);
			}
		};

		statistics = new LocalizedAction("statictics", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				statistics();
			}
		};

		copyAction = new LocalizedAction("copy", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				performDefaultEditorAction(DefaultEditorKit.copyAction, e);
			}
		};

		pasteAction = new LocalizedAction("paste", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				performDefaultEditorAction(DefaultEditorKit.pasteAction, e);
			}

		};

		cutAction = new LocalizedAction("cut", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				performDefaultEditorAction(DefaultEditorKit.cutAction, e);
			}
		};

		setEnglish = new SetLanguageAction(EN, provider);
		setGerman = new SetLanguageAction(DE, provider);
		setCroatian = new SetLanguageAction(HR, provider);

		toUppercase = new TextAction("to_uppercase", provider, mdm, TextAction::toUpper);
		toLowercase = new TextAction("to_lowercase", provider, mdm, TextAction::toLower);
		invertCase = new TextAction("invert_case", provider, mdm, TextAction::invertCase);

		sortAscending = new LinesTextAction("sort_asc", provider, mdm,
				s -> LinesTextAction.sortLinesAscending(s, provider));
		sortDescending = new LinesTextAction("sort_desc", provider, mdm,
				s -> LinesTextAction.sortLinesDescending(s, provider));
		unique = new LinesTextAction("unique", provider, mdm, LinesTextAction::removeDuplicateLines);

		exit = new LocalizedAction("exit", provider) {

			@Override
			public void actionPerformed(ActionEvent e) {
				windowListener.windowClosing(null);
			}
		};

		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.SHORT_DESCRIPTION, "Opens a new untitled document");

		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Opens a selected document.");

		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Saves the current document.");
		saveDocument.setEnabled(false);

		saveDocumentAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveDocumentAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveDocumentAs.putValue(Action.SHORT_DESCRIPTION,
				"Opens a file chooser to save the document under a chosen name.");
		saveDocumentAs.setEnabled(false);

		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift C"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeDocument.putValue(Action.SHORT_DESCRIPTION, "Closes the current document.");
		closeDocument.setEnabled(false);

		statistics.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		statistics.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		statistics.putValue(Action.SHORT_DESCRIPTION,
				"Gives information about the number of characters and lines" + " in the currently opened document.");
		statistics.setEnabled(false);

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Copies the selected text.");
		copyAction.setEnabled(false);

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Pastes the text from the clipboard.");
		pasteAction.setEnabled(false);

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Cuts the selected text.");
		cutAction.setEnabled(false);

		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift E"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exit.putValue(Action.SHORT_DESCRIPTION, "Exits the application.");

		toUppercase.setEnabled(false);
		toLowercase.setEnabled(false);
		invertCase.setEnabled(false);

		sortAscending.setEnabled(false);
		sortDescending.setEnabled(false);
		unique.setEnabled(false);

	}

	private void createMenus() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new LJMenu("file", provider);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveDocumentAs));
		file.add(new JMenuItem(closeDocument));
		file.addSeparator();
		file.add(new JMenuItem(statistics));
		file.addSeparator();
		file.add(new JMenuItem(exit));
		mb.add(file);

		JMenu edit = new LJMenu("edit", provider);
		edit.add(new JMenuItem(copyAction));
		edit.add(new JMenuItem(pasteAction));
		edit.add(new JMenuItem(pasteAction));
		mb.add(edit);

		JMenu language = new LJMenu("language", provider);
		language.add(new JMenuItem(setEnglish));
		language.add(new JMenuItem(setGerman));
		language.add(new JMenuItem(setCroatian));
		mb.add(language);
		// a menu tools with sub-menus change case, sort and action unique
		JMenu tools = new LJMenu("tools", provider);
		JMenu changeCase = new LJMenu("change_case", provider);
		changeCase.add(new JMenuItem(toUppercase));
		changeCase.add(new JMenuItem(toLowercase));
		changeCase.add(new JMenuItem(invertCase));
		JMenu sort = new LJMenu("sort", provider);
		sort.add(new JMenuItem(sortDescending));
		sort.add(new JMenuItem(sortAscending));
		mb.add(tools);

		tools.add(changeCase);
		tools.add(sort);
		tools.add(new JMenuItem(unique));

		setJMenuBar(mb);
	}

	/**
	 * Creates a toolbar with JButtons for all frequently used actions.
	 * 
	 * @return the created toolbar
	 */
	private JToolBar createToolbar() {
		JToolBar tb = new JToolBar();
		tb.add(new JButton(newDocument));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveDocumentAs));
		tb.add(new JButton(closeDocument));
		tb.addSeparator();
		tb.add(new JButton(copyAction));
		tb.add(new JButton(pasteAction));
		tb.add(new JButton(cutAction));
		tb.addSeparator();
		tb.add(new JButton(statistics));
		tb.addSeparator();
		tb.add(new JButton(exit));
		return tb;
	}

	/**
	 * Creates a JPanel with labels that show information about the file length and
	 * the caret and a clock
	 * 
	 * @return created JPanel
	 */
	private Component createStatusBar() {
		JPanel statusBar = new JPanel(new BorderLayout());
		// panel to display length info
		JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lengthPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		// length: lengthNumber
		JLabel length = new LJLabel("length", provider);
		JLabel lengthNumber = new JLabel();
		lengthNumber.setPreferredSize(new Dimension(getPreferredSize().height, 16));

		lengthPanel.add(length);
		lengthPanel.add(lengthNumber);
		// panel to display caret info
		JPanel caretInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caretInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		// Ln: lnNumber
		JLabel ln = new LJLabel("ln", provider);
		JLabel lnNumber = new JLabel();
		lnNumber.setPreferredSize(new Dimension(getPreferredSize().height, 10));
		// Col: colNumber
		JLabel col = new LJLabel("col", provider);
		JLabel colNumber = new JLabel();
		colNumber.setPreferredSize(new Dimension(getPreferredSize().height, 10));
		// Sel: selNumber
		JLabel sel = new LJLabel("sel", provider);
		JLabel selNumber = new JLabel();
		selNumber.setPreferredSize(new Dimension(getPreferredSize().height, 10));

		JPanel clockPanel = createClock();
		clockPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		caretInfo.add(ln);
		caretInfo.add(lnNumber);
		caretInfo.add(col);
		caretInfo.add(colNumber);
		caretInfo.add(sel);
		caretInfo.add(selNumber);

		statusBar.add(lengthPanel, BorderLayout.LINE_START);
		statusBar.add(caretInfo, BorderLayout.CENTER);
		statusBar.add(clockPanel, BorderLayout.LINE_END);
		// a caret listener that will be registered and deregistered from documents
		// accordingly
		CaretListener caretListener = e -> {

			JTextComponent source = (JTextComponent) e.getSource();
			int caretPosition = source.getCaretPosition();

			Document doc = source.getDocument();
			Element root = doc.getDefaultRootElement();

			long lengthOfText = source.getText().length();
			int line = root.getElementIndex(caretPosition);
			int column = caretPosition - root.getElement(line).getStartOffset();
			int selected = Math.abs(e.getDot() - e.getMark());

			lengthNumber.setText(Long.toString(lengthOfText));
			lnNumber.setText(Integer.toString(line + 1));
			colNumber.setText(Integer.toString(column + 1));
			selNumber.setText(Integer.toString(selected));

		};
		// add a listener to track the current selected document
		mdm.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if (mdm.getNumberOfDocuments() == 0) {
					lengthNumber.setText("");
					lnNumber.setText("");
					colNumber.setText("");
					selNumber.setText("");
				}
				model.getTextComponent().removeCaretListener(caretListener);
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				// nothing since current document must be set to update information
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				// deregister the listener from the previous document
				if (previousModel != null)
					previousModel.getTextComponent().removeCaretListener(caretListener);
				// register it to the current document
				if (currentModel != null) {
					currentModel.getTextComponent().addCaretListener(caretListener);
					// update length and caret info
					caretListener.caretUpdate(simpleCaretEvent(currentModel));
					long length = currentModel.getTextComponent().getText().length();
					lengthNumber.setText(Long.toString(length));
				}
			}
		});

		return statusBar;
	}

	/**
	 * Creates an instance of a JClock, wraps it into a JPanel to align it right and
	 * returns it
	 * 
	 * @return a JPanel that contains a JClock aligned right
	 */
	private JPanel createClock() {
		JPanel clockPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JClock clock = new JClock();
		clockPanel.add(clock);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				clock.requestStop();
			}
		});

		return clockPanel;
	}

	/**
	 * Creates and returns a simple caret event that delegates its methods to the
	 * caret.
	 * 
	 * @param model
	 * @return CaretEvent
	 */
	private CaretEvent simpleCaretEvent(SingleDocumentModel model) {
		return new CaretEvent(model.getTextComponent()) {
			private static final long serialVersionUID = 1L;
			private JTextArea jta = (JTextArea) getSource();

			@Override
			public int getMark() {
				return jta.getCaret().getMark();
			}

			@Override
			public int getDot() {
				return jta.getCaret().getDot();
			}
		};
	}

	/**
	 * Performs a DefaultEditorKit action under key <code>key</code> on the
	 * currentModel.
	 * 
	 * @param key of the action
	 * @param e   ActionEvent
	 */
	private void performDefaultEditorAction(String key, ActionEvent e) {
		SingleDocumentModel model = mdm.getCurrentDocument();
		Action action = model.getTextComponent().getActionMap().get(key);
		action.actionPerformed(e);
	}

	private void openDocument() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Open file");
		// check if the user cancel the operation
		if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		Path filePath = jfc.getSelectedFile().toPath();
		mdm.loadDocument(filePath);
	}

	private void saveDocument(SingleDocumentModel model, boolean saveAs) {
		Path newPath = null;
		// if the saveAs flag is true or if the document is a new one, ask the user
		if (saveAs || model.getFilePath() == null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save file");
			// if the user cancels saving
			if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
				return;
			newPath = jfc.getSelectedFile().toPath();
		}
		// if this document does not have the same path as the chosen ask to
		// overwrite
		boolean fileExists = newPath == null ? false : Files.exists(newPath);
		if (fileExists && !newPath.equals(model.getFilePath())) {
			int choice = LJOptionPane.showOptionDialog(this, "file_exists_message", newPath.getFileName().toString(),
					"file_exists_title", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					YES_NO_CLOSE_OPTIONS, null, provider);

			if (choice != YES) {
				LJOptionPane.showMessageDialog(this, "not_saved_message", "not_saved_title",
						JOptionPane.INFORMATION_MESSAGE, null, provider);
				return;
			} else if (isOpen(newPath)) {
				LJOptionPane.showMessageDialog(this, "already_open_message", "already_open_title",
						JOptionPane.INFORMATION_MESSAGE, null, provider);
				return;
			}
		}

		mdm.saveDocument(model, newPath);
	}

	/**
	 * Checks if a document with the given path is among the currently opened
	 * documents.
	 * 
	 * @param path to check
	 * @return <code>true</code> if it is, <code>false</code> otherwise
	 */
	private boolean isOpen(Path path) {
		path = path.toAbsolutePath();
		for (SingleDocumentModel doc : mdm) {
			if (doc.getFilePath() == null)
				continue;
			if (path.equals(doc.getFilePath().toAbsolutePath()))
				return true;
		}
		
		return false;
	}

	private void statistics() {
		Runnable calculateStatistics = () -> {

			SingleDocumentModel model = mdm.getCurrentDocument();

			long characterCount = 0;
			long characterNoWhitespacesCount = 0;
			long linesCount = 1;

			if (model != null) {
				char[] text = model.getTextComponent().getText().toCharArray();
				characterCount = text.length;
				for (int i = 0; i < text.length; ++i) {
					if (!Character.isWhitespace(text[i]))
						characterNoWhitespacesCount++;
					else if (text[i] == '\n')
						linesCount++;
				}

			}
			String message = String.format("your file has %d characters , %d non-whitespace characters%nand %d lines",
					characterCount, characterNoWhitespacesCount, linesCount);
			String translated = parseAndTranslate(message);
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(this, translated, "statistics", JOptionPane.INFORMATION_MESSAGE);
			});
		};
		pool.submit(calculateStatistics);
	}

	private int askToSave(SingleDocumentModel model) {
		String name = model.getFilePath() == null ? UNNAMED_DOCUMENT : model.getFilePath().getFileName().toString();
		int choice = LJOptionPane.showOptionDialog(this, "save_message", name, "save_title",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, UNSAVED, SAVE_DONT_SAVE_OPTIONS,
				SAVE_DONT_SAVE_OPTIONS[1], provider);
		return choice;
	}

	/**
	 * Checks all opened documents and asks the user if he wants to save unsaved
	 * documents. The user can also choose to abort the checking which will return
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> if all documents were checked and an answer from
	 *         the user was obtained for every document, <code>false</code> if the
	 *         user decided to cancel the operation
	 */
	private boolean checkUnsaved() {
		boolean saveAllFlag = false;
		for (SingleDocumentModel m : mdm) {
			if (m.isModified()) {
				if (saveAllFlag) {
					saveDocument(m, false);
					continue;
				}

				int choice = askToSave(m);
				if (choice == SAVE_CANCEL)
					return false;
				if (choice == SAVE || choice == SAVE_ALL) {
					saveDocument(m, false);
					if (choice == SAVE_ALL)
						saveAllFlag = true;
				}

			}
		}
		return true;
	}

	/**
	 * Splits the given <code>message</code> by spaces and tries to translate every
	 * word using the <code>provider</code> and returns it.
	 * 
	 * @return translated message
	 */
	private String parseAndTranslate(String message) {

		StringBuilder sb = new StringBuilder();
		String[] words = message.split("\\s+");

		for (String s : words) {
			// try to translate, if it goes through, append
			try {
				String translated = provider.getString(s);
				sb.append(translated).append(' ');
				// if it fails, keep the original string
			} catch (MissingResourceException e) {
				sb.append(s).append(' ');
			}
		}
		return sb.toString();
	}

	/**
	 * Reads the image with given <code>imageName</code> from icons subpackage.
	 * 
	 * @param imageName to read
	 * @return ImageIcon with given <code>imagename</code>
	 */
	private ImageIcon loadImage(String imageName) {
		try (InputStream is = this.getClass().getResourceAsStream("icons/" + imageName)) {
			if (is == null)
				throw new IOException();
			byte[] bytes = is.readAllBytes();
			return new ImageIcon(bytes);
		} catch (IOException e) {
			throw new RuntimeException("Reading of image: " + imageName + " failed.");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
