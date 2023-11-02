package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JTabbedPane;

/**
 * A listener for the {@link MultipleDocumentModel} implementation
 * @author Vito Sabalic
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Called when a change has been made to the currently active document
	 * @param previousModel the model instance before the change
	 * @param currentModel The model instance after the change
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Called when a new tab from {@link JTabbedPane} has been added
	 * @param model The model added
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Called when a tab has been removed from {@link JTabbedPane}
	 * @param model The removed model
	 */
	void documentRemoved(SingleDocumentModel model);

}
