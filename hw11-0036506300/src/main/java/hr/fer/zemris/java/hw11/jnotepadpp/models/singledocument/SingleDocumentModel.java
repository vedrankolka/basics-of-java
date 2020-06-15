package hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument.MultipleDocumentModel;

/**
 * A model of a single document opened in a {@link MultipleDocumentModel}
 * 
 * @author Vedran Kolka
 *
 */
public interface SingleDocumentModel {
	/**
	 * Returns this models {@link JTextArea}
	 * 
	 * @return this models {@link JTextArea}
	 */
	JTextArea getTextComponent();

	/**
	 * Returns the path to this document, or <code>null</code> if the document does
	 * not exist on the disk (e.g. it is a new document and has not been saved yet).
	 * 
	 * @return path to the document
	 */
	Path getFilePath();

	/**
	 * Sets the path of this document where it should be saved.
	 * 
	 * @param path to set
	 * @throws NullPointerException if <code>path</code> is <code>null</code>
	 */
	void setFilePath(Path path);

	/**
	 * Checks if this document has been modified since the last time it was saved.
	 * 
	 * @return <code>true</code> if it has, <code>false</code> otherwise
	 */
	boolean isModified();

	/**
	 * Sets <code>modified</code> to the given <code>modified</code> value.
	 * 
	 * @param modified
	 */
	void setModified(boolean modified);

	/**
	 * Adds the given {@link SingleDocumentListener} <code>l</code> to this models
	 * listeners.
	 * 
	 * @param l
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes the given {@link SingleDocumentListener} <code>l</code> from this models
	 * listeners.
	 * 
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
