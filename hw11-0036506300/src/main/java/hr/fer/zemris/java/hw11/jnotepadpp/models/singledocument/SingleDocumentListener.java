package hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument;

/**
 * Models a listener of a SingleDocumentModel.
 * 
 * @author Vedran Kolka
 *
 */
public interface SingleDocumentListener {
	/**
	 * Indicates that the <code>model</code> in which this listener is interested
	 * has been modified.
	 * 
	 * @param model - the modified model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Indicates the file path of the <code>model</code> has been changed.
	 * 
	 * @param model - the modified model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}