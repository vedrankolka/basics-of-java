package hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument;

import java.nio.file.Path;

import hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument.SingleDocumentModel;

/**
 * A model that has zero or more {@link SingleDocumentModel}s and if there is at
 * least one SingleDocumentModel, has a current model.
 * 
 * @author Vedran Kolka
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates a new {@link SingleDocumentModel}
	 * 
	 * @return created model
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns the current document of this model.
	 * 
	 * @return current model
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a document from the given <code>path</code> and returns it as a
	 * {@link SingleDocumentModel}
	 * 
	 * @param path
	 * @return created model or <code>null</code> if the file could not be read
	 * @throws NullPointerException if <code>path</code> is <code>null</code>
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the given <code>model</code> with the given <code>newPath</code> and
	 * sets the model's path to <code>newPath</code> or with the current path of the
	 * given model if <code>newPath</code> is <code>null</code>.
	 * 
	 * @param model   to save
	 * @param newPath - new path for the model
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes the given <code>model</code>.
	 * 
	 * @param model
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds the given {@link MultipleDocumentListener} <code>l</code> to this model.
	 * 
	 * @param l
	 * @throws NullPointerException if <code>l</code> is <code>null</code>
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes the given {@link MultipleDocumentListener} <code>l</code> from this
	 * model.
	 * 
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns the number of currently opened documents in this model.
	 * 
	 * @return number of opened documents in this model
	 */
	int getNumberOfDocuments();

	/**
	 * Returns the {@link SingleDocumentModel} at given <code>index</code>.
	 * 
	 * @param index of the model to return
	 * @return model at the given <code>index</code>
	 * @throws IndexOutOfBoundsException
	 */
	SingleDocumentModel getDocument(int index);

}
