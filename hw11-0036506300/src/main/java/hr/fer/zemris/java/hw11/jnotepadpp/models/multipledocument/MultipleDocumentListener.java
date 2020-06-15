package hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument;

import hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument.SingleDocumentModel;
/**
 * A listener interested in the changes in a {@link MultipleDocumentModel},
 * in latter documentation referred to as "the MultipleDocumentModel".
 * @author Vedran Kolka
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Indicates that the current model in the {@link MultipleDocumentModel} has changed
	 * from <code>previousModel</code> to <code>currentModel</code> 
	 * @param previousModel - the model that was previously current
	 * @param currentModel - the new current model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	/**
	 * Indicates that a document has been added to the {@link MultipleDocumentModel}.
	 * @param model - the newly added model
	 */
	void documentAdded(SingleDocumentModel model);
	/**
	 * Indicates that a document has been removed from the {@link MultipleDocumentModel}.
	 * @param model - the removed model
	 */
	void documentRemoved(SingleDocumentModel model);

}
