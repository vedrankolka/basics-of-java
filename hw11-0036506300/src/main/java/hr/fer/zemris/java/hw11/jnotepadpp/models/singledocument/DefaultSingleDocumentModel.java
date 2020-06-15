package hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An implementation of a {@link SingleDocumentListener}.
 * 
 * @author Vedran Kolka
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/** This model's text editor */
	private JTextArea editor;
	/**
	 * Path from which this document was loaded, <code>null</code> if it is a new
	 * document
	 */
	private Path path;
	/** States if the document has been modified since the last save */
	private boolean modified;

	private List<SingleDocumentListener> listeners;

	/**
	 * Constructor.
	 * 
	 * @param path    to the file that this document represents, can be
	 *                <code>null</code> if it is a new document
	 * @param content content of the document
	 */
	public DefaultSingleDocumentModel(Path path, String content) {
		this.path = path;
		this.editor = new JTextArea(content);
		this.listeners = new ArrayList<>();
		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			private void update() {
				modified = true;
				notifyModified();
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	/**
	 * @throws NullPointerException if <code>path</code> is <code>null</code>
	 */
	@Override
	public void setFilePath(Path path) {
		this.path = Objects.requireNonNull(path);
		notifyPathUpdated();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyModified();
	}

	/**
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int hashCode() {
		return Objects.hash(editor, listeners, modified, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DefaultSingleDocumentModel))
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		if (path == null && other.path == null)
			return false;
		return Objects.equals(path, other.path);
	}

	private void notifyModified() {
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}

	private void notifyPathUpdated() {
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

}
