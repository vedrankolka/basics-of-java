package hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument.DefaultSingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument.SingleDocumentModel;

/**
 * An implementation of a {@link MultipleDocumentModel} that uses a JTabbedPane to show its documents.
 * 
 * @author Vedran Kolka
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	/** List of documents currently opened in this model */
	private List<SingleDocumentModel> documents;
	/** The current document opened for editing */
	private SingleDocumentModel currentModel;
	/** A list of listeners registered to this model */
	private List<MultipleDocumentListener> listeners;
	/** Icon to set with a tab of a document that is saved */
	private ImageIcon saved;
	/** Icon to set with a tab of a document that is not saved */
	private ImageIcon unsaved;

	/**
	 * Constructor.
	 */
	public DefaultMultipleDocumentModel(ImageIcon saved, ImageIcon unsaved) {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		this.saved = saved;
		this.unsaved = unsaved;
		// add a listener to update the currentModel accordingly
		addChangeListener( e -> {
			int index = getSelectedIndex();
			if (index == -1)
				setCurrentModel(null);
			else
				setCurrentModel(documents.get(getSelectedIndex()));
		});
		addMultipleDocumentListener(new SimpleMultipleDocumentListener() {
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel != null) {
					int index = documents.indexOf(currentModel);
					setSelectedIndex(index);
				}
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		// create the document model and add it to documents
		SingleDocumentModel model = new DefaultSingleDocumentModel(null, "");
		documents.add(model);
		// add it to a new tab
		addDocumentToTab(model);
		// set it as currentModel and notify listeners
		setCurrentModel(model);
		notifyDocumentAdded(model);

		return model;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentModel;
	}

	/**
	 * @throws NullPointerException if <code>path</code> is <code>null</code>
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (!Files.isReadable(Objects.requireNonNull(path))) {
			JOptionPane.showMessageDialog(this, path + " is not readable", "Loading error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		SingleDocumentModel model;
		// search if this document is already opened
		int index = searchDocumentsFor(path);
		if (index != -1) {
			model = documents.get(index);
			setCurrentModel(model);
			setSelectedIndex(index);
			return model;
		}
		// if not, read the content and create a new model
		String content = null;
		try {
			content = Files.readString(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Reading from " + path + " failed.", "Loading error",
					JOptionPane.ERROR_MESSAGE);
			return null;   
		}

		model = new DefaultSingleDocumentModel(path, content);
		// add the document model to documents and set as current model
		documents.add(model);
		// add the model's content to a new tab
		addDocumentToTab(model);
		setCurrentModel(model);
		notifyDocumentAdded(model);
		return model;
	}

	/**
	 * Searches <code>documents</code> for a model with filePath <code>path</code>.
	 * 
	 * @param path to search
	 * @return index of model with filePath <code>path</code> or -1 if no such
	 *         model exists in documents or if <code>path</code> is null
	 */
	private int searchDocumentsFor(Path path) {
		if (path == null)
			return -1;
		for (int i  = 0; i<documents.size(); ++i) {
			SingleDocumentModel m = documents.get(i);
			if (path.equals(m.getFilePath()))
				return i;
		}
		return -1;
	}

	/**
	 * Adds the document to a new tab and registers a listener to it to update the
	 * title and icon.
	 * 
	 * @param model to add
	 */
	private void addDocumentToTab(SingleDocumentModel model) {
		// register a listener to update the title and icon
		model.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(getSelectedIndex(), model.isModified() ? unsaved : saved);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				Path p = model.getFilePath();
				String title = p == null ? JNotepadPP.UNNAMED_DOCUMENT : p.getFileName().toString();
				String toolTip = p == null ? JNotepadPP.UNNAMED_DOCUMENT : p.toAbsolutePath().toString();
				int index = getSelectedIndex();
				setTitleAt(index, title);
				setToolTipTextAt(index, toolTip);
			}

		});
		// create a new tab with the content of the given model and set the title,
		// tooltip and icon
		JScrollPane tab = new JScrollPane(model.getTextComponent());
		Path p = model.getFilePath();
		// if the model has no path set, it is an unnamed document, otherwise, set path
		// as name and absolute path as tooltip
		String title = p == null ? JNotepadPP.UNNAMED_DOCUMENT : p.getFileName().toString();
		String toolTip = p == null ? JNotepadPP.UNNAMED_DOCUMENT : p.toAbsolutePath().toString();
		addTab(title, saved, tab, toolTip);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model);
		// determine the path to save
		Path p = newPath == null ? model.getFilePath() : newPath;

		try {
			Files.writeString(p, model.getTextComponent().getText(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "An error occurred while saving. Document not saved.", "Saving error",
					JOptionPane.ERROR_MESSAGE);
		}
		model.setFilePath(p);
		model.setModified(false);
		notifyCurrentDocumentChanged(model);
	}
	
	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		if (index == -1) return;
		documents.remove(index);
		removeTabAt(index);
		// if it is possible set the previous tab as current
		if (index > 0) {
			setCurrentModel(documents.get(index - 1));
		//else the first tab was removed, so set the documents(0) if there is one
		} else if (documents.size() >= 1) {
			setCurrentModel(documents.get(0));
		// else there are no more opened documents
		} else {
			setCurrentModel(null);
		}
		notifyDocumentRemoved(model);
	}

	/**
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	/**
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	private void notifyCurrentDocumentChanged(SingleDocumentModel previous) {
		listeners.forEach(l -> l.currentDocumentChanged(previous, currentModel));
	}

	private void notifyDocumentAdded(SingleDocumentModel newModel) {
		listeners.forEach(l -> l.documentAdded(newModel));
	}

	private void notifyDocumentRemoved(SingleDocumentModel removedModel) {
		listeners.forEach(l -> l.documentRemoved(removedModel));
	}

	/**
	 * Sets the current model to given <code>model</code> <b>and notifies</b>
	 * registered listeners.
	 * 
	 * @param model
	 */
	private void setCurrentModel(SingleDocumentModel model) {
		SingleDocumentModel previous = currentModel;
		currentModel = model;
		notifyCurrentDocumentChanged(previous);
	}

}
