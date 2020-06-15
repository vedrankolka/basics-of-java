package hr.fer.zemris.java.hw11.jnotepadpp.models.multipledocument;

import hr.fer.zemris.java.hw11.jnotepadpp.models.singledocument.SingleDocumentModel;
/**
 * An implementation of a {@link MultipleDocumentListener} that does nothing<br>
 * Created to be extended.
 * @author Vedran Kolka
 *
 */
public class SimpleMultipleDocumentListener implements MultipleDocumentListener {

	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {}

	@Override
	public void documentAdded(SingleDocumentModel model) {}

	@Override
	public void documentRemoved(SingleDocumentModel model) {}

}
